package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;

public class Indi_CoopRepo {
    private Indi_Coop indi_coop;

    public Indi_CoopRepo() {
        this.indi_coop = new Indi_Coop();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Indi_Coop.TABLE+" ("
                + Indi_Coop.KEY_ID_Indi_Co+" INTEGER "+" , "
                + Indi_Coop.KEY_FirstPartID+" INTEGER "+" , "
                + Indi_Coop.KEY_SecondPartID+" INTEGER "+" , "
                + Indi_Coop.KEY_Title+" TEXT "+" , "
                + " PRIMARY KEY(" + Indi_Coop.KEY_ID_Indi_Co + ")"
                +");";

    }

    public int insert (Indi_Coop indi_coop){
        int indi_coopId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Indi_Coop.KEY_FirstPartID,indi_coop.getFirstPartID());
        values.put(Indi_Coop.KEY_SecondPartID,indi_coop.getSecondPartID());
        values.put(Indi_Coop.KEY_Title,indi_coop.getTitle());
        indi_coopId = (int) db.insert(Indi_Coop.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return indi_coopId;
    }

    public boolean update(Indi_Coop indi_coop){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Indi_Coop.KEY_FirstPartID,indi_coop.getFirstPartID());
        values.put(Indi_Coop.KEY_SecondPartID,indi_coop.getSecondPartID());
        values.put(Indi_Coop.KEY_Title,indi_coop.getTitle());
        return db.update(Indi_Coop.TABLE,values,Indi_Coop.KEY_ID_Indi_Co + "=?",new String[]{String.valueOf(indi_coop.getID_Indi_Co())}) > 0;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Indi_Coop.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteIndiID (String ID_Indi_Co){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Indi_Coop.TABLE,Indi_Coop.KEY_ID_Indi_Co + "=?",new String[]{ID_Indi_Co})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }

}
