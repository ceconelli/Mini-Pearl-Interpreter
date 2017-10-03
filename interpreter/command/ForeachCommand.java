package interpreter.command;
import interpreter.expr.Expr;
import interpreter.expr.Variable;

public class ForeachCommand extends Command {
    Variable var;
    Expr expr;
    Command cmd;

    public ForeachCommand(Variable var, Expr expr, Command cmd, int line) {
        super(line);
        this.var = var;
        this.expr = expr;
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        //TODO
    }

    
    
    
}
