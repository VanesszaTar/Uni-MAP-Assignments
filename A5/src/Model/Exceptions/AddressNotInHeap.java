package Model.Exceptions;

public class AddressNotInHeap extends MyException {
    public AddressNotInHeap(int addr) {
        super("The address" + addr + " is not in heap!");
    }
}
