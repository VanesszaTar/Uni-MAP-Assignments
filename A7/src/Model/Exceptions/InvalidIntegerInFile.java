package Model.Exceptions;

public class InvalidIntegerInFile extends MyException {
    public InvalidIntegerInFile(String filename) {
        super("Invalid integer in file " + filename);
    }
}
