package com.parandak.ensaf8.mapPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Atten;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CreateViews;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Place_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Rating;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;
import com.parandak.ensaf8.mapPage.drawer.FragmentDrawer_map;

import java.util.ArrayList;
import java.util.List;

public class MapPageQuery {
    private final String TAG = this.getClass().getSimpleName();
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
                + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate + " , "
                //+ BookMark.TABLE + "." + BookMark.KEY_B_TYPE_ID + " , "
                + Rating.TABLE + "." + Rating.KEY_Rate
                + " FROM "
                + CreateViews.Construction.VIEW
                + " INNER JOIN " + CreateViews.LastUpConsInd.VIEW
                + " ON " +  CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_ID_cons
                //+ " LEFT JOIN " + BookMark.TABLE
                //+ " ON " +  CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + BookMark.TABLE + "." + BookMark.KEY_IndID
                + " LEFT JOIN " + Rating.TABLE
                + " ON " +  CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + Rating.TABLE + "." + Rating.KEY_IndID
                + " WHERE " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = ' " + consID + " ';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public Cursor singleTapOnPlaceIndFirst(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Individual.TABLE + "." + Individual.KEY_ID_Indi + " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName + " , "
                + GPoint.TABLE + "." + GPoint.KEY_Lat + " , "
                + GPoint.TABLE + "." + GPoint.KEY_Lon + " , "
                + Place_Geop.TABLE + "." + Place_Geop.KEY_IconID + " , "
                + Rating.TABLE + "." + Rating.KEY_Rate
                + " FROM "
                + Individual.TABLE
                + " INNER JOIN " + Place_Geop.TABLE
                + " ON " +  Individual.TABLE + "." + Individual.KEY_ID_Indi + " = " + Place_Geop.TABLE + "." + Place_Geop.KEY_IndiID
                + " INNER JOIN " + GPoint.TABLE
                + " ON " +  Place_Geop.TABLE + "." + Place_Geop.KEY_GeopID + " = " + GPoint.TABLE + "." + GPoint.KEY_IDGeop
                + " LEFT JOIN " + Rating.TABLE
                + " ON " +  Individual.TABLE + "." + Individual.KEY_ID_Indi + " = " + Rating.TABLE + "." + Rating.KEY_IndID
                + " WHERE " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " = ' " + consID + " ';";

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
    public Cursor singleTapOnConsIndThirdBook(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + BookMark.TABLE + "." + BookMark.KEY_B_TYPE_ID + " , "
                + BookMarkType.TABLE + "." + BookMarkType.KEY_TITLE
                + " FROM "
                + BookMark.TABLE
                + " INNER JOIN " + BookMarkType.TABLE
                + " ON " +  BookMark.TABLE + "." + BookMark.KEY_B_TYPE_ID+ " = " + BookMarkType.TABLE + "." + BookMarkType.KEY_ID
                + " WHERE " + BookMark.TABLE + "." + BookMark.KEY_IndID + " = ' " + consID + " ';";

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
    public Cursor getAtten(String consID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Atten.TABLE + "." + Atten.KEY_Ind1ID + " , "
                + Atten.TABLE + "." + Atten.KEY_AttenDate
                + " FROM "
                + Atten.TABLE
                + " WHERE " + Atten.TABLE + "." + Atten.KEY_Ind2ID + " = ' " + consID + " ';";

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
    }//TODO clear useless codes. make decision about MapPageQuery & BookMarkPageQuery
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
    public Cursor showConsIndiWhereFilter02(FragmentDrawer_map fragmentDrawer_map){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String whereStmt = "";
        int i = 0;
        String innerJoinStmt = "";
        if (fragmentDrawer_map.isCheck01()){
            i++;
            whereStmt = whereStmt + " WHERE " + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhase +
                    " BETWEEN " + fragmentDrawer_map.getSeekProgress01() +" and  " + fragmentDrawer_map.getSeekProgress02();
        }
        //if(fragmentDrawer_map.isCheck02()){//TODO add check02
            //innerJoinStmt = " INNER JOIN " + Tend.TABLE
                    //+ " ON " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + Tend.TABLE +"."+ Tend.KEY_Ind2ID;
        //}
        if (fragmentDrawer_map.isCheck03()){
            String a = " WHERE ";
            if (i>0){
                a = " AND ";
            }
            i++;
            whereStmt = whereStmt + a + CreateViews.LastUpConsInd.VIEW + "." + CreateViews.LastUpConsInd.KEY_lastPhaseDate +
                    " BETWEEN " + "'" + fragmentDrawer_map.getDateFilter01() + "'" +" and  " + "'" + fragmentDrawer_map.getDateFilter02() + "'";
        }
        if(fragmentDrawer_map.isCheck04()){
            innerJoinStmt = innerJoinStmt + " INNER JOIN " + Rating.TABLE
                    + " ON " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + Rating.TABLE +"."+ Rating.KEY_IndID;
            String a = " WHERE ";
            if (i>0){
                a = " AND ";
            }
            i++;
            whereStmt = whereStmt + a  + Rating.TABLE + "." + Rating.KEY_Rate + "=" + fragmentDrawer_map.getRating() ;
        }
        if(fragmentDrawer_map.isCheckBook()){
            innerJoinStmt = innerJoinStmt + " INNER JOIN " + BookMark.TABLE
                    + " ON " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons + " = " + BookMark.TABLE +"."+ BookMark.KEY_IndID;
            String a = " WHERE ";
            if (i>0){
                a = " AND ";
            }
            whereStmt = whereStmt + a + BookMark.TABLE + "." + BookMark.KEY_B_TYPE_ID + " IN "
                    + fragmentDrawer_map.getSelectedBookTypeID();
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
                + innerJoinStmt
                + whereStmt
                + " GROUP BY " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_ID_cons
                + " ORDER BY " + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lat + "," + CreateViews.Construction.VIEW + "." + CreateViews.Construction.KEY_lon + " ASC ";
        //Log.d("ensaf::::::::", TAG + ">showConsIndiWhereFilter02 >showQuery : " + showQuery );
        return db.rawQuery(showQuery, null);
    }//TODO use string builders for queries
    public Cursor showAllPlaceGPoint(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String showQuery = " SELECT "
                + Individual.TABLE + "." + Individual.KEY_ID_Indi + ","
                + Individual.TABLE + "." + Individual.KEY_IndiName + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lat + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lon + ","
                + Place_Geop.TABLE + "." + Place_Geop.KEY_IconID
                + " FROM " + Individual.TABLE
                + " INNER JOIN " + Place_Geop.TABLE
                + " ON " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " = " + Place_Geop.TABLE + "." + Place_Geop.KEY_IndiID
                + " INNER JOIN " + GPoint.TABLE
                + " ON " +  Place_Geop.TABLE + "." + Place_Geop.KEY_GeopID + " = " + GPoint.TABLE + "." + GPoint.KEY_IDGeop;
        return db.rawQuery(showQuery, null);
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
    public String getGeopIDFromPlace(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + Place_Geop.KEY_GeopID
                + " FROM "
                + Place_Geop.TABLE
                + " WHERE " + Place_Geop.KEY_IndiID + " = " + IndiID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            if (cursor.getCount()== 1){
                return cursor.getString(0);
            }else {
                return null;
            }
        }else{
            return null;
        }
    }
    public String getBookmarkTypeTitle(String BookTypeID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + BookMarkType.KEY_TITLE
                + " FROM "
                + BookMarkType.TABLE
                + " WHERE " + BookMarkType.KEY_ID + " = " + BookTypeID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()&&cursor.getCount()==1){
            return cursor.getString(0);
        }else {
            return "error";
        }
    }
    public List<BookMarkType> getBookMarkTypeList(){
        List<BookMarkType> bookMarkTypeList = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + BookMarkType.KEY_ID + ","
                + BookMarkType.KEY_TITLE
                + " FROM " + BookMarkType.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            bookMarkTypeList.clear();
            BookMarkType bookMarkType;
            do{
                bookMarkType = new BookMarkType();
                bookMarkType.setId(cursor.getString(0));
                bookMarkType.setTitle(cursor.getString(1));
                bookMarkTypeList.add(bookMarkType);
            }while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return bookMarkTypeList;
    }
}
