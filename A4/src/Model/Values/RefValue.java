package Model.Values;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;
    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }
    public int getAddress() {
        return address;
    }
    public IType getLocationType() {
        return locationType;
    }
    @Override
    public IType getType(){
        return new RefType(locationType);
    }
    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if(!(other instanceof RefValue))
            return false;
        RefValue otherRef = (RefValue) other;
        return this.address == otherRef.address && this.locationType.equals(otherRef.locationType);
    }
    @Override
    public String toString(){
        return "(" + address + "," + locationType.toString() + ")";
    }
}
