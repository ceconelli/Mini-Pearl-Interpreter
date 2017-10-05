package interpreter.expr;
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
		Value baseV = (Value)base.expr();
		Value indexV = (Value)index.expr();
		
                HashValue hashV = (HashValue)base.expr();
                StringValue stringV = (StringValue)index.expr();
		return hashV.value().get(stringV.value());
		
	}

        @Override
        public void setValue(Value<?> value) {
            PrimitiveValue pv = (PrimitiveValue)value;
            HashValue hashV = (HashValue)base.expr();
            StringValue stringV = (StringValue)index.expr();
            hashV.value().put(stringV.value(),pv);
        }

	
	
}