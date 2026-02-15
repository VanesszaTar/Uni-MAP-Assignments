package Model.Expressions;
import Model.Values.IValue;
import Model.ADT.IDictionary;
import Model.Exceptions.MyException;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> table) throws MyException;
    String toString();
    IExpression deepCopy();
}
