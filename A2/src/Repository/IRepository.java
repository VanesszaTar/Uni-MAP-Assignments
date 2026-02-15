package Repository;
import Model.PrgState;

public interface IRepository {
    PrgState getCurrentProgram();
    void addProgram(PrgState prgState);
}
