package interpreter.expr;

import interpreter.value.Value;

public abstract class IndexExpr extends SetExpr{
	
	protected Expr base;
	protected Expr index;
	
	protected IndexExpr(Expr base, Expr index, int line) {
		super(line);
		this.base = base;
		this.index = index;
	}
	
	public abstract Value<?> expr();
	
	public abstract void setValue(Value<?> value);
	
}
