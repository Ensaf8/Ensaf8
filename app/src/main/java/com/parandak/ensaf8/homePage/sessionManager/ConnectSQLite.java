package com.parandak.ensaf8.homePage.sessionManager;

import android.database.Cursor;

import com.parandak.ensaf8.homePage.HomePageQuery;

public class ConnectSQLite {

    HomePageQuery homePageQuery;

    public ConnectSQLite(){
        homePageQuery = new HomePageQuery();
    }

    public boolean checkIsUserName(String username){
        Cursor cursor = homePageQuery.checkUserName(username);
        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public String getPassWord(String username){
        Cursor cursor = homePageQuery.checkUserName(username);
        if (cursor.moveToFirst()){
            return cursor.getString(1);
        }else {
            return "error!";
        }
    }

    public String getID_UserName(String username){
        Cursor cursor = homePageQuery.checkUserName(username);
        if (cursor.moveToFirst()){
            return cursor.getString(2);
        }else {
            return "error!";
        }
    }

    public String getID_Customer(String username){
        Cursor cursor = homePageQuery.checkUserName(username);
        if (cursor.moveToFirst()){
            return cursor.getString(3);
        }else {
            return "error!";
        }
    }

    public int checkUserNameCount(String username){
        Cursor cursor = homePageQuery.checkUserName(username);
        return cursor.getCount();
    }
}
