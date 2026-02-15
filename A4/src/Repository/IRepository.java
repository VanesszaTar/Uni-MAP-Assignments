package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;
public interface IRepository {
    PrgState getCurrentProgram();
    void addProgram(PrgState prgState);
    void logPrgStateExec() throws MyException;
}
