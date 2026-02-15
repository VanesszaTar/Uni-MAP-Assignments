package Model.ADT;

import Model.Exceptions.MyException;

import java.util.Map;

// for SymTable
public interface IDictionary<K,V> {
    void put(K key, V value);
    V lookup(K key) throws MyException;
    boolean isDefined(K key);
    void update(K key, V value) throws MyException;
    Map<K,V> getContent();
    String toString();
}
