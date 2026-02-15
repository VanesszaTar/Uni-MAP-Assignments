package Model;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.ADT.IList;
import Model.ADT.IStack;
import Model.Statements.IStmt;
import Model.Values.IValue;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IFileTable fileTable;
    private IStmt originalProgram;

    public PrgState(IStack<IStmt> stack, IDictionary<String, IValue> symTable, IList<IValue> out, IFileTable fileTable, IStmt originalProgram){
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.exeStack.push(originalProgram);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IFileTable getFileTable() {
        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ExeStack: ").append(exeStack.toString()).append("\n");
        sb.append("SymTable: ").append(symTable.toString()).append("\n");
        sb.append("Out: ").append(out.toString()).append("\n");
        sb.append("FileTable: ").append(fileTable.toString()).append("\n");
        sb.append("Program: ").append(originalProgram.toString()).append("\n");
        return sb.toString();
    }
}
