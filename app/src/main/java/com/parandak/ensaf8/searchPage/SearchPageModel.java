package com.parandak.ensaf8.searchPage;

public class SearchPageModel {

    private String id, name, title, firstPart;

    public SearchPageModel(){

    }

    public SearchPageModel(String name, String title){
        this.name = name;
        this.title = title;

    }

    public SearchPageModel(String id, String name, String title){
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getFirstPart(){
        return firstPart;
    }

    public void setFirstPart(String firstPart){
        this.firstPart = firstPart;
    }

}
