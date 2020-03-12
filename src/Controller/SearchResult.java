package Controller;

import java.util.ArrayList;

public class SearchResult {

    private String colName;
    private ArrayList<Integer> indexes;

    public SearchResult(String colName, ArrayList<Integer> indexes){
        this.colName = colName;
        this.indexes = indexes;
    }

    public String getColName(){
        return colName;
    }

    public ArrayList<Integer> getIndexes(){
        return indexes;
    }

    public boolean found(){
        return indexes.size()>0;
    }
}
