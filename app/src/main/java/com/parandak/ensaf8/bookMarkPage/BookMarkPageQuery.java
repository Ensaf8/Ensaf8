package com.parandak.ensaf8.bookMarkPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.bookMarkPage.rv.BookMarkFolder;
import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Atten;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Place_Geop;

import java.util.ArrayList;

public class BookMarkPageQuery {
    public String getIndiIDBookMark(String B_type_id){
        return "SELECT " + BookMark.KEY_IndID
                + " FROM " + BookMark.TABLE
                + " WHERE " + BookMark.KEY_B_TYPE_ID + " = " + B_type_id
                + " ORDER BY " + BookMark.KEY_IndID + " ASC";
    }


    public String getIndiCoopBookMark(String B_type_id){
        return " SELECT "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_FirstPartID + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_SecondPartID + " , "
                + Indi_Coop.TABLE + "." + Indi_Coop.KEY_Title + " , "
                + Individual.TABLE + "." + Individual.KEY_IndiName
                + " FROM "
                + Indi_Coop.TABLE
                + " INNER JOIN " + Individual.TABLE
                + " ON " +  Individual.TABLE + "." + Individual.KEY_ID_Indi+ " = " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_SecondPartID
                + " WHERE " + Indi_Coop.TABLE + "." + Indi_Coop.KEY_FirstPartID + " IN ("
                    + getIndiIDBookMark(B_type_id) + ")";
    }


    /*public String getIndiBookMark(String B_type_id){
        return "SELECT " + Individual.TABLE + "." + Individual.KEY_ID_Indi + ","
                + Individual.TABLE + "." + Individual.KEY_IndiName + ","
                + Individual.TABLE + "." + Individual.KEY_IsCons + ","
                + Individual.syncLink.TABLE_F + "." + Individual.syncLink.KEY_IndiFID + ","
                + Individual.syncLink.TABLE_F + "." + Individual.syncLink.KEY_CusId
                + " FROM " + Individual.TABLE
                + " LEFT JOIN " + Individual.syncLink.TABLE_F
                + " ON " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " = "
                + Individual.syncLink.TABLE_F + "." + Individual.syncLink.KEY_IndiID
                + " WHERE " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
        //        + " ORDER BY " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " ASC";
        //        + " ORDER BY " + BookMark.KEY_IndID + " ASC";
    }*/

    public String getIndiBookMark(String B_type_id){
        return "SELECT " + Individual.TABLE + "." + Individual.KEY_ID_Indi + ","
                + Individual.TABLE + "." + Individual.KEY_IndiName + ","
                + Individual.TABLE + "." + Individual.KEY_IsCons
                + " FROM " + Individual.TABLE
                + " WHERE " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
        //        + " ORDER BY " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " ASC";
        //        + " ORDER BY " + BookMark.KEY_IndID + " ASC";
    }

    public String getIndiF(String indiId){
        return "SELECT " + Individual.syncLink.KEY_IndiFID + ","
                + Individual.syncLink.KEY_CusId
                + " FROM " + Individual.syncLink.TABLE_F
                + " WHERE " + Individual.syncLink.KEY_IndiID + " = " + indiId;
    }

    public String getIndiBookMarkIndiF(String B_type_id){
        return "SELECT " + Individual.TABLE + "." + Individual.KEY_ID_Indi + ","
                + Individual.TABLE + "." + Individual.KEY_IndiName + ","
                + Individual.TABLE + "." + Individual.KEY_IsCons
                + " FROM " + Individual.TABLE
                + " WHERE " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
        //        + " ORDER BY " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " ASC";
        //        + " ORDER BY " + BookMark.KEY_IndID + " ASC";
    }

    public String getGPointBookMark(String B_type_id){
        return "SELECT " + Indi_Geop.TABLE + "." + Indi_Geop.KEY_IndiID + ","
                + GPoint.TABLE + "." + GPoint.KEY_IDGeop + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lat + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lon
                + " FROM " + GPoint.TABLE
                + " INNER JOIN " + Indi_Geop.TABLE
                + " ON " + Indi_Geop.TABLE + "." + Indi_Geop.KEY_GeopID
                + " = " + GPoint.TABLE + "." + GPoint.KEY_IDGeop
                + " WHERE " + Indi_Geop.TABLE + "." + Indi_Geop.KEY_IndiID + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
                //+ " ORDER BY " + Indi_Geop.TABLE + "." + Indi_Geop.KEY_IndiID + " ASC";

    }

