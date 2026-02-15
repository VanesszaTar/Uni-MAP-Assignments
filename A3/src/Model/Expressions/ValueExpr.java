package Model.Expressions;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.Values.IValue;
public class ValueExpr implements IExpression {
    private IValue value;

    public ValueExpr(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate (IDictionary<String, IValue> table) throws MyException {
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
