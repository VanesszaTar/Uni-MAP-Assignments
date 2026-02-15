package Model.Exceptions;

public class FirstOperandNotInteger extends MyException {
    public FirstOperandNotInteger() {
        super("First operand is not an integer!");
    }
}
