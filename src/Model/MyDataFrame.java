package Model;

import Model.ColumnTypes.MyDataColumn;

import java.util.ArrayList;

public interface MyDataFrame {
//• addColumn
//• getColumnNames (a list of names in the same order as they are stored in the frame).
//• getRowCount (the number of rows in a column, all columns should have the same number of rows when the frame is fully loaded with data).
//• getValue(columnName, row) to get a row value from a column.
//• putValue(columnName, row, value) to put a value into a row in a column.
//• addValue(columnName, value) to add a value to a column (at the end).
    void addColumn(MyDataColumn col);

    ArrayList<String> getColumnNames();

    int getRowCount() throws FrameException;

    String getValue(String columnName, int row) throws FrameException;

    void addValue(String columnName, String value) throws FrameException;

}
