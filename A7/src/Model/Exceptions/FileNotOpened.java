package Model.Exceptions;

public class FileNotOpened extends MyException {
    public FileNotOpened(String filename) {
        super("File " + filename + " is not opened!");
    }
}
