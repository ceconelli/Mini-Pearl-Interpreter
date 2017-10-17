package interpreter.expr;
import syntatic.SyntaticAnalysis;
import interpreter.value.HashValue;
import interpreter.value.IntegerValue;
import interpreter.value.PrimitiveValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class HashIndexExpr extends IndexExpr{

	public HashIndexExpr(Expr base, Expr index, int line) {
		super(base, index, line);
	}
	
	public Value<?> expr(){
		Value<?> baseV = (Value<?>)base.expr();
		Value<?> indexV = (Value<?>)index.expr();
		
		if(!(baseV instanceof HashValue) && !(indexV instanceof StringValue))
			SyntaticAnalysis.showError("Invalid type for the Hash or String Value on Hash",this.getLine());
		
		HashValue hashV = (HashValue)base.expr();
        StringValue stringV = (StringValue)index.expr();
		return hashV.value().get(stringV.value());
		
	}

    @Override
    public void setValue(Value<?> value) {
    	
        PrimitiveValue<?> pv = (PrimitiveValue<?>) value;
        
        if(!(value instanceof IntegerValue) && !(value instanceof StringValue))
        	SyntaticAnalysis.showError("Invalid value type for Hash", this.getLine());
        
        Value<?> baseV = (Value<?>)base.expr();
		Value<?> indexV = (Value<?>)index.expr();
		
		if(!(baseV instanceof HashValue) && !(indexV instanceof StringValue))
			SyntaticAnalysis.showError("Invalid type for the Hash or String Value on Hash", this.getLine());
		
        HashValue hashV = (HashValue)base.expr();
        StringValue stringV = (StringValue)index.expr();
        
        hashV.value().put(stringV.value(),pv);
    }
	
}