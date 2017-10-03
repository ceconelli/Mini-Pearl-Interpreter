package interpreter.boolexpr;

public abstract class BoolExpr {
	private int line;
	
	protected BoolExpr (int line){
		this.line = line;
	}
	
	protected int getLine(){
		return this.line;
	}
	
	public abstract boolean expr();
}
