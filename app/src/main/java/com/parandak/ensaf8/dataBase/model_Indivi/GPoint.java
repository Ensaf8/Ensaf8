package com.parandak.ensaf8.dataBase.model_Indivi;

public class GPoint {

    public static final String TABLE = "GPoint";

    public static final String KEY_IDGeop = "IDGeop";
    public static final String KEY_Lat = "lat";
    public static final String KEY_Lon = "lon";
    public static final String KEY_IsSolo = "isSolo";

    private String IDGeop;
    private String lat ;
    private String lon ;
    private String isSolo ;

    public String getIDGeop() {
        return IDGeop;
    }

    public void setIDGeop(String IDGeop) {
        this.IDGeop = IDGeop;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getIsSolo() {
        return isSolo;
    }

    public void setIsSolo(String isSolo) {
        this.isSolo = isSolo;
    }
}
