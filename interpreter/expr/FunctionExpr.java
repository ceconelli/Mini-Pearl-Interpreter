package interpreter.expr;

import interpreter.value.HashValue;
import java.util.Scanner;

import interpreter.value.Value;
import interpreter.value.StringValue;
import interpreter.value.IntegerValue;
import interpreter.value.ListValue;
import interpreter.value.PrimitiveValue;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
           case Size:
               return sizeExpr();
           case Sort:
//               return sortExpr();
           case Reverse:
//               return reverseExpr();
           case Keys:
//               return keysExpr();
           case Values:
//               return valuesExpr();
           case Empty:
//               return emptyExpr();
           case Pop:
//               return popExpr();
           case Shift:
//               return shiftExpr();
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
    
    private Value<?> sizeExpr(){
        Value<?> v = this.param.expr();
        int size = 0;
        if(v instanceof ListValue){
            ListValue listV = (ListValue)v.value();
            size = listV.value().size();
        }
        else if (v instanceof HashValue){
            HashValue hashV = (HashValue)v.value();
            size = hashV.value().size();
        }
        return new IntegerValue(size,param.getLine());
        
    }
    
    private Value<?> sortExpr(){
        List<PrimitiveValue<?>> values;
        Value<?> v = this.param.expr();
        if(v instanceof ListValue){
            ListValue listV = (ListValue)v.value();
            values = listV.value();
//            Collections.sort(values.);
            return new ListValue(values,param.getLine());
        }
        return null;
    }
    
    private Value<?> reverseExpr(){
        Value<?> v = this.param.expr();
        if(v instanceof ListValue){
            ListValue listV = (ListValue)v.value();
            Collections.reverse(listV.value());
            return new ListValue(listV.value(),param.getLine());
        }
        return null;
    }
    
    private Value<?> keysExpr(){
        Value<?> v = this.param.expr();
        if(v instanceof HashValue){
            HashValue hashV = (HashValue)v.value();
            
        }
    }
}
