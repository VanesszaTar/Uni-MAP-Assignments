package Model.Values;
import Model.Types.IType;

public interface IValue {
    IType getType();
    String toString();
    boolean equals(Object other);
    IValue deepCopy();
}
