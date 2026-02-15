package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.ADT.IStack;
import Model.Exceptions.ConditionNotBoolean;
import Model.Exceptions.MyException;
import Model.Exceptions.OperandNotBoolean;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class WhileStmt implements IStmt{
    private IExpression condition;
    private IStmt body;
    public WhileStmt(IExpression condition, IStmt body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IHeap heap = prgState.getHeap();
        IValue condValue = condition.evaluate(symTable,heap);
        if(!(condValue instanceof BoolValue))
            throw new OperandNotBoolean();
        BoolValue boolValue = (BoolValue) condValue;
        if(boolValue.getValue()) {
            IStack<IStmt> stack = prgState.getExeStack();
            stack.push(this); // for looping
            stack.push(body);
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType typeexp = condition.typecheck(typeEnv);
        if(typeexp.equals(new BoolType())){
            body.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new ConditionNotBoolean();
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(condition.deepCopy(), body.deepCopy());
    }
    @Override
    public String toString() {
        return "while(" + condition.toString() + ") " + body.toString();
    }
}
