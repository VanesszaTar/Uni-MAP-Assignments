package Model.Values;
import Model.Types.IType;
import Model.Types.IntType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof IntValue)) return false;
        return this.value == ((IntValue)other).value;
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }
}
