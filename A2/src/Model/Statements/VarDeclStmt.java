package Model.Statements;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeNotFound;
import Model.Exceptions.VariableAlreadyDefined;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        if(symTable.isDefined(name))
            throw new VariableAlreadyDefined(name);
        if(type.equals(new IntType()))
            symTable.put(name, new IntValue(0));
        else if (type.equals(new BoolType()))
            symTable.put(name, new BoolValue(false));
        else
            throw new TypeNotFound();
        return prgState;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }
}
