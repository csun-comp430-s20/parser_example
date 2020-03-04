package example.tokenizer;

public class LeftParenToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof LeftParenToken;
    }

    @Override
    public String toString() {
        return "(";
    }
}
