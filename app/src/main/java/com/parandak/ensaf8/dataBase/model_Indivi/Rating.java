package com.parandak.ensaf8.dataBase.model_Indivi;

public class Rating {

    public static final String TABLE = "Rating";

    public static final String KEY_ID_Rating = "ID_Rating";
    public static final String KEY_IndID = "indID";
    public static final String KEY_Rate = "rate";

    private String ID_BookMark;
    private String indID;
    private String rate;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
