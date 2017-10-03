package interpreter.boolexpr;

import interpreter.value.IntegerValue;

public class CompositeBoolExpr extends BoolExpr{
    private BoolExpr left;
    private BoolOp op;
    private BoolExpr rigth;

    public CompositeBoolExpr(BoolExpr left, BoolOp op, BoolExpr rigth, int line) {
        super(line);
        this.left = left;
        this.op = op;
        this.rigth = rigth;
    }
    
    public boolean expr(){
        switch(this.op){
            case And:
                return this.left.expr() && this.rigth.expr();
            case Or:
                return this.left.expr() || this.rigth.expr();
        }
        return false;
    }
}
