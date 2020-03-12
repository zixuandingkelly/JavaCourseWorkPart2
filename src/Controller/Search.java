package Controller;

import Model.DataFrame;
import Model.ColumnTypes.DateColumn;
import Model.FrameException;
import Model.ColumnTypes.MyDataColumn;

import java.util.ArrayList;

public class Search {
    private DataFrame frame;

    private ArrayList<MyDataColumn> containsSpecialChar;
    private ArrayList<MyDataColumn> containsLettersOnly;
    private ArrayList<MyDataColumn> containsNumber;
    private ArrayList<MyDataColumn> containsBothNumberAndSpecialChar;
    private ArrayList<MyDataColumn> leftover;

    public Search (DataFrame dataframe) {
        frame = dataframe;
        containsLettersOnly = new ArrayList<>();
        containsNumber = new ArrayList<>();
        containsSpecialChar = new ArrayList<>();
        containsBothNumberAndSpecialChar = new ArrayList<>();
        leftover = new ArrayList<>();
        partition();
    }

    private void partition(){
        ArrayList<MyDataColumn> all_cols = frame.getAllColumns();
        for(MyDataColumn col: all_cols){
            if(col.containsNum()){
                containsNumber.add(col);
            }else if(col.containsNum()&&col.containsSpecialCharacter()){
                containsBothNumberAndSpecialChar.add(col);
            }else if(col.containsLettersOnly()){
                containsLettersOnly.add(col);
            }else if(col.containsSpecialCharacter()){
                containsSpecialChar.add(col);
            }else{
                leftover.add(col);
            }
        }
    }

    private boolean strContNum(String str){
        return str.matches(".*\\d.*");
    }

    private boolean strContSpecChar(String str){
        return str.matches(".*[^A-Za-z0-9].*");
    }

    private boolean strContLetterOnly(String str){
        return str.matches("[a-zA-Z]+");
    }

    public ArrayList<SearchResult> searchString(String str){
        if(strContNum(str) && strContSpecChar(str)){
            return searchThroughPartition(containsBothNumberAndSpecialChar,str);
        }else if(strContNum(str)){
            return searchThroughPartition(containsNumber,str);
        }else if(strContSpecChar(str)){
            return searchThroughPartition(containsSpecialChar,str);
        }else if(strContLetterOnly(str)){
            return searchThroughPartition(containsLettersOnly,str);
        }else{
            return searchThroughPartition(leftover,str);
        }
    }

    private ArrayList<SearchResult> searchThroughPartition(ArrayList<MyDataColumn> part, String str){
        ArrayList<SearchResult> results = new ArrayList<>();
        for(MyDataColumn col:part){
            SearchResult result = col.searchThroughCol(str);
            if(result.found()){
                results.add(result);
            }
        }
        return results;
    }

    public ArrayList<Integer> sameCityIndex(String city_name) throws FrameException {
        return frame.getColumnByName(frame.getColumnNames().get(17)).searchThroughCol(city_name).getIndexes();
    }

    public ArrayList<Integer> getOldestPerson() throws FrameException {
        DateColumn birthdate = (DateColumn) frame.getColumnByName("BIRTHDATE");
        return birthdate.getOldest();
    }

}
