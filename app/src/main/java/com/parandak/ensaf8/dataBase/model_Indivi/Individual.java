package com.parandak.ensaf8.dataBase.model_Indivi;

public class Individual {

    public static final String TABLE = "Individual";

    public static final String KEY_ID_Indi = "ID_Indi";
    public static final String KEY_IndiName = "indiName";
    public static final String KEY_IsCons = "isCons";


    private String ID_Indi;
    private String indiName ;
    private String isCons ;

    public String getID_Indi() {
        return ID_Indi;
    }

    public void setID_Indi(String ID_Indi) {
        this.ID_Indi = ID_Indi;
    }

    public String getIndiName() {
        return indiName;
    }

    public void setIndiName(String indiName) {
        this.indiName = indiName;
    }

    public String getIsCons() {
        return isCons;
    }

    public void setIsCons(String isCons) {
        this.isCons = isCons;
    }
}
