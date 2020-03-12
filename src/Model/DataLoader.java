package Model;

import Model.ColumnTypes.MyColumnFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Reference: https://stackabuse.com/reading-and-writing-csvs-in-java/
// Accessed on: 04 Mar 2020

public class DataLoader {
    private DataFrame dataframe;

    public DataLoader(String filepath) throws IOException, FrameException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
        String row;
        boolean firstRow = false;
        List<String> firstRowNames = new ArrayList<>();

        while((row =csvReader.readLine())!=null) {
            List<String> data = Arrays.asList(row.split(","));
            // Manipulate the first row
            if(!firstRow){
                firstRowNames = data;
                dataframe = new DataFrame();
                for(String str: data){
                    dataframe.addColumn(MyColumnFactory.getInstance().getMyColumn(str));
                }
                firstRow = true;
            }else { // Manipulate the rest
                for(int i=0;i<firstRowNames.size();i++){
                    try {
                        dataframe.addValue(firstRowNames.get(i), data.get(i));
                    }catch (ArrayIndexOutOfBoundsException e){
                        dataframe.addValue(firstRowNames.get(i), "");
                    }
                }
            }

        }
        csvReader.close();
    }

    public DataFrame getDataframe(){
        return dataframe;
    }


}
