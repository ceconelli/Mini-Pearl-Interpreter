package interpreter.value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashValue extends CompositeValue{
	private Map <String, PrimitiveValue<?>> hashValue;

	public HashValue(Map<String, PrimitiveValue<?>> hashValue, int line) {
		super(line);
		this.hashValue = hashValue;
	}

	public Map <String, PrimitiveValue<?>> value(){
		return hashValue;
	}
	
	public List<String> keys(){
		List<String> list = new ArrayList<String>(hashValue.keySet());
		return list;
	}
	
	public List<PrimitiveValue<?>> values(){
		List<PrimitiveValue<?>> list = new ArrayList<PrimitiveValue<?>>(hashValue.values());
		return list;
	}
	
	
}
