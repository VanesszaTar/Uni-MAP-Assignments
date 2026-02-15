package Model.ADT;

import Model.Values.IValue;
import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IHeap{
    private Map<Integer, IValue> heap;
    private int freeLocation;
    public MyHeap(){
        this.heap = new HashMap<>();
        this.freeLocation = 1;
    }
    private int getFreeLocation(){
        while(heap.containsKey(freeLocation))
            freeLocation++;
        return freeLocation;
    }
    @Override
    public int allocate(IValue value){
        int address = getFreeLocation();
        heap.put(address, value);
        return address;
    }
    @Override
    public IValue get(int address){
        return heap.get(address);
    }
    @Override
    public void put(int address, IValue value){
        heap.put(address, value);
    }
    @Override
    public boolean contains(int address){
        return heap.containsKey(address);
    }
    @Override
    public void remove(int address){
        heap.remove(address);
    }
    @Override
    public void setContent(Map<Integer, IValue> newContent){
        this.heap = newContent;
    }
    @Override
    public Map<Integer, IValue> getContent(){
        return heap;
    }
    @Override
    public String toString(){
        return heap.toString();
    }
}
