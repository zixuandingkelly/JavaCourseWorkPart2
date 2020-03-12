package Model.ColumnTypes;
// Reference: Comp0004 CourseWork part1, MySetException.java, by Graham Roberts

public class MyColumnFactory {
    private static MyColumnFactory factory = null;

    private MyColumnFactory() { }

    public static MyColumnFactory getInstance()
    {
        if (factory == null) factory = new MyColumnFactory();
        return factory;
    }


    public MyDataColumn getMyColumn(String colName){
        switch (colName)
        {
            case "BIRTHDATE":
            case "DEATHDATE":
                return new DateColumn(colName);

            case "ZIP":
                return new IntegerColumn(colName);

            default:
                return new StringColumn(colName);
        }
    }



}
