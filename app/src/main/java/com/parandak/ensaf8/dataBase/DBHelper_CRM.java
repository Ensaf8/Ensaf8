package com.parandak.ensaf8.dataBase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parandak.ensaf8.app.App;

import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.CreateViews;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkTypeRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Cons_PhaseRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.CusAccountRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.GPointRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_CoopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_GeopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.PhoneNumRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.RatingRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.TendRepo;


public class DBHelper_CRM extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =33;
    // Database Name
    private static final String DATABASE_NAME = "ensaf8.db";
    private static final String TAG = DBHelper_CRM.class.getSimpleName();

    public DBHelper_CRM() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ensaf::::::::", TAG + "> onUpgrade : " + DATABASE_VERSION);
        //Individual tables will create here
        db.execSQL(Cons_PhaseRepo.createTable());
        db.execSQL(CusAccountRepo.createTable());
        db.execSQL(GPointRepo.createTable());
        db.execSQL(Indi_CoopRepo.createTable());
        db.execSQL(Indi_GeopRepo.createTable());
        db.execSQL(IndividualRepo.createTable());
        db.execSQL(PhoneNumRepo.createNewTable());
        db.execSQL(TendRepo.createTable());
        //db.execSQL(BookMarkRepo.createTable());
        db.execSQL(RatingRepo.createTable());
        db.execSQL(BookMarkTypeRepo.createTable());
        IndividualRepo.syncLink syncLinkF = new IndividualRepo.syncLink(true);
        db.execSQL(syncLinkF.createTable());
        Log.d("ensaf::::::::", TAG + "> syncLinkF : " + syncLinkF.createTable());
        IndividualRepo.syncLink syncLinkT = new IndividualRepo.syncLink(false);
        db.execSQL(syncLinkT.createTable());
        Log.d("ensaf::::::::", TAG + "> syncLinkT : " + syncLinkT.createTable());
        //db.execSQL(BookMarkRepo.updateBookMarkTable());
        //db.execSQL(BookMarkRepo.alterTable());
        db.execSQL(BookMarkRepo.createTableFK());
        //db.execSQL(BookMarkRepo.insertToBookMarkNew());
        //db.execSQL(BookMarkRepo.dropOldTable());
        Log.d("ensaf::::::::", TAG + "> initTable : " + BookMarkTypeRepo.initTable());
        //db.execSQL(Cons_Phase_IntRepo.createTable() );
        //db.execSQL("DROP TABLE IF EXISTS Cons_Phase" );
        db.execSQL(CreateViews.createConstruction());
        db.execSQL(CreateViews.createLastUpConsInd());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
        Log.d("ensaf::::::::", TAG + "> onUpgrade : " + String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
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
