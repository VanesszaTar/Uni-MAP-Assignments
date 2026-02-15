package Repository;

import Model.ADT.IFileTable;
import Model.ADT.IHeap;
import Model.ADT.IList;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Values.IValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private PrgState prgState;
    private String logFilePath;
    public Repository(PrgState prgState, String logFilePath) {
        this.prgState = prgState;
        this.logFilePath = logFilePath;
    }
    @Override
    public PrgState getCurrentProgram(){
        return prgState;
    }
    @Override
    public void addProgram(PrgState prgState){ this.prgState = prgState; }
    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println("ExeStack:");
//            IStack<IStmt> stack = prgState.getExeStack();
//            List<IStmt> stmts = new ArrayList<>(stack.toList());
//            for (IStmt stmt : stmts)
//                logFile.println(stmt.toString());
            logFile.println(prgState.getExeStack().fileToString());

            logFile.println("SymTable:");
            prgState.getSymTable().getContent().forEach((k,v) -> logFile.println(k + " --> " + v));

            logFile.println("Out:");
            IList<IValue> out = prgState.getOut();
            for(IValue val : out.getAll())
                logFile.println(val.toString());

            logFile.println("FileTable:");
            IFileTable fileTable = prgState.getFileTable();
            for(var entry : fileTable.getContent().entrySet())
                logFile.println(entry.getKey().getValue());

            logFile.println("Heap:");
            IHeap heap = prgState.getHeap();
            heap.getContent().forEach((k,v) -> logFile.println(k + " --> " + v));
            logFile.println("---------------------------");

        } catch (IOException e){
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }
}
