package Model.Types;
import Model.Values.IValue;

public interface IType {
    boolean equals(Object other);
    String toString();
    IValue defaultValue();
    IType deepCopy();
}
