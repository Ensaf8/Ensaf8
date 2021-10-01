package com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;

public class Cons_PhaseRepo {
    private Cons_Phase cons_phase;

    public Cons_PhaseRepo() {
        cons_phase = new Cons_Phase();
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS "+ Cons_Phase.TABLE+" ("
                + Cons_Phase.KEY_ID_Cons_Phase+" INTEGER "+" , "
                + Cons_Phase.KEY_IndID+" INTEGER "+" , "
                + Cons_Phase.KEY_Phase+" TEXT "+" , "
                + Cons_Phase.KEY_PhaseDate+" TEXT "+" , "
                + " PRIMARY KEY(" + Cons_Phase.KEY_ID_Cons_Phase + ")"
                +");";

    }

    public int insert (Cons_Phase cons_phase){
        int cons_phaseId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Cons_Phase.KEY_ID_Cons_Phase,cons_phase.getID_Cons_Phase());
        values.put(Cons_Phase.KEY_IndID,cons_phase.getIndID());
        values.put(Cons_Phase.KEY_Phase,cons_phase.getPhase());
        values.put(Cons_Phase.KEY_PhaseDate,cons_phase.getPhaseDate());
        cons_phaseId = (int) db.insert(Cons_Phase.TABLE,null,values);
        DatabaseManager.getInstance().closeDatabase();
        return cons_phaseId;
    }

    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Cons_Phase.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean deleteIndiID(String IndiID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        boolean b = db.delete(Cons_Phase.TABLE,Cons_Phase.KEY_IndID + "=?",new String[]{IndiID})>0;
        DatabaseManager.getInstance().closeDatabase();
        return b;
    }


}
