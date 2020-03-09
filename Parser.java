import java.util.List;
import java.util.ArrayList;

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

    private void checkTokenIs(final int position, final Token token) throws ParseException {
        if (!tokens[position].equals(token)) {
            throw new ParseException("Expected: " + token.toString() +
                                     "Received: " + tokens[position].toString());
        }
    }

    public ParseResult<List<Exp>> parseAdditiveExpHelper(final int startPos) {
        final List<Exp> resultList = new ArrayList<Exp>();
        int curPos = startPos;

        while (curPos < tokens.length) {
            try {
                checkTokenIs(curPos, new PlusToken());
                final ParseResult<Exp> curPrimary = parsePrimary(curPos + 1);
                curPos = curPrimary.nextPos;
                resultList.add(curPrimary.result);
            } catch (final ParseException e) {
                break;
            }
        }

        return new ParseResult<List<Exp>>(resultList, curPos);
    }
    
    public ParseResult<Exp> parseAdditiveExp(final int startPos) throws ParseException {
        final ParseResult<Exp> starting = parsePrimary(startPos);
        final ParseResult<List<Exp>> rest = parseAdditiveExpHelper(starting.nextPos);
        Exp resultExp = starting.result;

        for (final Exp otherExp : rest.result) {
            resultExp = new PlusExp(resultExp, otherExp);
        }

        return new ParseResult<Exp>(resultExp, rest.nextPos);
    }

    public ParseResult<Exp> parsePrimary(final int startPos) throws ParseException {
        if (tokens[startPos] instanceof VariableToken) {
            final VariableToken asVar = (VariableToken)tokens[startPos];
            return new ParseResult<Exp>(new VariableExp(asVar.name),
                                        startPos + 1);
        } else if (tokens[startPos] instanceof IntegerToken) {
            final IntegerToken asInt = (IntegerToken)tokens[startPos];
            return new ParseResult<Exp>(new IntegerExp(asInt.value),
                                        startPos + 1);
        } else {
            checkTokenIs(startPos, new LeftParenToken());
            final ParseResult<Exp> inner = parseExp(startPos + 1);
            checkTokenIs(inner.nextPos, new RightParenToken());
            return new ParseResult<Exp>(inner.result,
                                        inner.nextPos + 1);
        }
    }
    
    public ParseResult<Exp> parseExp(final int startPos) throws ParseException {
        if (tokens[startPos] instanceof IfToken) {
            checkTokenIs(startPos + 1, new LeftParenToken());
            final ParseResult<Exp> guard = parseExp(startPos + 2);
            checkTokenIs(guard.nextPos, new RightParenToken());
            final ParseResult<Exp> ifTrue = parseExp(guard.nextPos + 1);
            checkTokenIs(ifTrue.nextPos, new ElseToken());
            final ParseResult<Exp> ifFalse = parseExp(ifTrue.nextPos + 1);
            return new ParseResult<Exp>(new IfExp(guard.result, ifTrue.result, ifFalse.result),
                                        ifFalse.nextPos);
        } else {
            return parseAdditiveExp(startPos);
        }
    }

    public Exp parseToplevelExp() throws ParseException {
        final ParseResult<Exp> result = parseExp(0);

        if (result.nextPos == tokens.length) {
            return result.result;
        } else {
            throw new ParseException("extra tokens at end");
        }
    }
}
