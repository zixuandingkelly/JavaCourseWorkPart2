package View;
import Model.*;
import Model.ColumnTypes.MyDataColumn;
import Model.DataFrame;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    private DataFrame dataframe;

    public MyTableModel()  {
        dataframe = new DataFrame();
    }

    public void addColumn(MyDataColumn col){
        dataframe.addColumn(col);
    }

    public void removeColumn(String name) throws FrameException {
        dataframe.removeColumnbyName(name);
    }

    @Override
    public int getRowCount() {
        try {
            return dataframe.getRowCount();
        } catch (FrameException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getColumnCount() {
        return dataframe.getColCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            return dataframe.getValue(getColName(columnIndex),rowIndex);
        } catch (FrameException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getColName(int columnIndex){
        return dataframe.getColumnNames().get(columnIndex);
    }

    public String getColumnName(int index){
        return dataframe.getColNameByindex(index);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


}
