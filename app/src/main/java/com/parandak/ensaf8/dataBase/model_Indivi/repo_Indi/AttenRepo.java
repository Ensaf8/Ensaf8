package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Atten;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;



public class AttenRepo {
    Atten atten;

    public AttenRepo(){
        this.atten = new Atten();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Atten.TABLE+" ("
                + Atten.KEY_ID_Atten +" INTEGER "+" , "
                + Atten.KEY_Ind1ID +" INTEGER "+" , "
                + Atten.KEY_Ind2ID +" INTEGER "+" , "
                + Atten.KEY_AttenDate +" TEXT "+" , "
                + " PRIMARY KEY(" + Atten.KEY_ID_Atten + ")"
                + " FOREIGN KEY(" + Atten.KEY_Ind1ID + ")"
                + " REFERENCES " + Individual.TABLE + " ( " + Individual.KEY_ID_Indi + ") ON DELETE CASCADE " + ","
                + " FOREIGN KEY(" + Atten.KEY_Ind2ID + ")"
                + " REFERENCES " + Individual.TABLE + " ( " + Individual.KEY_ID_Indi + ") ON DELETE CASCADE "
                +");";

    }

    public int insert (Atten atten){
        int attenId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Atten.KEY_Ind1ID,atten.getInd1ID());
        values.put(Atten.KEY_Ind2ID,atten.getInd2ID());
        values.put(Atten.KEY_AttenDate,atten.getAttenDate());
        attenId = (int) db.insert(Atten.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return attenId;
    }


    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Atten.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteID_Atten(String ID_Atten){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Atten.TABLE,Atten.KEY_ID_Atten + "=?",new String[]{ID_Atten})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }
}
