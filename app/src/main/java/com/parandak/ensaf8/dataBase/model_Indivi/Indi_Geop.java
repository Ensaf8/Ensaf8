package com.parandak.ensaf8.dataBase.model_Indivi;

public class Indi_Geop {
    public static final String TABLE = "Indi_Geop";

    public static final String KEY_ID_Indi_Geop = "ID_Indi_geop";
    public static final String KEY_IndiID = "indiID";
    public static final String KEY_GeopID = "geopID";


    private String ID_Indi_geop;
    private String indiID ;
    private String geopID ;

    public String getID_Indi_geop() {
        return ID_Indi_geop;
    }

    public void setID_Indi_geop(String ID_Indi_geop) {
        this.ID_Indi_geop = ID_Indi_geop;
    }

    public String getIndiID() {
        return indiID;
    }

    public void setIndiID(String indiID) {
        this.indiID = indiID;
    }

    public String getGeopID() {
        return geopID;
    }

    public void setGeopID(String geopID) {
        this.geopID = geopID;
    }
}
