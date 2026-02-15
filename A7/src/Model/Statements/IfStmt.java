package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.IStack;
import Model.Exceptions.ConditionNotBoolean;
import Model.Exceptions.MyException;
import Model.Exceptions.UnknownOperator;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class IfStmt implements IStmt {
    private IExpression expr;
    private IStmt thenStmt, elseStmt;

    public IfStmt(IExpression expr, IStmt thenStmt, IStmt elseStmt) {
        this.expr = expr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IHeap heap = prgState.getHeap();
        IValue condition = expr.evaluate(symTable, heap);
        if (!condition.getType().equals(new BoolType()))
            throw new UnknownOperator();
        boolean b = ((BoolValue)condition).getValue();
        IStack<IStmt> stack = prgState.getExeStack();
        if (b)
            stack.push(thenStmt);
        else
            stack.push(elseStmt);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typeexp =  expr.typecheck(typeEnv);
        if (typeexp.equals(new BoolType())){
            thenStmt.typecheck(typeEnv.deepCopy());
            elseStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new ConditionNotBoolean();
    }

    @Override
    public String toString(){
        return "(IF" + expr.toString() + ") THEN(" + thenStmt.toString() + ") ELSE(" + elseStmt.toString() + "))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expr.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }
}
