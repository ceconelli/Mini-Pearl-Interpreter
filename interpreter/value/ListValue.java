package interpreter.value;

import java.util.List;

public class ListValue extends CompositeValue<Object>{
    List<PrimitiveValue<?>> listValue;

    public ListValue(List<PrimitiveValue<?>> listValue,int line) {
        super(line);
        this.listValue = listValue;
    }

    public List<PrimitiveValue<?>> value(){
        return this.listValue;
    }
    
    public void push(PrimitiveValue<?> value){
        this.listValue.add(value);
    }
    
    public void push(List<PrimitiveValue<?>> values){
        for(PrimitiveValue<?> value:values){
            this.listValue.add(value);
        }
    }
    
    public PrimitiveValue<?> pop(){
        return this.listValue.remove(0);
    }
    
    public void unshift(PrimitiveValue<?> value){
        this.listValue.add(0, value);
    }
    
    public void  unshift(List<PrimitiveValue<?>> values){
    	this.listValue.addAll(0, values);
    }
    
    public PrimitiveValue<?> shift(){
    	return this.listValue.remove(0);
    }
}
