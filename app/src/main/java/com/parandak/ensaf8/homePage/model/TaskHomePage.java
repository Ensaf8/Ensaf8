package com.parandak.ensaf8.homePage.model;

public class TaskHomePage {
    private String customerName;
    private String title;
    private String dateTime;
    private String main;
    private String tendID;

    public String getIndi2ID() {
        return indi2ID;
    }

    public void setIndi2ID(String indi2ID) {
        this.indi2ID = indi2ID;
    }

    private String indi2ID;
    private boolean checked;

    public TaskHomePage(String tendID, String customerName,String title, String dateTime, String main, boolean checked,String indi2ID){
        this.customerName = customerName;
        this.title = title;
        this.dateTime = dateTime;
        this.main = main;
        this.checked = checked;
        this.tendID = tendID;
        this.indi2ID = indi2ID;
    }

    public String getTendID() {
        return tendID;
    }

    public void setTendID(String tendID) {
        this.tendID = tendID;
    }

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public String getMain(){
        return main;
    }

    public void setMain(String main){
        this.main = main;
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }
}
