package Model.ADT;

import Model.Exceptions.MyException;

import java.util.List;

// for exeStack
public interface IStack<T> {
    void push(T v);
    T pop() throws MyException;
    boolean isEmpty();
    List<T> toList();
    String toString();
    String fileToString();
}
