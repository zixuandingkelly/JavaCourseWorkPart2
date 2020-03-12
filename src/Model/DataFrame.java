package Model;

import Model.ColumnTypes.MyDataColumn;

import java.util.*;


public class DataFrame implements MyDataFrame{
    private String frameName;
    private ArrayList<MyDataColumn> columns;

    public DataFrame(){
        columns = new ArrayList<>();
    }

    public void addColumn(MyDataColumn column){
        columns.add(column);
    }

    public ArrayList<String> getColumnNames(){
        Iterator<MyDataColumn> iterator = columns.iterator();
        ArrayList<String> columnNames = new ArrayList<>();
        while(iterator.hasNext()){
            columnNames.add(iterator.next().getName());
        }
        return columnNames;
    }

    public MyDataColumn getColumnByName(String columnName) throws FrameException {
        for(MyDataColumn col: columns){
            if(col.getName().equals(columnName)){
                return col;
            }
        }
        throw new FrameException("Invalid row index.");
    }

    public int getRowCount() throws FrameException {
        int rowNum = 0;
        for(MyDataColumn col: columns){
            if(rowNum == 0) {
                rowNum = col.getSize();
            }else if(rowNum != col.getSize()){
                throw new FrameException("The table is not consistent.");
            }
        }
        return rowNum;
    }



    public int getColCount(){
        return columns.size();
    }


    public String getValue(String columnName, int row) throws FrameException {
        return getColumnByName(columnName).getRowValue(row).toString();
    }

    public void addValue(String columnName, String value) throws FrameException {
        getColumnByName(columnName).addRowValue(value);
    }


    public void removeColumnbyName(String name) throws FrameException {
        columns.remove(getColumnByName(name));
    }

    public String getColNameByindex(int index){
        return columns.get(index).getName();
    }

    public ArrayList<MyDataColumn> getAllColumns(){
        return columns;
    }

    public void setFrameName(String name){
        frameName = name;
    }

    public String getFrameName(){
        return frameName;
    }

    public boolean contains(String str){
        return getColumnNames().contains(str);
    }
}
