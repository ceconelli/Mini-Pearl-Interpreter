package interpreter.expr;

import interpreter.value.Value;
import interpreter.value.CompositeValue;

public class HashVariable extends Variable {

    public HashVariable(String name) {
        super(name);
    }

    public void setValue(Value<?> value) {
        if (value instanceof CompositeValue)
            super.value = value;
        else
            throw new InvalidValueException("valor inválido");
    }
}
