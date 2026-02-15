package Model.ADT;
import java.util.List;

// for out
public interface IList<T> {
    void add(T v);
    List<T> getAll();
    String toString();
}
