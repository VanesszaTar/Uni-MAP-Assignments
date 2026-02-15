package Model.Expressions;

import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.Values.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> table) throws MyException;
    String toString();
    IExpression deepCopy();
}
