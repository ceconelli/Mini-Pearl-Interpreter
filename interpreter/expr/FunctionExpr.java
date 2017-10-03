package interpreter.expr;

import java.util.Scanner;

import interpreter.value.Value;
import interpreter.value.StringValue;
import interpreter.value.IntegerValue;

public class FunctionExpr extends Expr {

    private FunctionType type;
    private Expr param;
    private Scanner in;

    public FunctionExpr(FunctionType type, Expr param, int line) {
        super(line);
        this.type = type;
        this.param = param;
        this.in = new Scanner(System.in);
    }

    public Value<?> expr() {
       switch (this.type) {
           case Input:
               return inputExpr();
           // FIXME: All other functions.
           default:
               return null;
       }
    }

    private Value<?> inputExpr() {
        Value<?> v = param.expr();
        if (v instanceof IntegerValue) {
            IntegerValue iv = (IntegerValue) v;
            System.out.print(iv.value());
        } else if (v instanceof StringValue) {
            StringValue sv = (StringValue) v;
            System.out.print(sv.value());
        } else {
            throw new RuntimeException("Implement ListValue and HashValue");
        }

        String str = in.nextLine();
        try {
           int n = Integer.parseInt(str);
           IntegerValue iv = new IntegerValue(n, this.getLine());
           return iv;
        } catch (Exception e) {
           StringValue sv = new StringValue(str, this.getLine());
           return sv;
        }
    }
}
