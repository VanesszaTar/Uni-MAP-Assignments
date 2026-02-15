package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;

public class NopStmt implements IStmt {
    public NopStmt() {}

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        return null;
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
