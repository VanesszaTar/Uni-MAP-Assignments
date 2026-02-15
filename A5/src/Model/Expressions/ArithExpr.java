package Model.Expressions;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.DivisionByZero;
import Model.Exceptions.MyException;
import Model.Exceptions.OperandNotInteger;
import Model.Exceptions.UnknownOperator;
import Model.Types.IntType;
import Model.Values.IValue;
import Model.Values.IntValue;

public class ArithExpr implements IExpression {
    private IExpression e1, e2;
    private int op;

    public ArithExpr(int op, IExpression e1, IExpression e2) {
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws MyException {
        IValue v1 = e1.evaluate(table,heap);
        if (!v1.getType().equals(new IntType())) throw new OperandNotInteger();
        IValue v2 = e2.evaluate(table,heap);
        if (!v2.getType().equals(new IntType())) throw new OperandNotInteger();
        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();
        switch (op) {
            case 1:
                return new IntValue(n1 + n2);
            case 2:
                return new IntValue(n1 - n2);
            case 3:
                return new IntValue(n1 * n2);
            case 4:
                if (n2 == 0)
                    throw new DivisionByZero();
                return new IntValue(n1 / n2);
            default:
                throw new UnknownOperator();
        }
    }

    @Override
    public String toString(){
        String sOp;
        switch (op) {
            case 1:
                sOp = "+";
            case  2:
                sOp = "-";
            case 3:
                sOp = "*";
            case  4:
                sOp = "/";
            default:
                sOp = "?";
        }
        return "(" + e1.toString() + " " + sOp + " " + e2.toString() + ")";
    }

    @Override
    public IExpression deepCopy() {
        return new ArithExpr(op, e1.deepCopy(), e2.deepCopy());
    }
}
