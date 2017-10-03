package interpreter.expr;

import interpreter.value.Value;

public abstract class Variable extends SetExpr {

    private String name;
    protected Value<?> value;

    protected Variable(String name) {
        super(-1);

        this.name = name;
        this.value = null;
    }

    public String getName() {
        return name;
    }

    @Override
    public Value<?> expr() {
        return value;
    }

    @Override
    public abstract void setValue(Value<?> value);
}
