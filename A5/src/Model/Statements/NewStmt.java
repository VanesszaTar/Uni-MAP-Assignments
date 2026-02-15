package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.OperandNotRef;
import Model.Exceptions.VariableNotDefined;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

public class NewStmt implements IStmt {
    private final String varName;
    private final IExpression expression;

    public NewStmt(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IHeap heap = prgState.getHeap();

        if (!symTable.isDefined(varName))
            throw new VariableNotDefined(varName);
        IValue value = symTable.lookup(varName);
        if (!(value.getType() instanceof RefType refType))
            throw new OperandNotRef();
        IValue evaluated = expression.evaluate(symTable, heap);
        if (!evaluated.getType().equals(refType.getInner()))
            throw new MyException("Type mismatch: expression type does not match reference inner type.");
        int newAddress = heap.allocate(evaluated);
        symTable.update(varName, new RefValue(newAddress, refType.getInner()));
        return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, expression.deepCopy());
    }
}
