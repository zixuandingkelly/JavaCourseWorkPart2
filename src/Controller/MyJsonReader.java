package Controller;

import Model.ColumnTypes.MyColumnFactory;
import Model.DataFrame;
import Model.FrameException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyJsonReader {
    private DataFrame dataFrame;

    public MyJsonReader(String filepath) throws IOException, FrameException {
        BufferedReader jsonReader = new BufferedReader(new FileReader(filepath));
        dataFrame = new DataFrame();
        String row;
        while((row =jsonReader.readLine())!=null) {
            if(row.matches("\\s*\"[a-zA-Z]+\":\\s*\\[")){
                String str = row.codePoints().filter(Character::isLetter).boxed()
                        .collect(Collector.of(StringBuilder::new,
                                StringBuilder::appendCodePoint,
                                StringBuilder::append,
                                StringBuilder::toString));
                dataFrame.setFrameName(str);
            }
            else if(row.matches( "\\s*\".*\":\\s*\".*\",")) {
                String finalRow = row;
                List<Integer> indexes = IntStream.range(0,row.length()).boxed()
                        .filter(i -> finalRow.charAt(i) == '\"')
                        .collect(Collectors.toList());
                String colName = row.substring(indexes.get(0)+1, indexes.get(1)).toUpperCase();
                String value;
                if(indexes.get(2)+1 == indexes.get(3)) {
                    value = "";
                }else{
                    value = row.substring(indexes.get(2) + 1, indexes.get(3));
                }
                if (!dataFrame.contains(colName)) {
                    dataFrame.addColumn(MyColumnFactory.getInstance().getMyColumn(colName));
                }

                dataFrame.addValue(colName, value);
            }
        }

    }

    public DataFrame getDataFrame(){
        return dataFrame;
    }

}
