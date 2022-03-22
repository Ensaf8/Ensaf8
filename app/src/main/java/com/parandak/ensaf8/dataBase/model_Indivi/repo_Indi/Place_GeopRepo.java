package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Place_Goop;

import org.osmdroid.util.GeoPoint;

public class Place_GeopRepo {

    Place_Goop place_goop;

    public Place_GeopRepo(Place_Goop place_goop) {
        this.place_goop = place_goop;
    }

    public String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Place_Goop.TABLE + " ("
                + Place_Goop.KEY_ID_Indi_Geop + " INTEGER " + " , "
                + Place_Goop.KEY_IndiID + " INTEGER " + " , "
                + Place_Goop.KEY_GeopID + " INTEGER " + " , "
                + Place_Goop.KEY_IconID + " INTEGER " + " , "
                + " PRIMARY KEY(" + Place_Goop.KEY_ID_Indi_Geop + ")"
                + " FOREIGN KEY(" + Place_Goop.KEY_IndiID + ")"
                + " REFERENCES " + Individual.TABLE + " ( " + Individual.KEY_ID_Indi + ") ON DELETE CASCADE " + ","
                + " FOREIGN KEY(" + Place_Goop.KEY_GeopID + ")"
                + " REFERENCES " + GPoint.TABLE + " ( " + GPoint.KEY_IDGeop + ") ON DELETE CASCADE "
                + ");";

    }

    public int insert (Place_Goop place_goop){
        int place_geopId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Place_Goop.KEY_IndiID,place_goop.getIndiID());
        values.put(Place_Goop.KEY_GeopID,place_goop.getGeopID());
        values.put(Place_Goop.KEY_IconID,place_goop.getIconID());
        place_geopId = (int) db.insert(Place_Goop.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return place_geopId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Place_Goop.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteIndiID(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Place_Goop.TABLE,Place_Goop.KEY_IndiID + "=?",new String[]{IndiID})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
}