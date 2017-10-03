package interpreter.value;

public class StringValue extends PrimitiveValue<String> {

    private String strValue;

    public StringValue(String strValue, int line) {
        super(line);
        this.strValue = strValue;
    }

    public String value() {
        return strValue;
    }
}
