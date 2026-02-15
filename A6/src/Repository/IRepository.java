package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;

import java.util.List;

public interface IRepository {
    void addProgram(PrgState prgState);
    void logPrgStateExec(PrgState prgState) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newPrgList);
}
