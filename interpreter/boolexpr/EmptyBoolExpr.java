package interpreter.boolexpr;

import interpreter.expr.Expr;
import interpreter.value.StringValue;

public class EmptyBoolExpr {
    private Expr expr;

    public EmptyBoolExpr(Expr expr) {
        this.expr = expr;
    }
    
    public boolean expr(){
        StringValue stringVal = (StringValue)this.expr.expr();   
        return stringVal.value() == "";
        
    }
}
