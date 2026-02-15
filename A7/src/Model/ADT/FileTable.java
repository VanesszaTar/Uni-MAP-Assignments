package Model.ADT;

import Model.Exceptions.FileNotOpened;
import Model.Exceptions.MyException;
import Model.Exceptions.FileAlreadyOpened;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;

// files currently opened by program
// mapping: filename -> Java file handler
public class FileTable implements IFileTable {
    private final Map<StringValue, BufferedReader> fileTable;
    public FileTable() {
        this.fileTable = new LinkedHashMap<>();
    } // preserving insertion order
    @Override
    public void put(StringValue key, BufferedReader value) throws MyException {
        if(fileTable.containsKey(key))
            throw new FileAlreadyOpened(key.toString());
        fileTable.put(key, value);
    }
    @Override
    public BufferedReader get(StringValue key) throws MyException {
        if(!fileTable.containsKey(key))
            throw new FileNotOpened(key.toString());
        return fileTable.get(key);
    }
    @Override
    public boolean isDefined(StringValue key){
        return fileTable.containsKey(key);
    }
    @Override
    public void remove(StringValue key) throws MyException {
        if(!fileTable.containsKey(key))
            throw new FileNotOpened(key.toString());
        fileTable.remove(key);
    }
    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return fileTable;
    }

    @Override
    public String toString() {
        return fileTable.keySet().toString(); // in insertion order, only filenames displayed because of keySet
    }
}

