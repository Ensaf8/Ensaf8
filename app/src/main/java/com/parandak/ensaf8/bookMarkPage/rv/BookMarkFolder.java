package com.parandak.ensaf8.bookMarkPage.rv;

public class BookMarkFolder {
    String id ,title;

    public BookMarkFolder() {
    }

    public BookMarkFolder(String id, String title) {
        this.id = id;
        this.title = title;
    }

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
