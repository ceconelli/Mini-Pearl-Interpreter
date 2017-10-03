package interpreter.value;

public abstract class CompositeValue<T> extends Value<T> {

    protected CompositeValue(int line) {
        super(line);
    }
    
    public abstract T value();
}
