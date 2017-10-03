package interpreter.command;

import interpreter.expr.Expr;

/**
 *
 * @author gceconelli
 */
public class PushCommand extends ActionCommand{

    private Expr list,values;
    
    
    public PushCommand(int line,Expr list,Expr values) {
        super(line);
        this.list = list;
        this.values = values;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO
    }

    
    
}
