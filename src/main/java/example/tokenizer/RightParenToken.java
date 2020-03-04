package example.tokenizer;

public class RightParenToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof RightParenToken;
    }

    @Override
    public String toString() {
        return ")";
    }
}
