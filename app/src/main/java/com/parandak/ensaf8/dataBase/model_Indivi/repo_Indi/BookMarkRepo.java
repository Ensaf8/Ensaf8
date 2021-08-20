package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;

public class BookMarkRepo {
    BookMark bookMark;
    public BookMarkRepo(){
        bookMark = new BookMark();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ BookMark.TABLE+" ("
                + BookMark.KEY_ID_BookMark+" INTEGER "+" , "
                + BookMark.KEY_IndID+" INTEGER "+" , "
                + " PRIMARY KEY(" + BookMark.KEY_ID_BookMark + ")"
                +");";

    }

    public static String createTableFK(){
        return "CREATE TABLE IF NOT EXISTS "+ BookMark.TABLE+" ("
                + BookMark.KEY_ID_BookMark+" INTEGER "+" , "
                + BookMark.KEY_IndID+" INTEGER "+" , "
                + BookMark.KEY_B_TYPE_ID +" INTEGER DEFAULT 1 "+" , "
                + " CONSTRAINT " + BookMark.CONSTRAINT_BOOK_MARK_TYPE + " FOREIGN KEY (" + BookMark.KEY_B_TYPE_ID + ")"
                + " REFERENCES " + BookMarkType.TABLE + "(" + BookMarkType.KEY_ID + ")"
                + " ON DELETE CASCADE ,"
                + " CONSTRAINT " + BookMark.CONSTRAINT_INDIVIDUAL + " FOREIGN KEY (" + BookMark.KEY_IndID + ")"
                + " REFERENCES " + Individual.TABLE + "(" + Individual.KEY_ID_Indi + ")"
                + " ON DELETE CASCADE ,"
                + " PRIMARY KEY(" + BookMark.KEY_ID_BookMark + ")"
                +");";

    }

    public static String updateBookMarkTable(){
        return "ALTER TABLE " + BookMark.TABLE + " RENAME TO _BookMark_old;"
                + BookMarkRepo.createTableFK()
                + "INSERT INTO " + BookMark.TABLE + "(" + BookMark.KEY_ID_BookMark + "," + BookMark.KEY_IndID + ")"
                + "SELECT * FROM _BookMark_old;"
                + "DROP TABLE IF EXISTS _BookMark_old;";
    }

    public static String alterTable(){
        return "ALTER TABLE " + BookMark.TABLE + " RENAME TO _BookMark_old;";
    }

    public static String insertToBookMarkNew(){
        return "INSERT INTO " + BookMark.TABLE + "(" + BookMark.KEY_ID_BookMark + "," + BookMark.KEY_IndID + ")"
                + "SELECT * FROM _BookMark_old;";
    }

    public static String dropOldTable(){
        return "DROP TABLE IF EXISTS _BookMark_old;";
    }


    public int insert (BookMark bookMark){
        int bookMarkID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(BookMark.KEY_IndID,bookMark.getIndID());
        values.put(BookMark.KEY_B_TYPE_ID,bookMark.getB_type_id());
        bookMarkID = (int) db.insert(BookMark.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return bookMarkID;
    }

    public boolean updateTypeByIndiID(BookMark bookMark){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(Individual.KEY_ID_Indi,individual.getID_Indi());
        values.put(BookMark.KEY_B_TYPE_ID,bookMark.getB_type_id());
        return db.update(BookMark.TABLE,values,BookMark.KEY_IndID + "=?",new String[]{String.valueOf(bookMark.getIndID())}) > 0;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(BookMark.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean delete_indID_BookMark(String indID_BookMark){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(BookMark.TABLE,BookMark.KEY_IndID + "=?",new String[]{indID_BookMark})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }

    public void delete_indiID_BtypeID(String indID_BookMark,String bTypeID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + BookMark.TABLE
                + " WHERE " + BookMark.KEY_IndID + " = '" + indID_BookMark + "' AND "
                + BookMark.KEY_B_TYPE_ID + " = '" + bTypeID + "'";
        db.execSQL(sql);
    }
}
