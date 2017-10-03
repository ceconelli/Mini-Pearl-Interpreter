package interpreter.command;

import interpreter.boolexpr.*;

public class WhileCommand extends Command{
	
	private BoolExpr expr;
	private Command cmd;
	
	public WhileCommand(int line, BoolExpr expr, Command cmd) {
		super(line);
		this.expr = expr;
		this.cmd = cmd;
	}
	
	public void execute(){
		while(expr.expr())
			cmd.execute();
	}
	
}