    public String getGPointPlaceBookMark(String B_type_id){
        return "SELECT " + Place_Geop.TABLE + "." + Place_Geop.KEY_IndiID + ","
                + GPoint.TABLE + "." + GPoint.KEY_IDGeop + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lat + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lon + ","
                + Place_Geop.TABLE + "." + Place_Geop.KEY_IconID
                + " FROM " + GPoint.TABLE
                + " INNER JOIN " + Place_Geop.TABLE
                + " ON " + Place_Geop.TABLE + "." + Place_Geop.KEY_GeopID
                + " = " + GPoint.TABLE + "." + GPoint.KEY_IDGeop
                + " WHERE " + Place_Geop.TABLE + "." + Place_Geop.KEY_IndiID + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
        //+ " ORDER BY " + Indi_Geop.TABLE + "." + Indi_Geop.KEY_IndiID + " ASC";

    }

    public String getPhaseBookMark(String B_type_id){
        return "SELECT " + Cons_Phase.TABLE + "." + Cons_Phase.KEY_ID_Cons_Phase + ","
                + Cons_Phase.TABLE + "." + Cons_Phase.KEY_IndID + ","
                + Cons_Phase.TABLE + "." + Cons_Phase.KEY_Phase + ","
                + Cons_Phase.TABLE + "." + Cons_Phase.KEY_PhaseDate
                + " FROM " + Cons_Phase.TABLE
                + " WHERE " + Cons_Phase.TABLE + "." + Cons_Phase.KEY_IndID + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
                //+ " ORDER BY " + Cons_Phase.TABLE + "." + Cons_Phase.KEY_IndID + " ASC";

    }

    public String getAttenBookMark(String B_type_id){
        return "SELECT " + Atten.TABLE + "." + Atten.KEY_ID_Atten + ","
                + Atten.TABLE + "." + Atten.KEY_Ind1ID + ","
                + Atten.TABLE + "." + Atten.KEY_Ind2ID + ","
                + Atten.TABLE + "." + Atten.KEY_AttenDate
                + " FROM " + Atten.TABLE
                + " WHERE " + Atten.TABLE + "." + Atten.KEY_Ind2ID + " IN "
                + "(" + getIndiIDBookMark(B_type_id) + ")";
        //+ " ORDER BY " + Cons_Phase.TABLE + "." + Cons_Phase.KEY_IndID + " ASC";

    }

    public Cursor runSqlQuery(String sql){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        return  db.rawQuery(sql, null);
    }

    public Cursor getBookMarkType(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + BookMarkType.KEY_ID + ","
                + BookMarkType.KEY_TITLE
                + " FROM " + BookMarkType.TABLE
                + " ORDER BY " + BookMarkType.KEY_ID + " ASC ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
    public ArrayList<String> getBookMarkFolderListString(){
        ArrayList<String> bookMarkFolderList = new ArrayList<>();
        Cursor cursor  = getBookMarkType();
        if (cursor.moveToFirst()){
            do{
                bookMarkFolderList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return bookMarkFolderList;
    }

    public ArrayList<String> getBookMarkFolderListIntID(){
        ArrayList<String> bookMarkFolderList = new ArrayList<>();
        Cursor cursor  = getBookMarkType();
        if (cursor.moveToFirst()){
            do{
                bookMarkFolderList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return bookMarkFolderList;
    }

    public ArrayList<BookMarkFolder> getBookMarkFoldeList(){
        ArrayList<BookMarkFolder> bookMarkFoldeList = new ArrayList<>();
        Cursor cursor  = getBookMarkType();
        if (cursor.moveToFirst()){
            bookMarkFoldeList.clear();
            do{
                bookMarkFoldeList.add(new BookMarkFolder(cursor.getString(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return bookMarkFoldeList;
    }

}
