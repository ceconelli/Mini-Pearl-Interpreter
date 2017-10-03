package interpreter.command;

import interpreter.expr.Expr;
import interpreter.value.Value;
import interpreter.value.IntegerValue;

public class PrintCommand extends ActionCommand {

    private Expr expr;
    private boolean newLine;

    public PrintCommand(Expr expr, int line) {
        super(line);
        this.expr = expr;
        this.newLine = false;
    }

    public PrintCommand(boolean newLine, int line) {
        super(line);
        this.expr = null;
        this.newLine = newLine;
    }

    public PrintCommand(Expr expr, boolean newLine, int line) {
        super(line);
        this.expr = expr;
        this.newLine = newLine;
    }

    public void execute() {
        if (expr != null) {
            Value<?> value = expr.expr();
            if (value instanceof IntegerValue) {
                IntegerValue iv = (IntegerValue) value;
                System.out.print(iv.value());
//            } else if (value instanceof ...) {
            } else {
              // Erro semantico: operacao invalida, mostrar mensagem e abortar.
            }
        }

        if (newLine)
            System.out.println();

        // Implement me!
    }

}
