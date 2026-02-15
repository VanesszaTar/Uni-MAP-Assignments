package Model.Exceptions;

public class ExpressionNotEvaluatingToString extends MyException {
    public ExpressionNotEvaluatingToString() {
        super("The expression is not evaluated to a string!");
    }
}
