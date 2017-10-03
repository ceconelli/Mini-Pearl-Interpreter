package interpreter.value;

import java.util.List;

public class ListValue {
    List<PrimitiveValue<?>> listValue;

    public ListValue() {
        
    }
    
    public List<PrimitiveValue<?>> value(){
        return this.listValue;
    }
    
    public void push(PrimitiveValue<?> value){
        
    }
    
}
