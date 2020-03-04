package example.tokenizer;

public class ElseToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof ElseToken;
    }

    @Override
    public String toString() {
        return "else";
    }
}
