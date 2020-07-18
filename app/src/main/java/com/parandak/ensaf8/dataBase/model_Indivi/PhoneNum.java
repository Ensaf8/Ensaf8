package com.parandak.ensaf8.dataBase.model_Indivi;

public class PhoneNum {
    public static final String TABLE = "PhoneNumRepo";

    public static final String KEY_ID_Phone = "ID_Phone";
    public static final String KEY_CustomerID = "customerID";
    public static final String KEY_Num = "num";


    private String ID_Phone;
    private String customerID ;
    private String num ;


    public String getID_Phone() {
        return ID_Phone;
    }

    public void setID_Phone(String ID_Phone) {
        this.ID_Phone = ID_Phone;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
