package com.parandak.ensaf8.dataBase.model_Indivi;

public class BookMarkType {
    public static final String TABLE = "BookMarkType";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
