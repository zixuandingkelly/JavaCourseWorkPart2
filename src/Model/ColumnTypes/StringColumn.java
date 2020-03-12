package Model.ColumnTypes;

import Controller.SearchResult;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class StringColumn implements MyDataColumn<String> {
    private String columnName;
    private ArrayList<String> rows;

    public StringColumn(String name){
        this.columnName = name;
        rows = new ArrayList<>();
    }

    public String getName(){
        return columnName;
    }

    public int getSize(){
        return rows.size();
    }

    public String getRowValue(int index){
        return rows.get(index);
    }

    public void setRowValue(int index, String value){
        rows.set(index,value);
    }

    public void addRowValue(String value){
        rows.add(value);
    }


    private ArrayList<Integer> getNonEmpties(){
        return (ArrayList<Integer>) IntStream.range(0,rows.size()).boxed()
                .filter(p -> !rows.get(p).equals(""))
                .collect(Collectors.toList());
    }
    public boolean containsNum(){
        return getNonEmpties().stream().anyMatch(p -> rows.get(p).matches(".*\\d.*"));
    }

    public boolean containsLettersOnly(){
        return getNonEmpties().stream().anyMatch(i -> rows.get(i).matches("[a-zA-Z]+"));
    }

    public boolean containsSpecialCharacter(){
        return getNonEmpties().stream().anyMatch(j -> rows.get(j).matches(".*[^A-Za-z0-9].*"));

    }

    public SearchResult searchThroughCol(String str) {
        ArrayList<Integer> indexes =
                (ArrayList<Integer>) getNonEmpties().stream()
                .filter(i -> rows.get(i).toLowerCase().matches(".*" + str.toLowerCase() + ".*"))
                .collect(toList());
        return new SearchResult(columnName,indexes);
    }

}
