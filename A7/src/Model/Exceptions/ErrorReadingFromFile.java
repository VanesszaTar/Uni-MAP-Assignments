package Model.Exceptions;

public class ErrorReadingFromFile extends MyException {
    public ErrorReadingFromFile(String filename) {
        super("Error reading file " + filename);
    }
}
