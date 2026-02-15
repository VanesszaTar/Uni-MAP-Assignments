package Model.ADT;

import Model.Exceptions.MyException;
import Model.Exceptions.StackIsEmpty;
import Model.Statements.CompStmt;
import Model.Statements.IStmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// execution stack
public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<>();
    }
    public MyStack(Stack<T> stack){this.stack = stack;}
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
    public List<T> toList(){
        List<T> list = new ArrayList<>(stack); // stack top - at the end; we want top -> bottom
        java.util.Collections.reverse(list); // to get elements from stack in the correct order
        return list;
    }

    @Override
    public String toString(){
        return stack.toString();
    }

    @Override
    public String fileToString(){
        Stack<IStmt> stack = (Stack<IStmt>) this.stack.clone(); // not destroying the real execution stack
        MyStack<IStmt> copy = new MyStack<>(stack);
        StringBuilder sb = new StringBuilder();
        while(!copy.isEmpty()){
            IStmt stmt = copy.pop();
            if (stmt instanceof CompStmt){
                IStmt first = ((CompStmt) stmt).first;
                IStmt second = ((CompStmt) stmt).second;
                copy.push(second);
                copy.push(first); // last in, first out
            }
            else
                sb.append(stmt.toString()).append("\n");
        }
        return sb.toString();
    }
}
