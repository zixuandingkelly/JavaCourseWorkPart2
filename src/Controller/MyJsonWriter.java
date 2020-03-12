package Controller;


import Model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyJsonWriter {

    private Model model;

    public MyJsonWriter(String filePath, Model model) throws IOException, FrameException {
        FileWriter jsonWriter = new FileWriter(filePath);
        this.model = model;
        jsonWriter.append(frameToString());
        jsonWriter.flush();
        jsonWriter.close();

    }

    private String frameToString() throws FrameException {
        return "{\n" + "\"" + model.getModelName() + "\"" + ": ["
                + IntStream.range(0,model.getRows()).boxed()
                .map(this::onePatientToString)
                .collect(Collectors.joining(",\n")) + "\n"
                + "]\n" + "}\n";

    }

    private String onePatientToString(int index){
        ArrayList<String> colNames = model.getColNames();
        return "{\n" + colNames.stream()
                .map(name -> {
                    try {
                        return "\"" + name + "\"" + ": " + "\"" + model.getValue(name,index) + "\"";
                    } catch (FrameException e) {
                        e.printStackTrace();
                    }
                    return "\"" + name + "\"" + ": " + "\"" + "" + "\"";
                })
                .collect(Collectors.joining(",\n")) + "}";

    }

}
