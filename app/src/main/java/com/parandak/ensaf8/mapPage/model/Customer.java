package com.parandak.ensaf8.mapPage.model;

public class Customer {

    private String id, name, position, phoneNumber;

    public Customer(){

    }

    public Customer(String name, String position){
        this.name = name;
        this.position = position;

    }

    public Customer(String id, String name, String position){
        this.id = id;
        this.name = name;
        this.position = position;
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

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
