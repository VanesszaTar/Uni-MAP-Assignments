package Controller;

import Model.ADT.IStack;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.IValue;
import Model.Values.RefValue;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    private IRepository repo;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repo){
        this.repo = repo;
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

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException{
        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> p.oneStep()))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return null;
                        } catch (ExecutionException e) {
                            Throwable cause = e.getCause();
                            if (cause instanceof MyException me)
                                throw new RuntimeException(me);
                            else
                                throw new RuntimeException(cause);
                        }
                    }).filter(p -> p!=null).collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> repo.logPrgStateExec(prg));
        repo.setPrgList(prgList);
    }

    @Override
    public void allSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size()>0){
            List<Integer> symTableAddresses = prgList.stream()
                    .flatMap(p -> getAddrFromSymTable(p.getSymTable().getContent().values()).stream())
                    .collect(Collectors.toList());

            if (!prgList.isEmpty()) {
                prgList.get(0).getHeap().setContent(
                        safeGarbageCollector(symTableAddresses, prgList.get(0).getHeap().getContent())
                );
            }
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }
}

