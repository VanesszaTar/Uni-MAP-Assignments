package Model.Expressions;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.FirstOperandNotInteger;
import Model.Exceptions.InvalidRelationalOperator;
import Model.Exceptions.MyException;
import Model.Exceptions.SecondOperandNotInteger;
import Model.Types.BoolType;
import Model.Types.IType;
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
            throw new FirstOperandNotInteger();

        IValue v2 = exp2.evaluate(symTable, heap);
        if (!(v2.getType() instanceof IntType))
            throw new SecondOperandNotInteger();

        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();

        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new InvalidRelationalOperator(op);
        };
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType()))
                return new BoolType();
            else
                throw new SecondOperandNotInteger();
        }
        else
            throw new FirstOperandNotInteger();
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
