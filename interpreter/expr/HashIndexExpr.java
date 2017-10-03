package interpreter.expr;
import interpreter.value.Value;

public class HashIndexExpr extends IndexExpr{

	public HashIndexExpr(Expr base, Expr index, int line) {
		super(base, index, line);
	}
	
	public Value<?> expr(){
		Value baseV = base.expr();
		Value indexV = index.expr();
		
		if(baseV.value() instanceof String )
		
	}

	
	
}
