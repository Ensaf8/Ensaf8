package com.parandak.ensaf8.DirectionManagement;

public class wpt {

    private String lat;
    private String lon;
    private String name;
    private String date;
    private int color;

    public String getLat(){
        return lat;
    }

    public void setLat(String lat){
        this.lat = lat;
    }

    public String getLon(){
        return lon;
    }

    public void setLon(String lon){
        this.lon = lon;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDate(){return date;}

    public void setDate(String date){
        this.date = date;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }

    @Override
    public String toString() {
        return " Lat= "+lat + "\n Lon= " + lon + "\n Name= " + name+ "\n date= " + date+ "\n color= " + color;
    }

}
