package com.parandak.ensaf8.dataBase.model_Indivi;

public class Cons_Phase {

    public static final String TABLE = "Cons_Phase_Int";
    ////For reType phase
    public static final String TABLEint = "Cons_Phase_Int";
    public static final String KEY_ID_Cons_Phase = "ID_Cons_Phase";
    public static final String KEY_IndID = "indID";
    public static final String KEY_Phase = "phase";
    public static final String KEY_PhaseDate = "phaseDate";

    private String ID_Cons_Phase;
    private String indID ;
    private String phase ;
    private String phaseDate ;

    public String getID_Cons_Phase() {
        return ID_Cons_Phase;
    }

    public void setID_Cons_Phase(String ID_Cons_Phase) {
        this.ID_Cons_Phase = ID_Cons_Phase;
    }

    public String getIndID() {
        return indID;
    }

    public void setIndID(String indID) {
        this.indID = indID;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getPhaseDate() {
        return phaseDate;
    }

    public void setPhaseDate(String phaseDate) {
        this.phaseDate = phaseDate;
    }
}
