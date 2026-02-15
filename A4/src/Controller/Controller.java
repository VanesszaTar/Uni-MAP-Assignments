package Controller;

import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.IValue;
import Model.Values.RefValue;
import Repository.IRepository;

import java.util.*;
import java.util.stream.Collectors;

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
            prg.getHeap().setContent(safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    prg.getHeap().getContent()));
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

    @Override
    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(symTableAddr);
        Set<Integer> visited = new HashSet<>();
        boolean changed;
        do{
            changed = false;
            Set<Integer> newRefs = new HashSet<>();
            for (Integer addr: reachable){
                if(!visited.contains(addr)){
                    visited.add(addr);
                    IValue val = heap.get(addr);
                    if(val instanceof RefValue){
                        int refAddr = ((RefValue) val).getAddress();
                        if(!reachable.contains(refAddr)){
                            newRefs.add(refAddr);
                            changed = true;
                        }
                    }
                }
            }
            reachable.addAll(newRefs);
        } while(changed);

        return heap.entrySet().stream()
                .filter(e->reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    @Override
    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
}
