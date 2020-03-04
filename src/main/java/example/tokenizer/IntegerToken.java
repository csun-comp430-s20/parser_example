package example.tokenizer;

public class IntegerToken implements Token {
    public final int value;
    
    public IntegerToken(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof IntegerToken &&
                value == ((IntegerToken)other).value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
