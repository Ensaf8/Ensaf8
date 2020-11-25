package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;

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
                + BookMark.KET_B_TYPE_ID+" INTEGER DEFAULT 1 "+" , "
                + " CONSTRAINT " + BookMark.CONSTRAINT_BOOK_MARK_TYPE + " FOREIGN KEY (" + BookMark.KET_B_TYPE_ID + ")"
                + " REFERENCES " + BookMarkType.TABLE + "(" + BookMarkType.KEY_ID + ")"
                + " ON DELETE CASCADE "
                + " PRIMARY KEY(" + BookMark.KEY_ID_BookMark + ")"
                +");";

    }



    public int insert (BookMark bookMark){
        int bookMarkID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(BookMark.KEY_IndID,bookMark.getIndID());
        bookMarkID = (int) db.insert(BookMark.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return bookMarkID;
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
}
