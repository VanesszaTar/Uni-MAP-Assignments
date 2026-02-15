package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState prgState) throws MyException;
    String toString();
    IStmt deepCopy();
}
