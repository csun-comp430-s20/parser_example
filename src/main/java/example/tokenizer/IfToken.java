package example.tokenizer;

public class IfToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof IfToken;
    }

    @Override
    public String toString() {
        return "if";
    }
}
