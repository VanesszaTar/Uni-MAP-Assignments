package Model.Statements;

import Model.ADT.*;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;


public class ForkStmt implements IStmt {
    private IStmt stmt;
    public ForkStmt(IStmt stmt) { this.stmt = stmt; }
    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        MyStack<IStmt> newStack = new MyStack<>();
        newStack.push(stmt);

        IDictionary<String, IValue> newSymTable = prgState.getSymTable().deepCopy();
        IHeap heap = prgState.getHeap();
        IFileTable fileTable = prgState.getFileTable();
        IList<IValue> out = prgState.getOut();
        PrgState newPrg = new PrgState(newStack, newSymTable, out, fileTable, heap, null);
        return newPrg;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
