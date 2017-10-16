package interpreter.expr;

import interpreter.value.IntegerValue;
import interpreter.value.ListValue;
import interpreter.value.PrimitiveValue;
import interpreter.value.Value;
import java.util.List;

public class ListIndexExpr extends IndexExpr {

    public ListIndexExpr(Expr base, Expr index, int line) {
        super(base, index, line);
    }

    public Value<?> expr(){
        Value baseV = (Value)base.expr();
        Value indexV = (Value)index.expr();
        ListValue listV = (ListValue)base.expr();
        IntegerValue integerV = (IntegerValue)index.expr();
        return listV.value().get(integerV.value());
    }
    
    public void setValue(Value<?> value){
        List<PrimitiveValue<?>> values;
        ListValue listV = (ListValue)base.expr();
        IntegerValue integerV = (IntegerValue)index.expr();
        values = listV.value();
        values.set(integerV.value(),(PrimitiveValue)value);
       
    }

    
    

    
}
