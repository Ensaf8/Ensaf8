package com.parandak.ensaf8.fullScreenDialog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;

public class FullDialogQuery {

    public FullDialogQuery() {
    }

    public Cursor getIndiCooperate(String indi_ID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String getIndiCooperate = " SELECT "
                + Individual.TABLE + "." + Individual.KEY_ID_Indi + " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_Title + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_ID_Indi_Co
                + " FROM " + Indi_Coop.TABLE
                + " INNER JOIN " + Individual.TABLE
                + " ON " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " = " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_FirstPartID
                + " WHERE " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_SecondPartID + " = " + indi_ID;
        Cursor cursor = db.rawQuery(getIndiCooperate, null);
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
}
