package View;

import Controller.*;
import Model.*;
import Model.ColumnTypes.*;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

//Reference: https://www.codejava.net/java-se/swing/jcheckbox-basic-tutorial-and-examples
//Accessed on: 04 Mar 2020

public class GUI extends JFrame{
    private Model model;
    private Controller controller;
    private JPanel topPanel;
    private JPanel checkBoxPanel;
    private ArrayList<JCheckBox> checkBoxs;
    private JPanel outputPanel;
    private JScrollPane scroller;
    private JTable output;
    private MyTableModel tableModel;
    private JPanel analysisPanel;
    private JPanel searchPanel;
    private JLabel searchHere;
    private JTextField searchInput;
    private JButton searchPush;
    private JButton searchClear;
    private JPanel factPanel;
    private JPanel oldPanel;
    private JPanel find_num_samePlace_panel;
    private JTextField placeNameInput;
    private JLabel find_same_place_num_result;
    private JPanel saveAndReadPanel;


    public GUI() throws IOException, FrameException {
        super("patients");
        model = new Model();
        controller = new Controller();
        createGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createGUI(){
        createTopPanel();
        add(topPanel, BorderLayout.CENTER);
    }

    private void createTopPanel(){
        topPanel = new JPanel(new BorderLayout());
        createOutputPanel();
        createCheckBoxPanel();
        createAnalysisPanel();
        topPanel.add(checkBoxPanel, BorderLayout.WEST);
        topPanel.add(outputPanel, BorderLayout.CENTER);
        topPanel.add(analysisPanel,BorderLayout.EAST);
    }

    private void createCheckBoxPanel() {
        checkBoxPanel = new JPanel(new GridLayout(20, 1, 10, 3));
        checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createCheckBoxs();
        for(JCheckBox checkbox: checkBoxs){
            checkBoxPanel.add(checkbox);
        }

    }

    private void createOutputPanel(){
        outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        outputPanel.setLayout(new BorderLayout());
        createOutputArea();
        outputPanel.add(scroller,BorderLayout.CENTER);
    }

    private void createAnalysisPanel(){
        analysisPanel = new JPanel();
        analysisPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        analysisPanel.setLayout(new BorderLayout());
        createSearchPanel();
        createFactPanel();
        analysisPanel.add(searchPanel, BorderLayout.NORTH);
        analysisPanel.add(factPanel, BorderLayout.CENTER);
        createSaveAndReadPanel();
        analysisPanel.add(saveAndReadPanel,BorderLayout.SOUTH);
    }

    private void createSaveAndReadPanel(){
        saveAndReadPanel = new JPanel();
        JLabel label = new JLabel("Read from csv or json file and write to json file.");
        saveAndReadPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        saveAndReadPanel.setLayout(new BorderLayout());
        saveAndReadPanel.add(label,BorderLayout.NORTH);
        JButton savetoJson = new JButton("Save to Json");
        savetoJson.addActionListener((ActionEvent e) -> saveFile());
        saveAndReadPanel.add(savetoJson,BorderLayout.WEST);
        JButton readFromFile = new JButton("Read from File");
        readFromFile.addActionListener((ActionEvent e) -> loadFile());
        saveAndReadPanel.add(readFromFile,BorderLayout.EAST);
    }

    private void createSearchPanel(){
        searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        searchPanel.setLayout(new BorderLayout());
        createSearchArea();
        searchPanel.add(searchHere, BorderLayout.NORTH);
        searchPanel.add(searchInput, BorderLayout.CENTER);
        searchPanel.add(searchPush, BorderLayout.EAST);
        searchPanel.add(searchClear,BorderLayout.SOUTH);
    }

    private void createSearchArea(){
        searchHere =  new JLabel("Please input your keyword here.");
        searchInput = new JTextField();
        searchPush = new JButton("Search");
        searchClear = new JButton("Clear");
        searchPush.addActionListener((ActionEvent e) -> searchButtonClicked());
        searchClear.addActionListener((ActionEvent e) -> clearHighlightedSearch());
    }

    private void createFactPanel(){
        factPanel = new JPanel();
        factPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        factPanel.setLayout(new BorderLayout());
        createOldestSearch();
        createFindsamePlaceNumPanel();
        factPanel.add(oldPanel,BorderLayout.NORTH);
        factPanel.add(find_num_samePlace_panel,BorderLayout.CENTER);
    }

    private void createOldestSearch(){
        oldPanel = new JPanel();
        oldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        oldPanel.setLayout(new BorderLayout());
        JLabel find_oldest_person = new JLabel("Find the oldest Person in the list.");
        JButton find_oldest = new JButton("Oldest Person");
        find_oldest.addActionListener((ActionEvent e) -> {
            try {
                displayOldestPerson();
            } catch (FrameException ex) {
                ex.printStackTrace();
            }
        });
        oldPanel.add(find_oldest_person, BorderLayout.WEST);
        oldPanel.add(find_oldest, BorderLayout.CENTER);
    }

    private void createFindsamePlaceNumPanel(){
        find_num_samePlace_panel = new JPanel();
        find_num_samePlace_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 140, 20));
        find_num_samePlace_panel.setLayout(new BorderLayout());
        JLabel find_num_samePlace_label = new JLabel("Please Input the city name you want to find.");
        JButton find_num_samePlace_push = new JButton("Find");
        find_num_samePlace_push.addActionListener((ActionEvent e) -> {
            try {
                samePlace();
            } catch (FrameException ex) {
                ex.printStackTrace();
            }
        });
        placeNameInput = new JTextField();
        JButton find_same_place_num_clear = new JButton("Clear");
        find_same_place_num_clear.addActionListener((ActionEvent e) -> clearsamePlace());
        find_same_place_num_result = new JLabel("");
        find_num_samePlace_panel.add(find_num_samePlace_label,BorderLayout.NORTH);
        find_num_samePlace_panel.add(placeNameInput,BorderLayout.CENTER);
        find_num_samePlace_panel.add(find_num_samePlace_push,BorderLayout.EAST);
        find_num_samePlace_panel.add(find_same_place_num_clear,BorderLayout.WEST);
        find_num_samePlace_panel.add(find_same_place_num_result,BorderLayout.SOUTH);
    }



    private void createOutputArea(){
        tableModel = new MyTableModel();
        output = new JTable(tableModel);
        output.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        output.setLayout(new BorderLayout());
        scroller = new JScrollPane(output);
    }

    private void createCheckBoxs() {
        ArrayList<String> checkBoxNames = model.getColNames();
        checkBoxs = new ArrayList<>();
        for(String str: checkBoxNames){
            JCheckBox checkbox = new JCheckBox(str);
            checkbox.setSelected(false);
            checkbox.addActionListener(event -> {
                MyDataColumn values = null;
                JCheckBox cb = (JCheckBox) event.getSource();
                if (cb.isSelected()) {
                    ArrayList<Integer> selected = (ArrayList<Integer>) Arrays.stream(output.getSelectedRows())
                                                  .boxed().collect(Collectors.toList());
                    try {
                        values = model.getColByColName(str);
                    } catch (FrameException e) {
                        e.printStackTrace();
                    }

                    tableModel.addColumn(values);
                    tableModel.fireTableStructureChanged();
                    ListSelectionModel sel_model = output.getSelectionModel();
                    highlightrows(selected,sel_model);
                } else {
                    // check box is unselected, do something else
                    try {
                        tableModel.removeColumn(str);
                    } catch (FrameException e) {
                        e.printStackTrace();
                    }
                    tableModel.fireTableStructureChanged();
                }
            });
            checkBoxs.add(checkbox);
        }
    }

    private void searchButtonClicked() {
        String str = searchInput.getText();
        if (str.length() > 0)
        {
            ArrayList<SearchResult> searchResults = controller.searchforStr(str);
            ListSelectionModel sel_model = output.getSelectionModel();
            sel_model.clearSelection();
            for(SearchResult result: searchResults){
                String colNameFound = result.getColName();
                tickTheBox(colNameFound);
                highlightrows(result.getIndexes(),sel_model);
            }
        }
    }

    private void tickTheBox(String colName){
        for(JCheckBox checkbox: checkBoxs){
            if(checkbox.getText().equals(colName)){
                if(!checkbox.isSelected()){
                    checkbox.doClick();
                }
            }
        }
    }

    private void highlightrows(ArrayList<Integer> indexes, ListSelectionModel sel_model){
        for(Integer index : indexes) {
            sel_model.addSelectionInterval(index, index);
        }
    }

    private void clearHighlightedSearch(){
        ListSelectionModel sel_model = output.getSelectionModel();
        sel_model.clearSelection();
        searchInput.setText("");
        for(JCheckBox checkBox: checkBoxs){
            if(checkBox.isSelected()){
                checkBox.doClick();
            }
        }
    }

    private void displayOldestPerson() throws FrameException {
        tickTheBox("BIRTHDATE");
        tickTheBox("ID");
        displaySearchResultforOneCol("BIRTHDATE", controller.getOldestPerson());
    }

    private void samePlace() throws FrameException {
        String str = placeNameInput.getText();
        ArrayList<Integer> indexes = controller.getsamePlace(str);
        displaySearchResultforOneCol("CITY",indexes);
        find_same_place_num_result.setText("City Name: " + str + " Number of People: " + indexes.size());
    }

    private void displaySearchResultforOneCol(String col_name, ArrayList<Integer> indexes){
        tickTheBox(col_name);
        ListSelectionModel sel_model = output.getSelectionModel();
        highlightrows(indexes, sel_model);

    }

    private void clearsamePlace(){
        clearHighlightedSearch();
        find_same_place_num_result.setText("");
        placeNameInput.setText("");
    }

    private void saveFile(){
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            try
            {
                controller.saveToJson(file);
            }
            catch (IOException | FrameException exp)
                {
                    JOptionPane.showMessageDialog(this, "Unable to save the file", "File Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
    }

    private void loadFile()
    {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            try
            {
                model = new Model(controller.readFromFile(file));
            }
            catch (IOException | FrameException exp)
            {
                JOptionPane.showMessageDialog(this, "Unable to load the file", "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
