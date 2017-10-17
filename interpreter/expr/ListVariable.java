package interpreter.expr;

import interpreter.value.Value;
import interpreter.value.CompositeValue;

public class ListVariable extends Variable {

    public ListVariable(String name) {
        super(name);
    }

    public void setValue(Value<?> value) {
        if (value instanceof CompositeValue)
            super.value = value;
        else
            throw new InvalidValueException("valor inválido");
    }
}
