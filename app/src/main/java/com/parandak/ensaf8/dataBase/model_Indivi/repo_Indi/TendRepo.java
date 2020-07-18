package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;

public class TendRepo {
    Tend tend;

    public TendRepo() {
        this.tend = new Tend();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Tend.TABLE+" ("
                + Tend.KEY_ID_Tend+" INTEGER "+" , "
                + Tend.KEY_Ind1ID+" INTEGER "+" , "
                + Tend.KEY_Ind2ID+" INTEGER "+" , "
                + Tend.KEY_Title+" TEXT "+" , "
                + Tend.KEY_Detail+" TEXT "+" , "
                + Tend.KEY_TendDate+" TEXT "+" , "
                + Tend.KEY_IsChecked+" INTEGER "+" , "
                + " PRIMARY KEY(" + Tend.KEY_ID_Tend + ")"
                +");";

    }

    public int insert (Tend tend){
        int tendId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Tend.KEY_Ind1ID,tend.getInd1ID());
        values.put(Tend.KEY_Ind2ID,tend.getInd2ID());
        values.put(Tend.KEY_Title,tend.getTitle());
        values.put(Tend.KEY_Detail,tend.getDetail());
        values.put(Tend.KEY_TendDate,tend.getTendDate());
        values.put(Tend.KEY_IsChecked,tend.getIsChecked());

        tendId = (int) db.insert(Tend.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return tendId;
    }

    public boolean update(Tend tend){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Tend.KEY_Ind1ID,tend.getInd1ID());
        values.put(Tend.KEY_Ind2ID,tend.getInd2ID());
        values.put(Tend.KEY_Title,tend.getTitle());
        values.put(Tend.KEY_Detail,tend.getDetail());
        values.put(Tend.KEY_TendDate,tend.getTendDate());
        values.put(Tend.KEY_IsChecked,tend.getIsChecked());
        return db.update(Tend.TABLE,values,Tend.KEY_ID_Tend + "=?" , new String[]{String.valueOf(tend.getID_Tend())}) > 0;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Tend.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteID_Tend(String ID_Tend){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Tend.TABLE,Tend.KEY_ID_Tend + "=?",new String[]{ID_Tend})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
}
