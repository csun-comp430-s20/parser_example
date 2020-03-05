package example.parser;

public class VariableExp implements Exp {
    public final String name;

    public VariableExp(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object other) {
        return (other instanceof VariableExp &&
                name.equals(((VariableExp)other).name));
    }
}
