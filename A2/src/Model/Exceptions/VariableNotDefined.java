package Model.Exceptions;

public class VariableNotDefined extends MyException {
    public VariableNotDefined(String id) {
        super("Variable " + id + " not defined!");
    }
}
