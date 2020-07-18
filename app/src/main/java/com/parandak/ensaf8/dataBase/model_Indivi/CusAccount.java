package com.parandak.ensaf8.dataBase.model_Indivi;

public class CusAccount {

    public static final String TABLE = "Customer";

    public static final String KEY_ID_Cus = "ID_Cus";
    public static final String KEY_IndID = "indID";
    public static final String KEY_AccountName = "accountName";
    public static final String KEY_PassWord = "passWord";
    public static final String KEY_IsAct = "isAct";

    private String ID_Cus;
    private String indID ;
    private String accountName ;
    private String passWord ;
    private String isAct ;

    public String getID_Cus() {
        return ID_Cus;
    }

    public void setID_Cus(String ID_Cus) {
        this.ID_Cus = ID_Cus;
    }

    public String getIndID() {
        return indID;
    }

    public void setIndID(String indID) {
        this.indID = indID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getIsAct() {
        return isAct;
    }

    public void setIsAct(String isAct) {
        this.isAct = isAct;
    }
}
