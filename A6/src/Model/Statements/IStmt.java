package Model.Statements;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Types.IType;

public interface IStmt {
    PrgState execute(PrgState prgState) throws MyException;
    String toString();
    IStmt deepCopy();
    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException;
}
