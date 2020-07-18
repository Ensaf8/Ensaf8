package com.parandak.ensaf8.homePage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;

public class HomePageQuery {

    public HomePageQuery() {

    }

    public Cursor getAllTend(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT * " +
                " FROM " +
                CusAccount.TABLE + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getTasks(){
        String date01 = "2019-10-26 00:00" ;
        String date02 = "2019-11-01 23:59";
        String ID_Customer = "23";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Tend.TABLE + "." + Tend.KEY_ID_Tend + " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName + " , "
                + Tend.TABLE + "." + Tend.KEY_Title + " , "
                + Tend.TABLE + "." + Tend.KEY_Detail + " , "
                + Tend.TABLE + "." + Tend.KEY_TendDate + " , "
                + Tend.TABLE + "." + Tend.KEY_IsChecked
                + " FROM "
                + Tend.TABLE
                + " INNER JOIN " + Individual.TABLE
                + " ON " + Tend.TABLE + "." + Tend.KEY_Ind2ID + " = " + Individual.TABLE + "." + Individual.KEY_ID_Indi
                + " WHERE ( " + Tend.TABLE + "." + Tend.KEY_TendDate + " BETWEEN " + " ' " + date01+ " ' " + "AND" + " ' " + date02 + " '" +") "+ " AND " + Tend.TABLE + "." + Tend.KEY_Ind1ID + " = " + ID_Customer + ";" ;

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

    public Cursor getAllConsPhase(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " +
                Cons_Phase.KEY_IndID + " , "+
                Cons_Phase.KEY_Phase + " , "+
                Cons_Phase.KEY_PhaseDate +
                " FROM " +
                Cons_Phase.TABLE + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getAllConsPhaseInt(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " +
                Cons_Phase.KEY_IndID + " , "+
                Cons_Phase.KEY_Phase + " , "+
                Cons_Phase.KEY_PhaseDate +
                " FROM " +
                Cons_Phase.TABLEint + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
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

    public Cursor testConsView(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT * "
                + " FROM "
                + " construction ";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor testLastUpView(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT * "
                + " FROM "
                + " lastUpConsInd ";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }

    public Cursor checkCustomer(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        /*String selectQuery = " SELECT "
                + CusAccount.TABLE + "." + CusAccount.KEY_AccountName + " , "
                + CusAccount.TABLE + "." + CusAccount.KEY_PassWord + " , "
                + CusAccount.TABLE + "." + CusAccount.KEY_IndID
                + " FROM "
                + CusAccount.TABLE
                + " WHERE " + CusAccount.TABLE + "." + CusAccount.KEY_AccountName + " = "+" '" + accountName + "' ;" ;*/
        String selectQuery = " SELECT "
                +" * "
                +" FROM "
                + CusAccount.TABLE;

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
