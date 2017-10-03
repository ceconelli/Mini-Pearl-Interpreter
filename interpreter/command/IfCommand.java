package interpreter.command;
import interpreter.boolexpr.*;

public class IfCommand extends Command {
    private BoolExpr cond;
    private Command then;
    private Command _else;

    public IfCommand(BoolExpr cond, Command then, int line) {
        super(line);
        this.cond = cond;
        this.then = then;
    }
    
    public IfCommand(BoolExpr cond, Command then,Command _else, int line) {
        super(line);
        this.cond = cond;
        this.then = then;
        this._else = _else;
    }

    @Override
    public void execute() {
//        throw new UnsupportedOperationException("Not supported yet.");
        if(this.cond.expr()){
            this.then.execute();
        }
        else if(!this._else.equals(null))
            this._else.execute();
    }
    
    
    
}
