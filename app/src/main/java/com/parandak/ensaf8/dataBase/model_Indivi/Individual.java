package com.parandak.ensaf8.dataBase.model_Indivi;

public class Individual {

    public static final String TABLE = "Individual";

    public static final String KEY_ID_Indi = "ID_Indi";
    public static final String KEY_IndiName = "indiName";
    public static final String KEY_IsCons = "isCons";


    private String ID_Indi;
    private String indiName ;
    private String isCons ;

    public String getID_Indi() {
        return ID_Indi;
    }

    public void setID_Indi(String ID_Indi) {
        this.ID_Indi = ID_Indi;
    }

    public String getIndiName() {
        return indiName;
    }

    public void setIndiName(String indiName) {
        this.indiName = indiName;
    }

    public String getIsCons() {
        return isCons;
    }

    public void setIsCons(String isCons) {
        this.isCons = isCons;
    }

    public static class syncLink{
        public static final String TABLE_F = "Indi_f";
        public static final String TABLE_T = "Indi_t";

        public static final String KEY_ID = "_id";
        public static final String KEY_IndiID = "IndiID";
        public static final String KEY_CusId = "CusID";

        private String _id;
        private String IndiID ;
        private String CusID ;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getIndiID() {
            return IndiID;
        }

        public void setIndiID(String indiID) {
            IndiID = indiID;
        }

        public String getCusID() {
            return CusID;
        }

        public void setCusID(String cusID) {
            CusID = cusID;
        }
    }
}
