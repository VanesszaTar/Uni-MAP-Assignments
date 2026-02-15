package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Values.IValue;
import Repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IController {
    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap);
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues);
    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException;
    void allSteps() throws InterruptedException;
    IRepository getRepository();
    void oneStepGUI() throws MyException, InterruptedException;
}
