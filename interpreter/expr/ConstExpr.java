package interpreter.expr;

import interpreter.value.Value;

public class ConstExpr extends Expr {

    private Value<?> value;

    public ConstExpr(Value<?> value, int line) {
        super(line);
        this.value = value;
    }

    public Value<?> expr() {
        return value;
    }
}
