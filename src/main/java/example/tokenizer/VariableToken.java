package example.tokenizer;

public class VariableToken implements Token {
    public final String name;

    public VariableToken(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof VariableToken &&
                name.equals(((VariableToken)other).name));
    }

    @Override
    public String toString() {
        return name;
    }
}
