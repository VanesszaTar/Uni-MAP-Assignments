package Controller;

import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.IStmt;
import Repository.IRepository;

public class Controller implements IController {
    private IRepository repo;
    private boolean displayFlag;
    public Controller(IRepository repo){
        this.repo = repo;
    }
    @Override
    public PrgState oneStep(PrgState prgState) throws MyException {
        IStack<IStmt> stack = prgState.getExeStack();
        if(stack.isEmpty())
            throw new MyException("PrgState is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(prgState);
    }
    @Override
    public void allSteps() throws MyException {
        PrgState prg = repo.getCurrentProgram();
        repo.logPrgStateExec();
        //if(displayFlag)
            //System.out.println("PrgState: " + prg.toString());
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
            repo.logPrgStateExec();
            //if(displayFlag)
               // System.out.println("PrgState: " + prg.toString());
        }
        //if(!displayFlag)
            //System.out.println("PrgState: " + prg.toString());
    }
    public void setDisplayFlag(boolean flag){
        this.displayFlag = flag;
    }
}
