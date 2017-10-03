package interpreter.value;

public abstract class Value<T> {

    private int line;

    protected Value(int line) {
        this.line = line;
    }  

    public int getLine() {
        return line;
    }

    public abstract T value();
}
