package Model.Statements;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.Exceptions.VariableAlreadyDefined;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;

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
        symTable.put(name, type.defaultValue());
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
