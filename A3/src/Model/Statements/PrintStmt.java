package Model.Statements;

import Model.ADT.IDictionary;
import Model.ADT.IList;
import Model.Exceptions.MyException;
import Model.Expressions.IExpression;
import Model.PrgState;
import Model.Values.IValue;

public class PrintStmt implements IStmt {
    private IExpression expr;

    public PrintStmt(IExpression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IDictionary<String, IValue> symTable = prgState.getSymTable();
        IValue v = expr.evaluate(symTable);
        IList<IValue> out = prgState.getOut();
        out.add(v);
        return prgState;
    }

    @Override
    public String toString() {
        return "Print(" + expr.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expr.deepCopy());
    }
}
