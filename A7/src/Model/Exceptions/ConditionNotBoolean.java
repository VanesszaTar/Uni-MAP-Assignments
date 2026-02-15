package Model.Exceptions;

public class ConditionNotBoolean extends MyException {
    public ConditionNotBoolean() {
        super("The given condition does not have Boolean type!");
    }
}
