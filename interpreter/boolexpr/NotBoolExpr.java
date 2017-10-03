package interpreter.boolexpr;

public class NotBoolExpr extends BoolExpr{
    private BoolExpr expr;

    public NotBoolExpr(BoolExpr expr, int line) {
        super(line);
        this.expr = expr;
    }
    
    public boolean expr(){
        return !this.expr();
    }
}
