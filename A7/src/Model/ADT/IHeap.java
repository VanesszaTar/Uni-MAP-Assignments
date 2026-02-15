package Model.ADT;

import Model.Values.IValue;

import java.util.Map;

public interface IHeap {
    int allocate(IValue value);
    IValue get(int address);
    void put(int address, IValue value);
    boolean contains(int address);
    void remove(int address);
    void setContent(Map<Integer, IValue> content);
    Map<Integer, IValue> getContent();
}
