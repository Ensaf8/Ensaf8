package com.parandak.ensaf8.homePage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;

public class HomePageQuery {

    public HomePageQuery() {

    }
    public Cursor getBookMark(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT *"
                + " FROM " + BookMark.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor getTask(String IndID1, String date01, String date02){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Tend.TABLE + "." + Tend.KEY_ID_Tend + " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName + " , "
                + Tend.TABLE + "." + Tend.KEY_Title + " , "
                + Tend.TABLE + "." + Tend.KEY_Detail + " , "
                + Tend.TABLE + "." + Tend.KEY_TendDate + " , "
                + Tend.TABLE + "." + Tend.KEY_IsChecked + " , "
                + Tend.TABLE + "." + Tend.KEY_Ind2ID
                + " FROM "
                + Tend.TABLE
                + " INNER JOIN " + Individual.TABLE
                + " ON " + Tend.TABLE + "." + Tend.KEY_Ind2ID + " = " + Individual.TABLE + "." + Individual.KEY_ID_Indi
                + " WHERE ( " +Tend.TABLE + "." +  Tend.KEY_TendDate + " BETWEEN " + "'" + date01 + "'"  + " AND  " + "'" + date02 + "'" + " ) AND " +Tend.TABLE + "." +  Tend.KEY_Ind1ID + " = ' " + IndID1 + " '"
                + " ORDER BY " + Tend.KEY_ID_Tend + " DESC;";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor getTask(String IndID1){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Tend.TABLE + "." + Tend.KEY_ID_Tend + " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName + " , "
                + Tend.TABLE + "." + Tend.KEY_Title + " , "
                + Tend.TABLE + "." + Tend.KEY_Detail + " , "
                + Tend.TABLE + "." + Tend.KEY_TendDate + " , "
                + Tend.TABLE + "." + Tend.KEY_IsChecked + " , "
                + Tend.TABLE + "." + Tend.KEY_Ind2ID
                + " FROM "
                + Tend.TABLE
                + " INNER JOIN " + Individual.TABLE
                + " ON " + Tend.TABLE + "." + Tend.KEY_Ind2ID + " = " + Individual.TABLE + "." + Individual.KEY_ID_Indi
                + " WHERE " + Tend.TABLE + "." +  Tend.KEY_Ind1ID + " = ' " + IndID1 + " ' "
                + " ORDER BY " + Tend.KEY_ID_Tend + " DESC;";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor getTasksID(String TendID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Tend.TABLE + "." + Tend.KEY_Ind1ID + " , "
                + Tend.TABLE + "." + Tend.KEY_Ind2ID + " , "
                + Tend.TABLE + "." + Tend.KEY_Title + " , "
                + Tend.TABLE + "." + Tend.KEY_Detail + " , "
                + Tend.TABLE + "." + Tend.KEY_TendDate + " , "
                + Tend.TABLE + "." + Tend.KEY_IsChecked
                + " FROM "
                + Tend.TABLE
                + " WHERE " + Tend.TABLE + "." + Tend.KEY_ID_Tend + " = '" + TendID + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor checkUserName(String accountName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + CusAccount.TABLE + "." + CusAccount.KEY_AccountName + " , "
                + CusAccount.TABLE + "." + CusAccount.KEY_PassWord + " , "
                + CusAccount.TABLE + "." + CusAccount.KEY_IndID
                + " FROM "
                + CusAccount.TABLE
                + " WHERE " + CusAccount.TABLE + "." + CusAccount.KEY_AccountName + " = "+" '" + accountName + "' ;" ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public int isCons(String indiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.TABLE + "." + Individual.KEY_IsCons
                + " FROM " + Individual.TABLE
                + " WHERE " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " = " + indiID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }else {
            return -1;
        }
    }

}
