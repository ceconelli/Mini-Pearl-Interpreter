package interpreter.command;

import interpreter.boolexpr.BoolExpr;


public class DoWhileCommand extends Command{
    BoolExpr expr;
    Command cmd;

    public DoWhileCommand(BoolExpr expr, Command cmd, int line) {
        super(line);
        this.expr = expr;
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        do{
            cmd.execute();
        }while(expr.expr());
    }
    
    
}
