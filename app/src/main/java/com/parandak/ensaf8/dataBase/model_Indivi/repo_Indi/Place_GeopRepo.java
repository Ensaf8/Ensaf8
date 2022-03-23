package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Place_Geop;

import org.osmdroid.util.GeoPoint;

public class Place_GeopRepo {

    Place_Geop place_geop;

    public Place_GeopRepo() {
        this.place_geop = new Place_Geop();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Place_Geop.TABLE + " ("
                + Place_Geop.KEY_ID_Indi_Geop + " INTEGER " + " , "
                + Place_Geop.KEY_IndiID + " INTEGER " + " , "
                + Place_Geop.KEY_GeopID + " INTEGER " + " , "
                + Place_Geop.KEY_IconID + " INTEGER " + " , "
                + " PRIMARY KEY(" + Place_Geop.KEY_ID_Indi_Geop + ")"
                + " FOREIGN KEY(" + Place_Geop.KEY_IndiID + ")"
                + " REFERENCES " + Individual.TABLE + " ( " + Individual.KEY_ID_Indi + ") ON DELETE CASCADE " + ","
                + " FOREIGN KEY(" + Place_Geop.KEY_GeopID + ")"
                + " REFERENCES " + GPoint.TABLE + " ( " + GPoint.KEY_IDGeop + ") ON DELETE CASCADE "
                + ");";

    }

    public int insert (Place_Geop place_geop){
        int place_geopId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Place_Geop.KEY_IndiID,place_geop.getIndiID());
        values.put(Place_Geop.KEY_GeopID,place_geop.getGeopID());
        values.put(Place_Geop.KEY_IconID,place_geop.getIconID());
        place_geopId = (int) db.insert(Place_Geop.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return place_geopId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Place_Geop.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteIndiID(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Place_Geop.TABLE,Place_Geop.KEY_IndiID + "=?",new String[]{IndiID})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
}