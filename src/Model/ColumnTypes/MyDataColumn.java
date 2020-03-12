package Model.ColumnTypes;

import Controller.SearchResult;
import Model.Model;

public interface MyDataColumn<T> {
    //getName, getSize, getRowValue, setRowValue, and addRowValue (to add a new row).

    String getName();

    int getSize();

    T getRowValue(int index);

    void setRowValue(int index, String value);

    void addRowValue(String value);

    SearchResult searchThroughCol(String str);

    boolean containsNum();

    boolean containsLettersOnly();

    boolean containsSpecialCharacter();





}
