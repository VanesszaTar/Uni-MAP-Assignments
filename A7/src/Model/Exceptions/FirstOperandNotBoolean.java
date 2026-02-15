package Model.Exceptions;

public class FirstOperandNotBoolean extends MyException {
    public FirstOperandNotBoolean() {
        super("First operand is not boolean!");
    }
}
