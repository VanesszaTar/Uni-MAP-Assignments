package Model.Values;
import Model.Types.IType;
import Model.Types.BoolType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(value);
    }
}
