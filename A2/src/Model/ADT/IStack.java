package Model.ADT;
import Model.Exceptions.MyException;

// for exeStack
public interface IStack<T> {
    void push(T v);
    T pop() throws MyException;
    boolean isEmpty();
    String toString();
}
