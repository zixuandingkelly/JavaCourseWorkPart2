package Model;

import Model.ColumnTypes.MyDataColumn;

import java.io.IOException;
import java.util.ArrayList;

public class Model {

    private DataFrame dataframe;

    public Model(DataFrame frame)  {
        dataframe = frame;
    }

    public Model() throws IOException, FrameException { // Initialise with example data.
        dataframe = new DataLoader("data/patients100.csv").getDataframe();
    }


    public ArrayList<String> getColNames(){
        return dataframe.getColumnNames();
    }

    public MyDataColumn getColByColName(String colName) throws FrameException {
        return dataframe.getColumnByName(colName);
    }

    public int getRows() throws FrameException {
        return dataframe.getRowCount();
    }

    public DataFrame getDataframe(){
        return dataframe;
    }

    public String getValue(String colName, int index) throws FrameException {
        return dataframe.getValue(colName, index);
    }

    public String getModelName(){
        return dataframe.getFrameName();
    }

}
