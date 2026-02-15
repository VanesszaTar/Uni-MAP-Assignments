package Model.ADT;

import Model.Exceptions.MyException;
import Model.Exceptions.VariableAlreadyDefined;
import Model.Exceptions.VariableNotDefined;
import Model.Values.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V> {
    private Map<K,V> dict;

    public MyDictionary(){
        this.dict = new HashMap<>();
    }

    @Override
    public void put(K key, V value){
        dict.put(key, value);
    }

    @Override
    public V lookup(K key) throws MyException {
        if (!dict.containsKey(key)) {
            String k = key.toString();
            throw new VariableNotDefined(k);
        }
        return dict.get(key);
    }

    @Override
    public boolean isDefined(K key){
        return dict.containsKey(key);
    }

    @Override
    public void update(K key, V value) throws MyException {
        if(!dict.containsKey(key)) {
            String k = key.toString();
            throw new VariableAlreadyDefined(k);
        }
        dict.put(key, value);
    }

    @Override
    public Map<K,V> getContent(){
        return dict;
    }

    @Override
    public String toString(){
        return dict.toString();
    }

    @Override
    public IDictionary<K, V> deepCopy() {
        IDictionary<K, V> copy = new MyDictionary<>();
        for (Map.Entry<K, V> entry : dict.entrySet()) {
            V valueCopy;
            if (entry.getValue() instanceof IValue) {
                valueCopy = (V) ((IValue) entry.getValue()).deepCopy();
            } else {
                valueCopy = entry.getValue();
            }
            copy.put(entry.getKey(), valueCopy);
        }
        return copy;
    }

}
