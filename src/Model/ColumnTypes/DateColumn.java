package Model.ColumnTypes;

import Controller.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class DateColumn implements MyDataColumn<Date> {
    private String colName;
    private ArrayList<Date> dates;

    DateColumn(String name){
        colName = name;
        dates = new ArrayList<>();
    }

    public ArrayList<Integer> getOldest(){
        return findSmallest(findSmallest(findSmallest(null,"year"),"month"),"day");
    }


    private ArrayList<Integer> findSmallest(ArrayList<Integer> indexes, String label){
        if(label.equals("year")) {
            int min_year = IntStream.range(0, dates.size()).boxed()
                    .map(i -> dates.get(i).getDataByLabel("year"))
                    .min(Integer::compareTo).orElseThrow();
            return (ArrayList<Integer>) IntStream.range(0, dates.size()).boxed()
                    .filter(i -> dates.get(i).getDataByLabel("year") == min_year)
                    .collect(Collectors.toList());
        }else{
            if(indexes.size() == 1){
                return indexes;
            }
            int min_val = IntStream.range(0, indexes.size()).boxed()
                    .map(i -> dates.get(indexes.get(i)).getDataByLabel(label))
                    .min(Integer::compareTo).orElseThrow();
            return (ArrayList<Integer>) IntStream.range(0, indexes.size()).boxed()
                    .filter(i -> dates.get(indexes.get(i)).getDataByLabel(label) == min_val)
                    .map(indexes::get)
                    .collect(Collectors.toList());
        }
    }


    @Override
    public String getName() {
        return colName;
    }

    @Override
    public int getSize() {
        return dates.size();
    }

    @Override
    public Date getRowValue(int index) {
        return dates.get(index);
    }

    @Override
    public void setRowValue(int index, String value) {
        dates.set(index, new Date(value));
    }

    @Override
    public void addRowValue(String value) {
        dates.add(new Date(value));
    }

    @Override
    public SearchResult searchThroughCol(String str) {
        if(str.matches("\\d+")){
            return new SearchResult( colName, searchForOneNumber(str));
        }else if(str.contains("-")){
            String[] strings = str.split("-");
            ArrayList<Integer> results = new ArrayList<>();
            for(String string: strings){
                if(string.matches("\\d+")) {
                    results.addAll(searchForOneNumber(string));
                }
            }
            return new SearchResult(colName, results);
        }else{
            return new SearchResult(colName, new ArrayList<>());
        }
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
        return true;
    }

    private ArrayList<Integer> searchForOneNumber(String str){
        return (ArrayList<Integer>)IntStream.range(0, dates.size()).boxed()
                .filter(i -> dates.get(i).partiallyMatchNumOnly(str))
                .collect(toList());

    }

}
