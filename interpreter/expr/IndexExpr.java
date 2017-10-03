package interpreter.expr;

import interpreter.value.Value;

public abstract class IndexExpr extends SetExpr{
	
	protected Expr base;
	protected Expr index;
	
	protected IndexExpr(int line, Expr base, Expr index) {
		super(line);
		this.base = base;
		this.index = index;
	}
	
	public abstract Value<?> expr();
	
	
	
}
