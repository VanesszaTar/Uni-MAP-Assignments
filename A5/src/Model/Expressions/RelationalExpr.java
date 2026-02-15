package Model.Expressions;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;

public class RelationalExpr implements IExpression {
    private final IExpression exp1;
    private final IExpression exp2;
    private final String op;

    public RelationalExpr(IExpression exp1, IExpression exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }
    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        IValue v1 = exp1.evaluate(symTable, heap);
        if (!(v1.getType() instanceof IntType))
            throw new MyException("First operand is not an integer!");

        IValue v2 = exp2.evaluate(symTable, heap);
        if (!(v2.getType() instanceof IntType))
            throw new MyException("Second operand is not an integer!");

        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();

        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new MyException("Invalid relational operator: " + op);
        };
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpr(exp1.deepCopy(), exp2.deepCopy(), op);
    }
}
