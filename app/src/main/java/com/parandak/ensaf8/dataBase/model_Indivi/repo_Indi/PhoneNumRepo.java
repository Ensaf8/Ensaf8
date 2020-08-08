package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.PhoneNum;

public class PhoneNumRepo {
    PhoneNum phoneNum;

    public PhoneNumRepo() {
        this.phoneNum = new PhoneNum();
    }

    public static String createNewTable(){
        return "CREATE TABLE IF NOT EXISTS "+ PhoneNum.TABLE +" ("
                + PhoneNum.KEY_ID_Phone+" INTEGER "+" , "
                + PhoneNum.KEY_IndiID+" INTEGER "+" , "
                + PhoneNum.KEY_Num+" TEXT "+" , "
                + " PRIMARY KEY(" + PhoneNum.KEY_ID_Phone + ")"
                +");";

    }
    public int insert (PhoneNum phoneNum){
        int phoneNumId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneNum.KEY_IndiID,phoneNum.getIndiID());
        values.put(PhoneNum.KEY_Num,phoneNum.getNum());
        phoneNumId = (int) db.insert(PhoneNum.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return phoneNumId;
    }

    public boolean update(PhoneNum phoneNum){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(Individual.KEY_ID_Indi,individual.getID_Indi());
        values.put(PhoneNum.KEY_IndiID,phoneNum.getIndiID());
        values.put(PhoneNum.KEY_Num,phoneNum.getNum());
        return db.update(PhoneNum.TABLE,values,PhoneNum.KEY_ID_Phone + "=?",new String[]{String.valueOf(phoneNum.getID_Phone())}) > 0;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(PhoneNum.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deletePhoneID(String PhoneID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(PhoneNum.TABLE,PhoneNum.KEY_ID_Phone + "=?",new String[]{PhoneID})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
}
