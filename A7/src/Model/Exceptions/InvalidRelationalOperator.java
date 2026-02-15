package Model.Exceptions;

public class InvalidRelationalOperator extends MyException {
    public InvalidRelationalOperator(String operator) {
        super(operator + "is an invalid relational operator!");
    }
}
