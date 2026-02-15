package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.AddressNotInHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.OperandNotRef;
import Model.Exceptions.VariableNotDefined;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

public class WHStmt implements IStmt {
    private final String varName;
    private final IExpression expression;
    public WHStmt(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IHeap heap = prgState.getHeap();

        if(!symTable.isDefined(varName))
            throw new VariableNotDefined(varName);
        IValue varValue = symTable.lookup(varName);
        if(!(varValue instanceof RefValue))
            throw new OperandNotRef();
        RefValue refValue = (RefValue) varValue;
        int address = refValue.getAddress();

        if(!heap.contains(address))
            throw new AddressNotInHeap(address);
        IValue evaluated = expression.evaluate(symTable, heap);
        IType locationType = refValue.getLocationType();
        if(!evaluated.getType().equals(locationType))
            throw new MyException("Type mismatch: expression type " + evaluated.getType() + " does not match location type " + locationType);
        heap.put(address, evaluated);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(varName);
        IType typeexpr = expression.typecheck(typeEnv);
        if (typevar instanceof RefType){
            RefType refType = (RefType) typevar;
            if (refType.getInner().equals(typeexpr))
                return typeEnv;
            else
                throw new MyException("whStmt: type does not match!");
        }
        else
            throw new MyException("whStmt: variable is not of ref type!");
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
    @Override
    public IStmt deepCopy() {
        return new WHStmt(varName, expression.deepCopy());
    }
}
