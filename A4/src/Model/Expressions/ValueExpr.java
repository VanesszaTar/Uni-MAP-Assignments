package Model.Expressions;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Values.IValue;
public class ValueExpr implements IExpression {
    private IValue value;

    public ValueExpr(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate (IDictionary<String, IValue> table, IHeap heap) throws MyException {
        return value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpr(value.deepCopy());
    }
}
