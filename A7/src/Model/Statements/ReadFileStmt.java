package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.ADT.IHeap;
import Model.Exceptions.*;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private final IExpression exp;
    private final String varName;
    public ReadFileStmt(IExpression exp, String varName){
        this.exp = exp;
        this.varName = varName;
    }
    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IHeap heap = prgState.getHeap();
        if(!symTable.isDefined(varName))
            throw new VariableNotDefined(varName);
        if(!(symTable.lookup(varName).getType() instanceof IntType))
            throw new OperandNotInteger();
        IValue val = exp.evaluate(symTable, heap);
        if(!(val instanceof StringValue))
            throw new ExpressionNotEvaluatingToString();
        String filename = ((StringValue) val).getValue();
        StringValue fileKey = new StringValue(filename);
        IFileTable fileTable = prgState.getFileTable();
        if(!fileTable.isDefined(fileKey))
            throw new FileNotOpened(filename);
        BufferedReader br = fileTable.get(fileKey);
        int result;
        try{
            String line = br.readLine();
            if (line == null)
                result = 0; // EOF
            else
                result = Integer.parseInt(line);
        } catch (IOException e) {
            throw new ErrorReadingFromFile(filename);
        } catch (NumberFormatException e) {
            throw new InvalidIntegerInFile(filename);
        }
        symTable.put(varName, new IntValue(result));
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(varName);
        IType typeexp = exp.typecheck(typeEnv);
        if(typeexp.equals(new StringType()) && typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new DifferentTypes();
    }

    @Override
    public String toString() {
        return "Reading from file " + exp.toString() + " " + varName;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), varName);
    }
}
