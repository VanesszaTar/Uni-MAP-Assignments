package Model.Statements;
import Model.ADT.IDictionary;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeNotFound;
import Model.Exceptions.VariableNotDefined;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;

public class AssignStmt implements IStmt {
    private String id;
    private IExpression expr;

    public AssignStmt(String id, IExpression expr){
        this.id = id;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        if(!symTable.isDefined(id)){
            throw new VariableNotDefined(id);
        }
        IValue val = expr.evaluate(symTable);
        IType typeId;
        try{
            typeId = symTable.lookup(id).getType();
        } catch (Exception e){
            throw new TypeNotFound();
        }
        if(!val.getType().equals(typeId))
            throw new TypeNotFound();
        try{
            symTable.update(id, val);
        } catch (Exception e){
            throw new MyException(e.getMessage());
        }
        return prgState;
    }

    @Override
    public String toString() {
        return id + " = " + expr.toString();
    }

    @Override
    public IStmt deepCopy(){
        return new AssignStmt(id, expr.deepCopy());
    }
}