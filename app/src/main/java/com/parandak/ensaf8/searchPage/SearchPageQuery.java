package com.parandak.ensaf8.searchPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;

import java.util.ArrayList;

public class SearchPageQuery {
    public SearchPageQuery(){

    }
    public Cursor getAllCustomerSearchFilter(String filter){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.KEY_ID_Indi + " , "
                + Individual.KEY_IndiName
                + " FROM "
                + Individual.TABLE
                + " WHERE "
                + Individual.KEY_IsCons + " = 0 AND "
                + Individual.KEY_IndiName + "  LIKE '%"+filter+"%'"
                + " ORDER BY "
                + Individual.KEY_ID_Indi + " DESC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getAllCustomerSearchFilter(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.KEY_ID_Indi + " , "
                + Individual.KEY_IndiName
                + " FROM "
                + Individual.TABLE
                + " WHERE "
                + Individual.KEY_IsCons + " = 0 "
                + " ORDER BY "
                + Individual.KEY_ID_Indi + " DESC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getAllCustomerSearchFilter03(String filter){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.TABLE +"."+ Individual.KEY_ID_Indi + " , "
                + Individual.TABLE +"."+ Individual.KEY_IndiName + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_FirstPartID + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_Title
                + " FROM "
                + Individual.TABLE
                + " LEFT JOIN " + Indi_Coop.TABLE
                + " ON " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_SecondPartID + " = " + Individual.TABLE +"."+ Individual.KEY_ID_Indi
                + " WHERE "
                + Individual.KEY_IsCons + " = 0 AND "
                + Individual.KEY_IndiName + "  LIKE '%"+filter+"%'"
                + " GROUP BY " + Individual.KEY_ID_Indi
                + " ORDER BY " + Individual.KEY_ID_Indi + " DESC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getAllCustomerSearchFilter03(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.TABLE +"."+ Individual.KEY_ID_Indi + " , "
                + Individual.TABLE +"."+ Individual.KEY_IndiName + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_FirstPartID + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_Title
                + " FROM "
                + Individual.TABLE
                + " LEFT JOIN " + Indi_Coop.TABLE
                + " ON " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_SecondPartID + " = " + Individual.TABLE +"."+ Individual.KEY_ID_Indi
                + " WHERE "
                + Individual.KEY_IsCons + " = 0 "
                + " GROUP BY " + Individual.KEY_ID_Indi
                + " ORDER BY " + Individual.KEY_ID_Indi + " DESC;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

}
