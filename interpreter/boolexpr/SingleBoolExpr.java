package interpreter.boolexpr;

import interpreter.expr.Expr;
import interpreter.value.IntegerValue;

public class SingleBoolExpr extends BoolExpr{
    private Expr left;
    private RelOp op;
    private Expr rigth;

    public SingleBoolExpr(Expr left, RelOp op, Expr rigth, int line) {
        super(line);
        this.left = left;
        this.op = op;
        this.rigth = rigth;
    }
    
    public boolean expr(){
        IntegerValue l = (IntegerValue)this.left.expr();
        IntegerValue r = (IntegerValue)this.rigth.expr();
        switch(this.op){
            case Equal:
                return l.value() == r.value();
            case NotEqual:
                return l.value() != r.value();
            case LowerThan:
                return l.value() < r.value();
            case LowerEqual:
                return l.value() <= r.value();
            case GreaterThan:
                return l.value() > r.value();
            case GreaterEqual:
                return l.value() >= r.value();
        }
        return false;
    }
}
