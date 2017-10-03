package interpreter.command;

import interpreter.value.Value;
import interpreter.expr.Expr;
import interpreter.expr.SetExpr;
import interpreter.expr.InvalidValueException;

public class AssignCommand extends Command {

    private SetExpr lhs;
    private Expr rhs;

    public AssignCommand(SetExpr lhs, Expr rhs, int line) {
        super(line);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void execute() {
        Value<?> v = rhs.expr();

        try {
            lhs.setValue(v);
        } catch (InvalidValueException ive) {
            System.out.printf("%02d: Operação inválida\n",
                super.getLine());
            System.exit(1);
        }
    }
}
