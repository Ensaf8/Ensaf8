package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.PhoneNum;

public class PhoneNumRepo {
    PhoneNum phoneNum;

    public PhoneNumRepo() {
        this.phoneNum = new PhoneNum();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ PhoneNum.TABLE+" ("
                + PhoneNum.KEY_ID_Phone+" INTEGER "+" , "
                + PhoneNum.KEY_CustomerID+" INTEGER "+" , "
                + PhoneNum.KEY_Num+" TEXT "+" , "
                + " PRIMARY KEY(" + PhoneNum.KEY_ID_Phone + ")"
                +");";

    }
    public static String createNewTable(){
        return "CREATE TABLE IF NOT EXISTS "+ PhoneNum.TABLEnew+" ("
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
        values.put(PhoneNum.KEY_CustomerID,phoneNum.getCustomerID());
        values.put(PhoneNum.KEY_Num,phoneNum.getNum());
        phoneNumId = (int) db.insert(PhoneNum.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return phoneNumId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(PhoneNum.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
