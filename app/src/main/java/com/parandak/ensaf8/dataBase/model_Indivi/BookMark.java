package com.parandak.ensaf8.dataBase.model_Indivi;

public class BookMark {
    public static final String TABLE = "BookMark";

    public static final String KEY_ID_BookMark = "ID_BookMark";
    public static final String KEY_IndID = "indID";
    public static final String KET_B_TYPE_ID = "B_type_id";
    public static final String CONSTRAINT_BOOK_MARK_TYPE = "fk_BookMarkType";
    public static final String CONSTRAINT_INDIVIDUAL = "fk_Individual";

    private String ID_BookMark;
    private String indID;
    private String B_type_id;

    public String getID_BookMark() {
        return ID_BookMark;
    }

    public void setID_BookMark(String ID_BookMark) {
        this.ID_BookMark = ID_BookMark;
    }

    public String getIndID() {
        return indID;
    }

    public void setIndID(String indID) {
        this.indID = indID;
    }

    public String getB_type_id() {
        return B_type_id;
    }

    public void setB_type_id(String b_type_id) {
        B_type_id = b_type_id;
    }
}
