package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private final IExpression exp;
    public CloseRFileStmt(IExpression exp){
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState prgState) {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IHeap heap = prgState.getHeap();
        IValue val = exp.evaluate(symTable,heap);
        if(!(val instanceof StringValue))
            throw new MyException("Expression " + exp + "does not evaluate to a string!");
        StringValue fileKey = (StringValue) val;
        IFileTable fileTable = prgState.getFileTable();
        if(!fileTable.isDefined(fileKey))
            throw new MyException("File " + fileKey.getValue() + " is not opened!");
        BufferedReader br = fileTable.get(fileKey);
        try{
            br.close();
        } catch(IOException e){
            throw new MyException("Error closing file " + fileKey.getValue());
        }
        fileTable.remove(fileKey);
        return null;
    }
    @Override
    public String toString(){
        return "Closing file " + exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }
}
