package example.parser;

public class PlusExp implements Exp {
    public final Exp left;
    public final Exp right;

    public PlusExp(final Exp left, final Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof PlusExp) {
            final PlusExp otherPlus = (PlusExp)other;
            return (left.equals(otherPlus.left) &&
                    right.equals(otherPlus.right));
        } else {
            return false;
        }
    } // equals

    @Override
    public String toString() {
        return ("(" +
                left.toString() +
                " + " +
                right.toString() +
                ")");
    } // toString
} // PlusExp
