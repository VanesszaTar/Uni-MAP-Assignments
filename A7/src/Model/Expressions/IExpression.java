package Model.Expressions;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IType;
import Model.Values.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws MyException;
    String toString();
    IExpression deepCopy();
    IType typecheck(IDictionary<String, IType> typeEnv) throws MyException;
}
