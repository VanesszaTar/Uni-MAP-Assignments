package Model.Exceptions;

public class OperandNotInteger extends MyException {
    public OperandNotInteger() {
        super("This operand is not an integer!");
    }
}
