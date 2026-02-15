package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private List<T> list;

    public MyList(){
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T v){
        list.add(v);
    }

    @Override
    public List<T> getAll(){
        return list;
    }

    @Override
    public String toString(){
        return list.toString();
    }
}
