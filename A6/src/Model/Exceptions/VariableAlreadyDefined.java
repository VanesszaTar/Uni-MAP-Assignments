package Model.Exceptions;

public class VariableAlreadyDefined extends MyException {
    public VariableAlreadyDefined(String name) {
        super("Variable " + name + " already defined!");
    }
}
