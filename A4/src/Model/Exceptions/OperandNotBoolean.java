package Model.Exceptions;

public class OperandNotBoolean extends MyException {
    public OperandNotBoolean() {
        super("This operand is not a boolean!");
    }
}
