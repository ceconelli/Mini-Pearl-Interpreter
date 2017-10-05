package interpreter.value;

import java.util.List;

public class ListValue extends CompositeValue{
    List<PrimitiveValue<?>> listValue;

    public ListValue(int line) {
        super(line);
    }

    public List<PrimitiveValue<?>> value(){
        return this.listValue;
    }
    
    public void push(PrimitiveValue<?> value){
        this.listValue.add(value);
    }
    
    public void push(List<PrimitiveValue<?>> values){
        for(PrimitiveValue value:values){
            this.listValue.add(value);
        }
    }
    
    public PrimitiveValue<?> pop(){
        return this.listValue.remove(0);
    }
    
    public void  unshift(PrimitiveValue<?> value){
        
    }
    
    public void  unshift(List<PrimitiveValue<?>> values){
        
    }
    
    public PrimitiveValue<?> shift(){
        
    }
}
