package Model.Expressions;
import Model.Exceptions.VariableNotDefined;
import Model.Values.IValue;
import Model.ADT.IDictionary;
import Model.Exceptions.MyException;

public class VarExpr implements IExpression {
    private String id;

    public VarExpr(String id){
        this.id = id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> table) throws MyException {
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
