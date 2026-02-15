package Model.ADT;

import java.util.Stack;

import Model.Exceptions.MyException;
import Model.Exceptions.StackIsEmpty;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<>();
    }

    @Override
    public void push(T v){
        stack.push(v);
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty())
            throw new StackIsEmpty();
        return stack.pop();
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        return stack.toString();
    }
}
