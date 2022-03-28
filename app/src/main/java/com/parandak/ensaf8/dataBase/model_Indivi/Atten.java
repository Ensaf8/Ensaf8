package com.parandak.ensaf8.dataBase.model_Indivi;

public class Atten {

    public static final String TABLE = "Atten";

    public static final String KEY_ID_Atten = "ID_Atten";
    public static final String KEY_Ind1ID = "ind1ID";
    public static final String KEY_Ind2ID = "ind2ID";
    public static final String KEY_AttenDate = "attenDate";

    private String ID_Atten;
    private String ind1ID ;
    private String ind2ID ;
    private String attenDate ;

    public String getID_Atten() {
        return ID_Atten;
    }

    public void setID_Atten(String ID_Atten) {
        this.ID_Atten = ID_Atten;
    }

    public String getInd1ID() {
        return ind1ID;
    }

    public void setInd1ID(String ind1ID) {
        this.ind1ID = ind1ID;
    }

    public String getInd2ID() {
        return ind2ID;
    }

    public void setInd2ID(String ind2ID) {
        this.ind2ID = ind2ID;
    }

    public String getAttenDate() {
        return attenDate;
    }

    public void setAttenDate(String attenDate) {
        this.attenDate = attenDate;
    }
}
