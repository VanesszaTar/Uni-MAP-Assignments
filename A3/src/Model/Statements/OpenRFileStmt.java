package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.Exceptions.MyException;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    private IExpression exp;
    public OpenRFileStmt(IExpression exp){
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IValue val = exp.evaluate(symTable);
        if(!val.getType().equals(new StringType()))
            throw new MyException("OpenRFileStmt: expression is not of type string!");
        StringValue filename = (StringValue) val;
        IFileTable fileTable = prgState.getFileTable();
        if(fileTable.isDefined(filename))
            throw new MyException("OpenRFileStmt: File already opened: " + filename.getValue());
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(filename.getValue()));
        } catch (IOException e){
            throw new MyException("OpenRFileStmt: Error opening file: " + filename.getValue());
        }
        fileTable.put(filename,br);
        return prgState;
    }
    @Override
    public String toString(){
        return "OpenRFile(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
}
