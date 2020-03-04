package example.tokenizer;

public class PlusToken implements Token {
    @Override
    public boolean equals(final Object other) {
        return other instanceof PlusToken;
    }

    @Override
    public String toString() {
        return "+";
    }
}
