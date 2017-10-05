package interpreter.expr;

import interpreter.value.ListValue;
import interpreter.value.Value;

public class ListIndexExpr extends IndexExpr {

    public ListIndexExpr(Expr base, Expr index, int line) {
        super(base, index, line);
    }

//    public Value<?> expr(){
//        Value baseV = (Value)base.expr();
//        Value indexV = (Value)index.expr();
//        ListValue listV = (ListValue)base.expr();
//    }
//    
    public void setValue(Value<?> value){
        
    }

    
    public Value<?> expr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
