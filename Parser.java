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
                                     "Received: " tokens[position].toString());
        }
    }

    // public ParseResult<E> parseE(final int startPos) throws ParseException {
    //     try {
    //         final ParseResult<E> alpha = parseAlpha(startPos);
    //         return alpha;
    //     } catch (ParseException e) {
    //         return parseBeta(startPos);
    //     }
    // }
            
    public ParseResult<Exp> parseAdditiveExp(final int startPos) throws ParseException { ... }

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
            assertTokenIs(startPos, new LeftParenToken());
            final ParseResult<Exp> inner = parseExp(startPos + 1);
            assertTokenIs(inner.nextPos, new RightParenToken());
            return new ParseResult<Exp>(inner.result,
                                        inner.nextPos + 1);
        }
    }
    
    public ParseResult<Exp> parseExp(final int startPos) throws ParseException {
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
    }
}
