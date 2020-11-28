package com.parandak.ensaf8.bookMarkPage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;

public class BookMarkPageQuery {
    public Cursor getBookMarkType(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT "
                + BookMarkType.KEY_ID + ","
                + BookMarkType.KEY_TITLE
                + " FROM " + BookMarkType.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        return cursor;
    }
}
