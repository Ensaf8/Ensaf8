package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;


public class GPointRepo {
    GPoint gPoint;

    public GPointRepo() {
        this.gPoint = new GPoint();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ GPoint.TABLE+" ("
                + GPoint.KEY_IDGeop+" INTEGER "+" , "
                + GPoint.KEY_Lat+" TEXT "+" , "
                + GPoint.KEY_Lon+" TEXT "+" , "
                + GPoint.KEY_IsSolo+" INTEGER "+" , "
                + " PRIMARY KEY(" + GPoint.KEY_IDGeop + ")"
                +");";

    }

    public int insert (GPoint gPoint){
        int gPointId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(GPoint.KEY_IDGeop,gPoint.getIDGeop());
        values.put(GPoint.KEY_Lat,gPoint.getLat());
        values.put(GPoint.KEY_Lon,gPoint.getLon());
        values.put(GPoint.KEY_IsSolo,gPoint.getIsSolo());
        gPointId = (int) db.insert(GPoint.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return gPointId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(GPoint.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public String lastGPoint(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectLastConstruction = " SELECT "
                + GPoint.TABLE + "." + GPoint.KEY_IDGeop
                + " FROM "
                + GPoint.TABLE
                + " ORDER BY "
                + GPoint.TABLE + "." + GPoint.KEY_IDGeop + " DESC "
                + "LIMIT 1;";
        Cursor cursor = db.rawQuery(selectLastConstruction, null);
        String ss = "fuck....";
        if (cursor.moveToFirst()){
            ss = cursor.getString(0);
        }
        return ss;
    }

    public boolean deleteIDGeop(String IDGeop){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(GPoint.TABLE,GPoint.KEY_IDGeop + "=?",new String[]{IDGeop})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }

}
