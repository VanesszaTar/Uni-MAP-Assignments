package Model.Expressions;


import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.AddressNotInHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.RHArgumentNotRefType;
import Model.Exceptions.RHArgumentNotRefValue;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

// dereferencing a reference and read the value stored in the heap
public class RHExpr implements IExpression {
    private final IExpression exp;
    public RHExpr(IExpression exp) {
        this.exp = exp;
    }
    @Override
    public IValue evaluate(IDictionary<String,IValue> table, IHeap heap) throws MyException {
        IValue value = exp.evaluate(table, heap);
        if(!(value instanceof RefValue))
            throw new RHArgumentNotRefValue();
        RefValue refValue = (RefValue) value;
        int address = refValue.getAddress();
        if(!heap.contains(address))
            throw new AddressNotInHeap(address);
        return heap.get(address);
    }
    @Override
    public IType typecheck(IDictionary<String,IType> typeEnv) throws MyException {
        IType type = exp.typecheck(typeEnv);
        if (type instanceof RefType){
            // dereferencing a reference returns the inner type
            RefType reft = (RefType) type;
            return reft.getInner();
        }
        else
            throw new RHArgumentNotRefType();
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
