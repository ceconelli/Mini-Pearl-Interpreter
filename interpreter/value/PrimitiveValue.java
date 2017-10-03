package interpreter.value;

public abstract class PrimitiveValue<T> extends Value<T> {

    protected PrimitiveValue(int line) {
        super(line);
    }

    public abstract T value();
}
