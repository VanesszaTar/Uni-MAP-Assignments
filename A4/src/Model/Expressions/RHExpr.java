package Model.Expressions;


import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Values.IValue;
import Model.Values.RefValue;

public class RHExpr implements IExpression {
    private final IExpression exp;
    public RHExpr(IExpression exp) {
        this.exp = exp;
    }
    @Override
    public IValue evaluate(IDictionary<String,IValue> table, IHeap heap) throws MyException {
        IValue value = exp.evaluate(table, heap);
        if(!(value instanceof RefValue))
            throw new MyException("RH argument is not a RefValue!");
        RefValue refValue = (RefValue) value;
        int address = refValue.getAddress();
        if(!heap.contains(address))
            throw new MyException("Heap does not contain address " + address);
        return heap.get(address);
    }
    @Override
    public String toString() {
        return "rH(" +  exp.toString() + ")";
    }
    @Override
    public IExpression deepCopy() {
        return new RHExpr(exp.deepCopy());
    }
}
