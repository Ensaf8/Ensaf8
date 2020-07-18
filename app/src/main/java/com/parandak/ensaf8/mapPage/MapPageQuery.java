package com.parandak.ensaf8.mapPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CreateViews;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;

public class MapPageQuery {

    public MapPageQuery() {

    }

    public Cursor singleTapOnConsIndFirst(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " , "
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_consName + " , "
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lat + " , "
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lon + " , "
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase + " , "
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate
                + " FROM "
                + CreateViews.Construction.VIEW
                + " INNER JOIN " + CreateViews.LastUpConsInd.VIEW
                + " ON " +  CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_ID_cons
                + " WHERE " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = ' " + consID + " ';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor singleTapOnConsIndSecond(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.TABLE + "." + Individual.KEY_ID_Indi+ " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_Title
                + " FROM "
                + Indi_Coop.TABLE
                + " INNER JOIN " + Individual.TABLE
                + " ON " +  Individual.TABLE + "." + Individual.KEY_ID_Indi+ " = " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_SecondPartID
                + " WHERE " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_FirstPartID + " = ' " + consID + " ';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor getHistory(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Cons_Phase.TABLE + "." + Cons_Phase.KEY_ID_Cons_Phase+ " , "
                + Cons_Phase.TABLE + "." + Cons_Phase.KEY_Phase + " , "
                + Cons_Phase.TABLE + "." + Cons_Phase.KEY_PhaseDate
                + " FROM "
                + Cons_Phase.TABLE
                + " WHERE " + Cons_Phase.TABLE + "." + Cons_Phase.KEY_IndID + " = ' " + consID + " ';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor testIndiCoop(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + " * "
                + " FROM "
                + Indi_Coop.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor consPhase(String indiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + " * "
                + " FROM "
                + Cons_Phase.TABLE
                + " WHERE " + Cons_Phase.TABLE + "." + Cons_Phase.KEY_IndID + " = " + indiID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    } 
    public Cursor showAllConsIndi(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String showQuery = " SELECT "
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + ","
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_consName + ","
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lat + ","
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lon + ","
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase + ","
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate
                + " FROM " + CreateViews.Construction.VIEW
                + " INNER JOIN " + CreateViews.LastUpConsInd.VIEW
                + " ON " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_ID_cons
                + " ORDER BY " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lat + "," + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lon + " ASC ";
        Cursor cursor = db.rawQuery(showQuery, null);
        return cursor;
    }
    public Cursor showAllConsIndiWhereFilter01(String state01,String state02){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String showQuery = " SELECT "
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + ","
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_consName + ","
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lat + ","
                + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lon + ","
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase + ","
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate
                + " FROM " + CreateViews.Construction.VIEW
                + " INNER JOIN " + CreateViews.LastUpConsInd.VIEW
                + " ON " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_ID_cons
                + " WHERE " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase + " BETWEEN " + state01 +" and  " + state02
                + " ORDER BY " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lat + "," + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lon + " ASC ";
        Cursor cursor = db.rawQuery(showQuery, null);
        return cursor;
    }
    public String getGeopID(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Indi_Geop.KEY_GeopID
                + " FROM "
                + Indi_Geop.TABLE
                + " WHERE " + Indi_Geop.KEY_IndiID + " = " + IndiID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            if (cursor.getCount()== 1){
                return cursor.getString(0);
            }else {
                return "!solo";
            }
        }else{
            return "!solo";
        }
    }
}
