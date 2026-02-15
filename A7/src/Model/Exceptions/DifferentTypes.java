package Model.Exceptions;

public class DifferentTypes extends MyException {
    public DifferentTypes() {
        super("Type mismatch: right hand side and left hand side have different types!");
    }
}
