package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;

public class BookMarkTypeRepo {
    BookMarkType bookMarkType;
    public BookMarkTypeRepo(){
        bookMarkType = new BookMarkType();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ BookMarkType.TABLE+" ("
                + BookMarkType.KEY_ID+" INTEGER "+" , "
                + BookMarkType.KEY_TITLE+" TEXT "+" , "
                + " PRIMARY KEY(" + BookMarkType.KEY_ID + ")"
                +");";

    }
    public static String initTable(){
        return "INSERT INTO " + BookMarkType.TABLE + "(id,title) VALUES(1,\"defalt\");" ;
    }

    public int insert (BookMarkType bookMarkType){
        int bookMarkTypeID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(BookMarkType.KEY_ID,bookMarkType.getId());
        values.put(BookMarkType.KEY_TITLE,bookMarkType.getTitle());
        bookMarkTypeID = (int) db.insert(BookMarkType.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return bookMarkTypeID;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(BookMarkType.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean delete_ID_BookMarkType(String ID_BookMarkType){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(BookMarkType.TABLE,BookMarkType.KEY_ID + "=?",new String[]{ID_BookMarkType})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
}
