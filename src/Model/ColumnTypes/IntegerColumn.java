package Model.ColumnTypes;

import Controller.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntegerColumn implements MyDataColumn<Integer> {

    private String colName;
    private ArrayList<Integer> rows;

    public IntegerColumn(String name){
        colName = name;
        rows = new ArrayList<>();
    }
    @Override
    public String getName() {
        return colName;
    }

    @Override
    public int getSize() {
        return rows.size();
    }

    @Override
    public Integer getRowValue(int index) {
        return rows.get(index);
    }

    @Override
    public void setRowValue(int index, String value) {
        rows.set(index,Integer.parseInt(value));
    }


    @Override
    public void addRowValue(String value) {
        if(value.equals("")){
            rows.add(0);
        }else {
            rows.add(Integer.parseInt(value));
        }
    }

    @Override
    public SearchResult searchThroughCol(String str) {
        ArrayList<Integer> result = (ArrayList<Integer>)
                IntStream.range(0,rows.size()).boxed()
                .filter(i -> rows.get(i).toString().matches("\\d*" + str + "\\d*"))
                .collect(Collectors.toList());
        return new SearchResult(colName,result);
    }

    @Override
    public boolean containsNum() {
        return true;
    }

    @Override
    public boolean containsLettersOnly() {
        return false;
    }

    @Override
    public boolean containsSpecialCharacter() {
        return false;
    }
}
