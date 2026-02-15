package Model.Exceptions;

public class FileAlreadyOpened extends MyException {
    public FileAlreadyOpened(String filename) {
        super("File " + filename + " already exists!");
    }
}
