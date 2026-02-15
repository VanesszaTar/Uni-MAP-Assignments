package Model.Expressions;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.OperandNotBoolean;
import Model.Exceptions.UnknownOperator;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class LogicalExpr implements IExpression {
    private IExpression e1;
    private IExpression e2;
    private int op;

    public LogicalExpr(int op, IExpression e1, IExpression e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws MyException {
        IValue v1 = e1.evaluate(table, heap);
        if (!v1.getType().equals(new BoolType()))
            throw new OperandNotBoolean();
        IValue v2 = e2.evaluate(table, heap);
        if (!v2.getType().equals(new BoolType()))
            throw new OperandNotBoolean();
        boolean n1 = ((BoolValue)v1).getValue();
        boolean n2 = ((BoolValue)v2).getValue();
        if (op == 1)
            return new BoolValue(n1 && n2);
        else if (op == 2)
            return new BoolValue(n1 || n2);
        else
            throw new UnknownOperator();
    }

    @Override
    public String toString(){
        String sOp;
        switch (op){
            case 1:
                sOp = "&&";
            case 2:
                sOp = "||";
            default:
                sOp = "?";
        }
        return "(" + e1.toString() + " " + sOp + " " + e2.toString() + ")";
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpr(op, e1.deepCopy(), e2.deepCopy());
    }
}
