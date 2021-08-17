package com.parandak.ensaf8.bookMarkPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.bookMarkPage.rv.BookMarkFolder;
import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;

import java.util.ArrayList;

public class BookMarkPageQuery {
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

    public ArrayList<Integer> getBookMarkFolderListIntID(){
        ArrayList<Integer> bookMarkFolderList = new ArrayList<>();
        Cursor cursor  = getBookMarkType();
        if (cursor.moveToFirst()){
            do{
                bookMarkFolderList.add(cursor.getInt(0));
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
