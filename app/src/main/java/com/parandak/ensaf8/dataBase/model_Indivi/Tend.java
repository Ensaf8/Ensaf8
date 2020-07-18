package com.parandak.ensaf8.dataBase.model_Indivi;

public class Tend {

    public static final String TABLE = "Tend";

    public static final String KEY_ID_Tend = "ID_Tend";
    public static final String KEY_Ind1ID = "ind1ID";
    public static final String KEY_Ind2ID = "ind2ID";
    public static final String KEY_Title = "title";
    public static final String KEY_Detail = "detail";
    public static final String KEY_TendDate = "tendDate";
    public static final String KEY_IsChecked = "isChecked";



    private String ID_Tend;
    private String ind1ID ;
    private String ind2ID ;
    private String title ;
    private String detail ;
    private String tendDate ;
    private String isChecked ;

    public String getID_Tend() {
        return ID_Tend;
    }

    public void setID_Tend(String ID_Tend) {
        this.ID_Tend = ID_Tend;
    }

    public String getInd1ID() {
        return ind1ID;
    }

    public void setInd1ID(String ind1ID) {
        this.ind1ID = ind1ID;
    }

    public String getInd2ID() {
        return ind2ID;
    }

    public void setInd2ID(String ind2ID) {
        this.ind2ID = ind2ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTendDate() {
        return tendDate;
    }

    public void setTendDate(String tendDate) {
        this.tendDate = tendDate;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
