package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.IType;
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
        IHeap heap = prgState.getHeap();
        IValue val = exp.evaluate(symTable,heap);
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
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typeexp = exp.typecheck(typeEnv);
        if (typeexp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenRFileStmt: Expression is not a string!");
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
