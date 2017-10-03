package interpreter.command;

import interpreter.expr.Expr;

public class UnshiftCommand extends ActionCommand{
    private Expr list,values;

    public UnshiftCommand(Expr list, Expr values, int line) {
        super(line);
        this.list = list;
        this.values = values;
    }

    public void execute() {
        
        //TODO
    }
    
    
}
