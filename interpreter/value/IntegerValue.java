package interpreter.value;

public class IntegerValue extends PrimitiveValue<Integer> {

    private Integer intValue;

    public IntegerValue(Integer intValue, int line) {
        super(line);
        this.intValue = intValue;
    }

    public Integer value() {
        return intValue;
    }
}
