package Controller;

import Model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Search search_engine;
    private Model model;

    public Controller() throws IOException, FrameException {
        model = new Model();
        search_engine = new Search(model.getDataframe());
    }

    public ArrayList<SearchResult> searchforStr(String str) {
        return search_engine.searchString(str);
    }

    public ArrayList<Integer> getOldestPerson() throws FrameException {
        return search_engine.getOldestPerson();
    }

    public ArrayList<Integer> getsamePlace(String str) throws FrameException {
        return search_engine.sameCityIndex(str);
    }

    public void saveToJson(File file) throws IOException, FrameException {
        String filepath = file.getAbsolutePath();
        new MyJsonWriter(filepath, model);
    }

    public DataFrame readFromFile(File file) throws IOException, FrameException {
        String filepath = file.getAbsolutePath();
        String type = filepath.substring(filepath.lastIndexOf('.')+1);
        if(type.equals("csv")){
            DataLoader csvLoader = new DataLoader(filepath);
            model = new Model(csvLoader.getDataframe());
            return csvLoader.getDataframe();
        }else if(type.equals("json")){
            MyJsonReader jsonReader = new MyJsonReader(filepath);
            model = new Model(jsonReader.getDataFrame());
            return jsonReader.getDataFrame();
        }else{
            throw new FrameException("Invalid type.");
        }
    }

}
