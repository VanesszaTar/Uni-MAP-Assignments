package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Values.IValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IController {
    PrgState oneStep(PrgState prgState) throws MyException;
    void allSteps() throws MyException;
    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap);
    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues);
}
