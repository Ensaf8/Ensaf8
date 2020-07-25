package com.parandak.ensaf8.dataBase.model_Indivi;

public class BookMark {
    public static final String TABLE = "BookMark";

    public static final String KEY_ID_BookMark = "ID_BookMark";
    public static final String KEY_IndID = "indID";

    private String ID_BookMark;
    private String indID;

    public String getID_BookMark() {
        return ID_BookMark;
    }

    public void setID_BookMark(String ID_BookMark) {
        this.ID_BookMark = ID_BookMark;
    }

    public String getIndID() {
        return indID;
    }

    public void setIndID(String indID) {
        this.indID = indID;
    }
}
