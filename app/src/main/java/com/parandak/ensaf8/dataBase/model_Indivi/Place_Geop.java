package com.parandak.ensaf8.dataBase.model_Indivi;

public class Place_Geop {
    public static final String TABLE = "Place_Geop";

    public static final String KEY_ID_Indi_Geop = "ID_Place_Geop";
    public static final String KEY_IndiID = "indiID";
    public static final String KEY_GeopID = "geopID";
    public static final String KEY_IconID = "iconID";

    private String ID_Place_Geop;

    private String indiID;

    private String geopID;

    private String iconID;

    public String getID_Place_Geop() {
        return ID_Place_Geop;
    }

    public void setID_Place_Geop(String ID_Place_Geop) {
        this.ID_Place_Geop = ID_Place_Geop;
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

    public String getIconID() {
        return iconID;
    }

    public void setIconID(String iconID) {
        this.iconID = iconID;
    }
}
