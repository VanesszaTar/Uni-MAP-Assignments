package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue {
    private final String val;
    public StringValue(String val) {
        this.val = val;
    }
    public String getValue(){
        return val;
    }
    @Override
    public IType getType() {
        return new StringType();
    }
    @Override
    public String toString() {
        return val;
    }
    @Override
    public int hashCode() {
        return val.hashCode();
    }
    @Override
    public boolean equals(Object other) {
        if(!(other instanceof StringValue)) return false;
        return this.val.equals(((StringValue)other).val);
    }
    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }
}
