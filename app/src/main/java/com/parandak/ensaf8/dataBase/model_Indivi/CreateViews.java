package com.parandak.ensaf8.dataBase.model_Indivi;

public class CreateViews {

    public static String createConstruction(){
        return " CREATE VIEW IF NOT EXISTS " + Construction.VIEW
                + " AS SELECT "
                + Individual.TABLE + "." +Individual.KEY_ID_Indi + " AS " + Construction.KEY_ID_cons + ","
                + Individual.TABLE + "." +Individual.KEY_IndiName + " AS " + Construction.KEY_consName + ","
                + GPoint.TABLE + "." + GPoint.KEY_Lat + " AS " + Construction.KEY_lat + ","
                + GPoint.TABLE + "." +GPoint.KEY_Lon + " AS " + Construction.KEY_lon
                + " FROM "
                + Individual.TABLE
                + " INNER JOIN " + Indi_Geop.TABLE
                + " ON " + Individual.TABLE + "." + Individual.KEY_ID_Indi + " = " + Indi_Geop.TABLE + "." + Indi_Geop.KEY_IndiID
                + " INNER JOIN " + GPoint.TABLE
                + " ON " +Indi_Geop.TABLE + "." + Indi_Geop.KEY_GeopID + " = " + GPoint.TABLE + "." + GPoint.KEY_IDGeop + ";";

    }

    public static class Construction {
        Construction(){

        }
        public static final String VIEW = "construction";

        public static final String KEY_ID_cons = "consID";
        public static final String KEY_consName = "consName";
        public static final String KEY_lat = "lat";
        public static final String KEY_lon = "lon";


    }

    public static String createLastUpConsInd(){
        return " CREATE VIEW IF NOT EXISTS " + LastUpConsInd.VIEW
                + " AS SELECT "
                + Cons_Phase.KEY_IndID + " AS " + LastUpConsInd.KEY_ID_cons+ ","
                + Cons_Phase.KEY_Phase + " AS " + LastUpConsInd.KEY_lastPhase+ ","
                + " MAX(" + Cons_Phase.KEY_PhaseDate + " )" + " AS " + LastUpConsInd.KEY_lastPhaseDate
                + " FROM "
                + Cons_Phase.TABLE
                + " GROUP BY " + Cons_Phase.KEY_IndID;
    }

    public static class LastUpConsInd{
        LastUpConsInd(){

        }


        public static final String VIEW = "lastUpConsInd";

        public static final String KEY_ID_cons = "consID";
        public static final String KEY_lastPhase = "lastPhase";
        public static final String KEY_lastPhaseDate = "lastPhaseDate";
    }

}
