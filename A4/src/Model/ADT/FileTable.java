package Model.ADT;

import Model.Exceptions.MyException;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileTable implements IFileTable {
    private final Map<StringValue, BufferedReader> fileTable;
    public FileTable() {
        this.fileTable = new LinkedHashMap<>();
    }
    @Override
    public void put(StringValue key, BufferedReader value) throws MyException {
        if(fileTable.containsKey(key))
            throw new MyException("File already opened: " + key);
        fileTable.put(key, value);
    }
    @Override
    public BufferedReader get(StringValue key) throws MyException {
        if(!fileTable.containsKey(key))
            throw new MyException("File does not exist: " + key);
        return fileTable.get(key);
    }
    @Override
    public boolean isDefined(StringValue key){
        return fileTable.containsKey(key);
    }
    @Override
    public void remove(StringValue key) throws MyException {
        if(!fileTable.containsKey(key))
            throw new MyException("File does not exist: " + key);
        fileTable.remove(key);
    }
    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return fileTable;
    }

    @Override
    public String toString() {
        return fileTable.keySet().toString(); // in insertion order
    }
}

