package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;

public class CusAccountRepo {
    CusAccount cusAccount;

    public CusAccountRepo() {
        this.cusAccount = new CusAccount();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ CusAccount.TABLE+" ("
                + CusAccount.KEY_ID_Cus+" INTEGER "+" , "
                + CusAccount.KEY_IndID+" INTEGER "+" , "
                + CusAccount.KEY_AccountName+" TEXT "+" , "
                + CusAccount.KEY_PassWord+" TEXT "+" , "
                + CusAccount.KEY_IsAct+" INTEGER "+" , "
                + " PRIMARY KEY(" + CusAccount.KEY_ID_Cus + ")"
                +");";
    }

    public int insert (CusAccount cusAccount){
        int customerId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(CusAccount.KEY_IndID, cusAccount.getIndID());
        values.put(CusAccount.KEY_AccountName, cusAccount.getAccountName());
        values.put(CusAccount.KEY_PassWord, cusAccount.getPassWord());
        values.put(CusAccount.KEY_IsAct, cusAccount.getIsAct());
        customerId =(int) db.insert(CusAccount.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return customerId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(CusAccount.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
