package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;

public class Indi_GeopRepo {
    Indi_Geop indi_geop;

    public Indi_GeopRepo() {
        this.indi_geop = new Indi_Geop();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Indi_Geop.TABLE+" ("
                + Indi_Geop.KEY_ID_Indi_Geop+" INTEGER "+" , "
                + Indi_Geop.KEY_IndiID+" INTEGER "+" , "
                + Indi_Geop.KEY_GeopID+" INTEGER "+" , "
                + " PRIMARY KEY(" + Indi_Geop.KEY_ID_Indi_Geop + ")"
                +");";

    }

    public int insert (Indi_Geop indi_geop){
        int indi_geopId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Indi_Geop.KEY_IndiID,indi_geop.getIndiID());
        values.put(Indi_Geop.KEY_GeopID,indi_geop.getGeopID());
        indi_geopId = (int) db.insert(Indi_Geop.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return indi_geopId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Indi_Geop.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }


    public boolean deleteIndiID(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Indi_Geop.TABLE,Indi_Geop.KEY_IndiID + "=?",new String[]{IndiID})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }

}
