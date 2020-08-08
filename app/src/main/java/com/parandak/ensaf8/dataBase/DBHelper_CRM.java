package com.parandak.ensaf8.dataBase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parandak.ensaf8.app.App;

import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.CreateViews;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Cons_PhaseRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.CusAccountRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.GPointRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_CoopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_GeopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.PhoneNumRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.TendRepo;


public class DBHelper_CRM extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =28;
    // Database Name
    private static final String DATABASE_NAME = "ensaf8.db";
    private static final String TAG = DBHelper_CRM.class.getSimpleName();

    public DBHelper_CRM() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Individual tables will create here
        db.execSQL(Cons_PhaseRepo.createTable());
        db.execSQL(CusAccountRepo.createTable());
        db.execSQL(GPointRepo.createTable());
        db.execSQL(Indi_CoopRepo.createTable());
        db.execSQL(Indi_GeopRepo.createTable());
        db.execSQL(IndividualRepo.createTable());
        db.execSQL(PhoneNumRepo.createTable());
        db.execSQL(PhoneNumRepo.createNewTable());
        db.execSQL(TendRepo.createTable());
        db.execSQL(BookMarkRepo.createTable());
        //db.execSQL(Cons_Phase_IntRepo.createTable() );
        //db.execSQL("DROP TABLE IF EXISTS Cons_Phase" );
        db.execSQL(CreateViews.createConstruction());
        db.execSQL(CreateViews.createLastUpConsInd());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        //db.execSQL("DROP TABLE IF EXISTS " + Cons_Phase.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + CusAccount.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + GPoint.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Indi_Coop.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Indi_Geop.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Individual.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + PhoneNum.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Tend.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Cons_Phase.TABLEint);


        onCreate(db);

    }


}
