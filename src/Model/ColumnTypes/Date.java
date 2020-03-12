package Model.ColumnTypes;

public class Date {
    private int year;
    private int month;
    private int day;
    private boolean valid = false;

    public Date(String str){
        if(!str.equals("")) {
            String[] list = str.split("-");
            year = Integer.parseInt(list[0]);
            month = Integer.parseInt(list[1]);
            day = Integer.parseInt(list[2]);
            valid = true;
        }
    }

    public int getDataByLabel(String label) {
        switch(label){
            case "year":
                return year;
            case "month":
                return month;
            case "day":
                return day;
        }
        return 0;
    }

    public String toString(){
        if(!valid){
            return "";
        }
        return year + "-" + month + "-" + day ;
    }

    public boolean partiallyMatchNumOnly(String numOnly){
        int searching = Integer.parseInt(numOnly);
        return year == searching || month == searching || day == searching;
    }


}
