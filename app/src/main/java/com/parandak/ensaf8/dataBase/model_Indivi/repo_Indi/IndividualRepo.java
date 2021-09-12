package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;

public class IndividualRepo {
    Individual individual;

    public IndividualRepo() {
        this.individual = new Individual();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Individual.TABLE+" ("
                + Individual.KEY_ID_Indi+" INTEGER "+" , "
                + Individual.KEY_IndiName+" TEXT "+" , "
                + Individual.KEY_IsCons+" INTEGER "+" , "
                + " PRIMARY KEY(" +Individual.KEY_ID_Indi + ")"
                +");";

    }

    public int insert (Individual individual){
        int individualId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        if (individual.getID_Indi()!=null){
            values.put(Individual.KEY_ID_Indi,individual.getID_Indi());
        }
        values.put(Individual.KEY_IndiName,individual.getIndiName());
        values.put(Individual.KEY_IsCons,individual.getIsCons());
        individualId = (int) db.insert(Individual.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return individualId;
    }

    public boolean update(Individual individual){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(Individual.KEY_ID_Indi,individual.getID_Indi());
        values.put(Individual.KEY_IndiName,individual.getIndiName());
        values.put(Individual.KEY_IsCons,individual.getIsCons());
        return db.update(Individual.TABLE,values,Individual.KEY_ID_Indi + "=?",new String[]{String.valueOf(individual.getID_Indi())}) > 0;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Individual.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteIndiID(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Individual.TABLE,Individual.KEY_ID_Indi + "=?",new String[]{IndiID})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }

    public String lastIndividual(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectLastConstruction = " SELECT "
                + Individual.TABLE + "." + Individual.KEY_ID_Indi
                + " FROM "
                + Individual.TABLE
                + " ORDER BY "
                + Individual.TABLE + "." + Individual.KEY_ID_Indi + " DESC "
                + "LIMIT 1;";
        Cursor cursor = db.rawQuery(selectLastConstruction, null);
        String ss = "fuck....";
        if (cursor.moveToFirst()){
            ss = cursor.getString(0);
        }
        return ss;
    }

    public static class syncLink{
        String tableName;

        public syncLink(boolean isF){
            if (isF){
                tableName = Individual.syncLink.TABLE_F;
            }else {
                tableName = Individual.syncLink.TABLE_T;
            };
        }

        public String createTable(){
            return "CREATE TABLE IF NOT EXISTS "+ tableName +" ("
                    + Individual.syncLink.KEY_ID + " INTEGER "+" , "
                    + Individual.syncLink.KEY_IndiFID + " INTEGER "+" , "
                    + Individual.syncLink.KEY_IndiID + " INTEGER "+" , "
                    + Individual.syncLink.KEY_CusId +" INTEGER "+" , "
                    + " PRIMARY KEY(" + Individual.syncLink.KEY_ID + ")"
                    + " FOREIGN KEY(" + Individual.syncLink.KEY_IndiID + ")"
                    + " REFERENCES " + Individual.TABLE + " ( " + Individual.KEY_ID_Indi + ") ON DELETE CASCADE " + ","
                    + " FOREIGN KEY(" + Individual.syncLink.KEY_CusId + ")"
                    + " REFERENCES " + CusAccount.TABLE + " ( " + CusAccount.KEY_ID_Cus + ") ON DELETE CASCADE "
                    +");";

        }

        public int insert (Individual.syncLink syncLink){
            int syncLinkId;
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            ContentValues values = new ContentValues();
            if (syncLink.get_id()!=null){
                values.put(Individual.syncLink.KEY_ID,syncLink.get_id());
            }
            values.put(Individual.syncLink.KEY_IndiFID,syncLink.getIndiFID());
            values.put(Individual.syncLink.KEY_IndiID,syncLink.getIndiID());
            values.put(Individual.syncLink.KEY_CusId,syncLink.getCusID());
            syncLinkId = (int) db.insert(tableName,null,values);
            DatabaseManager.getInstance().closeDatabase();
            return syncLinkId;
        }

    }
}
