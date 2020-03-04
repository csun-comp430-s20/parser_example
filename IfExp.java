// sealed trait Exp
// case class IfExp(e1: Exp, e2: Exp, e3: Exp) extends Exp
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
}
