package Model.ADT;

import Model.Exceptions.MyException;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public interface IFileTable {
    void put(StringValue key, BufferedReader value) throws MyException;
    BufferedReader get(StringValue key) throws MyException;
    boolean isDefined(StringValue key);
    void remove(StringValue key) throws MyException;
    Map<StringValue, BufferedReader> getContent();
    String toString();
}
