package interpreter.expr;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException() {
    }

    public InvalidValueException(String msg) {
        super(msg);
    }
}
