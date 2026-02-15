package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IFileTable;
import Model.Exceptions.MyException;
import Model.Exceptions.OperandNotInteger;
import Model.Exceptions.VariableNotDefined;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.IntType;
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
        if(!symTable.isDefined(varName))
            throw new VariableNotDefined(varName);
        if(!(symTable.lookup(varName).getType() instanceof IntType))
            throw new OperandNotInteger();
        IValue val = exp.evaluate(symTable);
        if(!(val instanceof StringValue))
            throw new MyException("Expression " + exp + " does not evaluate to a string");
        String filename = ((StringValue) val).getValue();
        StringValue fileKey = new StringValue(filename);
        IFileTable fileTable = prgState.getFileTable();
        if(!fileTable.isDefined(fileKey))
            throw new MyException("Expression " + exp + " does not evaluate to a file");
        BufferedReader br = fileTable.get(fileKey);
        int result;
        try{
            String line = br.readLine();
            if (line == null)
                result = 0; // EOF
            else
                result = Integer.parseInt(line);
        } catch (IOException e) {
            throw new MyException("Error reading from file " + filename);
        } catch (NumberFormatException e) {
            throw new MyException("Invalid integer in file " + filename);
        }
        symTable.put(varName, new IntValue(result));
        return prgState;
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
