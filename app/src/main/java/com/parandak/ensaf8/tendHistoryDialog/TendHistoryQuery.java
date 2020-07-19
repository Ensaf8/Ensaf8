package com.parandak.ensaf8.tendHistoryDialog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;

public class TendHistoryQuery {

    public TendHistoryQuery(){

    }

    public Cursor getHistoryTend(String CUS_ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String getHistoryTend = " SELECT "
                + Tend.TABLE + "." + Tend.KEY_ID_Tend + " , "
                + Tend.TABLE + "." + Tend.KEY_Title + " , "
                + Tend.TABLE + "." + Tend.KEY_TendDate
                + " FROM " + Tend.TABLE
                + " WHERE " + Tend.TABLE + "." + Tend.KEY_Ind2ID + " = " + CUS_ID;
        Cursor cursor = db.rawQuery(getHistoryTend, null);
        return cursor;
    }
    public String getIndiName(String CUS_ID){
        String id = "error";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String getCustomerName = " SELECT "
                + Individual.KEY_IndiName
                + " FROM " + Individual.TABLE
                + " WHERE " + Individual.KEY_ID_Indi + " = " + CUS_ID;
        Cursor cursor = db.rawQuery(getCustomerName, null);
        if (cursor.moveToFirst()){
            id = cursor.getString(0);
        }
        return id;
    }
}
