package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.ADT.IHeap;
import Model.Exceptions.ErrorOpeningFile;
import Model.Exceptions.ExpressionNotEvaluatingToString;
import Model.Exceptions.FileAlreadyOpened;
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
            throw new ExpressionNotEvaluatingToString();
        StringValue filename = (StringValue) val;
        IFileTable fileTable = prgState.getFileTable();
        if(fileTable.isDefined(filename))
            throw new FileAlreadyOpened(filename.getValue());
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(filename.getValue()));
        } catch (IOException e){
            throw new ErrorOpeningFile(filename.getValue());
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
            throw new ExpressionNotEvaluatingToString();
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
