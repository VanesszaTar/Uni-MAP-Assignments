package Model.Exceptions;

public class ErrorOpeningFile extends MyException {
    public ErrorOpeningFile(String filename) {
        super("Error opening file " + filename + "!");
    }
}
