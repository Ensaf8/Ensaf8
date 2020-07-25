package com.parandak.ensaf8.mapPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CreateViews;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;

public class MapPageQuery {
    boolean check01;
    boolean check02;
    boolean check03;
    String tendTitle;
    String date01;
    String date02;
    public MapPageQuery() {

    }
    public MapPageQuery(boolean check01,boolean check02,String tendTitle,boolean check03,String date01,String date02){
        this.check01 = check01;
        this.check02 = check02;
        this.tendTitle = tendTitle;
        this.check03 = check03;
        this.date01 = date01;
        this.date02 = date02;
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
    public Cursor getHistoryConsPhase(String consID){
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
    public Cursor showConsIndiWhereFilter02(String state01, String state02){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String stmt01 = "";
        String stmt02 = "";
                if(check01 && !check03){
                    stmt01 = " WHERE " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase +
                            " BETWEEN " + state01 +" and  " + state02;
                }else if(check03 && !check01){
                    stmt01 = " WHERE " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate +
                            " BETWEEN " + "'" + date01 + "'" +" and  " + "'" + date02 + "'";
                }else if (check03 && check01){
                    stmt01 = " WHERE (" + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase +
                            " BETWEEN " + state01 +" and  " + state02 +" ) and (" +
                            CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate +
                            " BETWEEN " + "'" + date01 + "'" +" and  " + "'" + date02 + "')";
                }
                if(check02){
                    stmt02 = " INNER JOIN " + Tend.TABLE
                            + " ON " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + Tend.TABLE +"."+ Tend.KEY_Ind2ID;
                    //if(tendTitle != null){
                      //  stmt01 = " WHERE (" + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase + " BETWEEN " + state01 +" and  " + state02 + ") and " + Tend.TABLE + "." +Tend.KEY_Title + " LIKE '" + tendTitle + "'";
                    //}
                }
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
                + stmt02
                + stmt01
                + " GROUP BY " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons
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
