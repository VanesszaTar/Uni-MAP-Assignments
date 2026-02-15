package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.IType;

public class CompStmt implements IStmt {
    public IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IStack<IStmt> stack = prgState.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString(){
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
