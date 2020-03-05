package example.parser;

import java.util.List;
import java.util.ArrayList;

import example.tokenizer.*;

public class Parser {
    private final Token[] tokens;
    
    public Parser(final Token[] tokens) {
        this.tokens = tokens;
    }

    private class ParseResult<A> {
        public final A result;
        public final int nextPos;
        public ParseResult(final A result,
                           final int nextPos) {
            this.result = result;
            this.nextPos = nextPos;
        }
    }

    private void assertTokenIs(final int position, final Token token) throws ParseException {
        if (!tokens[position].equals(token)) {
            throw new ParseException("Expected: " + token.toString() +
                                     "Received: " + tokens[position].toString());
        }
    } // assertTokenIs

    private ParseResult<Exp> parsePrimary(final int startPos) throws ParseException {
        if (tokens[startPos] instanceof VariableToken) {
            final VariableToken asVar = (VariableToken)tokens[startPos];
            return new ParseResult<Exp>(new VariableExp(asVar.name),
                                        startPos + 1);
        } else if (tokens[startPos] instanceof IntegerToken) {
            final IntegerToken asInt = (IntegerToken)tokens[startPos];
            return new ParseResult<Exp>(new IntegerExp(asInt.value),
                                        startPos + 1);
        } else {
            assertTokenIs(startPos, new LeftParenToken());
            final ParseResult<Exp> inner = parseExp(startPos + 1);
            assertTokenIs(inner.nextPos, new RightParenToken());
            return new ParseResult<Exp>(inner.result,
                                        inner.nextPos + 1);
        }
    } // parsePrimary
    
    private ParseResult<Exp> parseExp(final int startPos) throws ParseException {
        if (tokens[startPos] instanceof IfToken) {
            assertTokenIs(startPos + 1, new LeftParenToken());
            final ParseResult<Exp> guard = parseExp(startPos + 2);
            assertTokenIs(guard.nextPos, new RightParenToken());
            final ParseResult<Exp> ifTrue = parseExp(guard.nextPos + 1);
            assertTokenIs(ifTrue.nextPos, new ElseToken());
            final ParseResult<Exp> ifFalse = parseExp(ifTrue.nextPos + 1);
            return new ParseResult<Exp>(new IfExp(guard.result, ifTrue.result, ifFalse.result),
                                        ifFalse.nextPos);
        } else {
            return parseAdditiveExp(startPos);
        }
    } // parseExp

    private ParseResult<List<Exp>> parseAdditiveExpHelper(int curPos) {
        final List<Exp> result = new ArrayList<Exp>();

        while (curPos < tokens.length) {
            try {
                assertTokenIs(curPos, new PlusToken());
                final ParseResult<Exp> currentPrimary = parsePrimary(curPos + 1);
                result.add(currentPrimary.result);
                curPos = currentPrimary.nextPos;
            } catch (final ParseException e) {
                break;
            }
        }

        return new ParseResult<List<Exp>>(result, curPos);
    } // parseAdditiveExpHelper
            
    private ParseResult<Exp> parseAdditiveExp(final int startPos) throws ParseException {
        final ParseResult<Exp> initialPrimary = parsePrimary(startPos);
        final ParseResult<List<Exp>> list = parseAdditiveExpHelper(initialPrimary.nextPos);
        Exp finalResult = initialPrimary.result;

        for (final Exp current : list.result) {
            finalResult = new PlusExp(finalResult, current);
        }

        return new ParseResult<Exp>(finalResult, list.nextPos);
    } // parseAdditiveExp

    public Exp parseToplevelExp() throws ParseException {
        final ParseResult<Exp> toplevel = parseExp(0);
        if (toplevel.nextPos == tokens.length) {
            return toplevel.result;
        } else {
            throw new ParseException("tokens remaining at end");
        }
    } // parseToplevelExp
}
