package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;

public interface IController {
    PrgState oneStep(PrgState prgState) throws MyException;
    void allSteps() throws MyException;
}
