package interpreter.expr;

import interpreter.value.Value;
import interpreter.value.PrimitiveValue;

public class ScalarVariable extends Variable {

    public ScalarVariable(String name) {
        super(name);
    }

    public void setValue(Value<?> value) {
        if (value instanceof PrimitiveValue)
            super.value = value;
        else
            throw new InvalidValueException("valor inválido");
    }
}
