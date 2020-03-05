package example.parser;

public class IfExp implements Exp {
    public final Exp guard;
    public final Exp trueBranch;
    public final Exp falseBranch;

    public IfExp(final Exp guard,
                 final Exp trueBranch,
                 final Exp falseBranch) {
        this.guard = guard;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof IfExp) {
            final IfExp otherIf = (IfExp)other;
            return (guard.equals(otherIf.guard) &&
                    trueBranch.equals(otherIf.trueBranch) &&
                    falseBranch.equals(otherIf.falseBranch));
        } else {
            return false;
        }
    } // equals

    @Override
    public String toString() {
        return ("(if (" +
                guard.toString() +
                ") " +
                trueBranch.toString() +
                " else " +
                falseBranch.toString());
    } // toString
} // IfExp
