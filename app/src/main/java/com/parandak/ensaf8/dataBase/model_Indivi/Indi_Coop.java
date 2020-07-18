package com.parandak.ensaf8.dataBase.model_Indivi;

public class Indi_Coop {
    public static final String TABLE = "Indi_CoopRepo";

    public static final String KEY_ID_Indi_Co = "ID_Indi_Co";
    public static final String KEY_FirstPartID = "firstPartID";
    public static final String KEY_SecondPartID = "secondPartID";
    public static final String KEY_Title = "title";

    private String ID_Indi_Co;
    private String firstPartID ;
    private String secondPartID ;
    private String title ;

    public String getID_Indi_Co() {
        return ID_Indi_Co;
    }

    public void setID_Indi_Co(String ID_Indi_Co) {
        this.ID_Indi_Co = ID_Indi_Co;
    }

    public String getFirstPartID() {
        return firstPartID;
    }

    public void setFirstPartID(String firstPartID) {
        this.firstPartID = firstPartID;
    }

    public String getSecondPartID() {
        return secondPartID;
    }

    public void setSecondPartID(String secondPartID) {
        this.secondPartID = secondPartID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
