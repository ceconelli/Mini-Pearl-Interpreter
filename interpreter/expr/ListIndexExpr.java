package interpreter.expr;

import interpreter.value.IntegerValue;
import interpreter.value.ListValue;
import interpreter.value.PrimitiveValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import java.util.List;

import syntatic.SyntaticAnalysis;

public class ListIndexExpr extends IndexExpr {

    public ListIndexExpr(Expr base, Expr index, int line) {
        super(base, index, line);
    }

    public Value<?> expr(){      
        Value<?> baseV = (Value<?>)base.expr();
		Value<?> indexV = (Value<?>)index.expr();
		
		if(!(baseV instanceof ListValue) && !(indexV instanceof IntegerValue))
			SyntaticAnalysis.showError("Invalid type for the List or Index Value on List",this.getLine());
		
		ListValue lv = (ListValue)base.expr();
        IntegerValue iv = (IntegerValue)index.expr();
		return lv.value().get(iv.value());
    }
    
    public void setValue(Value<?> value){
    	
    	List <PrimitiveValue<?>> values;
        
        if(!(value instanceof IntegerValue) && !(value instanceof StringValue))
        	SyntaticAnalysis.showError("Invalid value type for List", this.getLine());
        
        Value<?> baseV = (Value<?>)base.expr();
		Value<?> indexV = (Value<?>)index.expr();
		
		if(!(baseV instanceof ListValue) && !(indexV instanceof IntegerValue))
			SyntaticAnalysis.showError("Invalid type for the List or Index Value on List", this.getLine());
		
        ListValue lv = (ListValue)base.expr();
        IntegerValue iv = (IntegerValue)index.expr();
        
        values = lv.value();
        values.set(iv.value(),(PrimitiveValue<?>)value);
       
    }

    
    

    
}
