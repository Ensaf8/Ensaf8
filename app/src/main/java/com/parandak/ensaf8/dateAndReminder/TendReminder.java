package com.parandak.ensaf8.dateAndReminder;

public class TendReminder {

    private String id,indi1,indi2, title, main, timeDate;

    /*public TendReminder(String id, String title, String main, String timeDate){
        this.id = id;
        this.title = title;
        this.main = main;
        this.timeDate = timeDate;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndi1() {
        return indi1;
    }

    public void setIndi1(String indi1) {
        this.indi1 = indi1;
    }

    public String getIndi2() {
        return indi2;
    }

    public void setIndi2(String indi2) {
        this.indi2 = indi2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }
}
