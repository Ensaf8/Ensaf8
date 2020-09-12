package com.parandak.ensaf8.app;

import android.app.Application;
import android.content.Context;

import com.parandak.ensaf8.broadCast.ConnectivityReceiver;
import com.parandak.ensaf8.dataBase.DBHelper_CRM;
import com.parandak.ensaf8.dataBase.DatabaseManager;

public class App extends Application {
    public static Context context;
    private static App mInstance;

    private static DBHelper_CRM dbHelper_crm;

    @Override
    public void onCreate(){
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper_crm = new DBHelper_CRM();
        DatabaseManager.initializeInstance(dbHelper_crm);
        mInstance = this;
    }


    public static Context getContext(){
        return context;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
