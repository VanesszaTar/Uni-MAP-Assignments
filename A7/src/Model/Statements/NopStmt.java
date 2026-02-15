package Model.Statements;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.IType;

public class NopStmt implements IStmt {
    public NopStmt() {}

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "nop";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
