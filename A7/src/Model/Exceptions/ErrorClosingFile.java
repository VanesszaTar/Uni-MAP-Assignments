package Model.Exceptions;

public class ErrorClosingFile extends MyException {
    public ErrorClosingFile(String filename) {
        super("Error closing file " + filename);
    }
}
