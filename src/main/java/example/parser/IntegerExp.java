package example.parser;

public class IntegerExp implements Exp {
    public final int value;

    public IntegerExp(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof IntegerExp &&
                value == ((IntegerExp)other).value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
