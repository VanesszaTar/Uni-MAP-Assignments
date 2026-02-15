package Model.Expressions;

import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.VariableNotDefined;
import Model.Values.IValue;

public class VarExpr implements IExpression {
    private String id;

    public VarExpr(String id){
        this.id = id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table, IHeap heap) throws MyException {
        try{
            return table.lookup(this.id);
        } catch (Exception e){
            throw new VariableNotDefined(this.id);
        }
    }

    @Override
    public String toString(){
        return this.id;
    }

    @Override
    public IExpression deepCopy() {
        return new VarExpr(id);
    }
}
