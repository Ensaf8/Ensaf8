package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Rating;

public class RatingRepo {
    Rating rating;

    public RatingRepo(){
        this.rating = new Rating();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Rating.TABLE+" ("
                + Rating.KEY_ID_Rating +" INTEGER "+" , "
                + Rating.KEY_IndID+" INTEGER "+" , "
                + Rating.KEY_Rate+" INTEGER "+" , "
                + " PRIMARY KEY(" + Rating.KEY_ID_Rating + ")"
                +");";

    }

    public int insert (Rating rating){
        int ratingID;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Rating.KEY_IndID,rating.getIndID());
        values.put(Rating.KEY_Rate,rating.getRate());
        ratingID = (int) db.insert(Rating.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return ratingID;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Rating.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean delete_indID_Rating(String indID_BookMark){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Rating.TABLE,Rating.KEY_IndID + "=?",new String[]{indID_BookMark})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
     public boolean update_indID_Rating(Rating rating){
         SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
         ContentValues values = new ContentValues();
         values.put(Rating.KEY_Rate,rating.getRate());
         return db.update(Rating.TABLE,values,Rating.KEY_IndID + "=?",new String[]{String.valueOf(rating.getIndID())}) > 0;
     }
}
