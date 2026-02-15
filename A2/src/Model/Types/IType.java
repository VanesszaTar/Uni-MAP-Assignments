package Model.Types;

public interface IType {
    boolean equals(Object other);
    String toString();
    IType deepCopy();
}
