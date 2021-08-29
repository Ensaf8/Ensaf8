package com.parandak.ensaf8.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parandak.ensaf8.bookMarkPage.BookMarkPageQuery;
import com.parandak.ensaf8.dataBase.DatabaseManager;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.PhoneNum;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;
import com.parandak.ensaf8.homePage.HomePageActivity;
import com.parandak.ensaf8.homePage.HomePageQuery;
import com.parandak.ensaf8.mapPage.MapActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianDate;
import static com.parandak.ensaf8.homePage.HomePageActivity.ID_CONNECT_Indi1;


public class EnsafQueryExport {
    public static String customerID = "customerID";
    public EnsafQueryExport(){

    }
    public String exportFromBookMark(String bTypeID){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        StringBuilder XMLensaf0 = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n");
        StringBuilder Ensaf = new StringBuilder("<ensaf></ensaf>");
        StringBuilder XMLall0000 = new StringBuilder();
        BookMarkPageQuery bookMarkPageQuery = new BookMarkPageQuery();
        Cursor phaseCursor = bookMarkPageQuery.runSqlQuery(bookMarkPageQuery.getPhaseBookMark(bTypeID));
        Cursor gPointCursor = bookMarkPageQuery.runSqlQuery(bookMarkPageQuery.getGPointBookMark(bTypeID));
        Cursor indiCursor = bookMarkPageQuery.runSqlQuery(bookMarkPageQuery.getIndiBookMark(bTypeID));
        Cursor indiCoopCursor = bookMarkPageQuery.runSqlQuery(bookMarkPageQuery.getIndiCoopBookMark(bTypeID));
        XMLall0000.append(
                "   \n <info>  \n" +
                "        <" + customerID + ">" + HomePageActivity.ID_CONNECT_Customer + "</" + customerID + "> \n" +
                "    </info>  \n");
        if (phaseCursor.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+Cons_Phase.TABLE+">  \n" +
                                "        <"+Cons_Phase.KEY_ID_Cons_Phase+">" + phaseCursor.getString(0) + "</"+Cons_Phase.KEY_ID_Cons_Phase+">  \n" +
                                "        <"+Cons_Phase.KEY_IndID+">" + phaseCursor.getString(1) + "</"+Cons_Phase.KEY_IndID+">  \n" +
                                "        <"+Cons_Phase.KEY_Phase+">" + phaseCursor.getString(2) + "</"+Cons_Phase.KEY_Phase+"> \n" +
                                "        <"+Cons_Phase.KEY_PhaseDate+">" + phaseCursor.getString(3) + "</"+Cons_Phase.KEY_PhaseDate+"> \n" +
                                "    </" + Cons_Phase.TABLE + ">  \n");
            }while (phaseCursor.moveToNext());
        }
        if (gPointCursor.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+GPoint.TABLE+">  \n" +
                                "        <"+Indi_Geop.KEY_IndiID+">" + gPointCursor.getString(0) + "</"+Indi_Geop.KEY_IndiID+">  \n" +
                                "        <"+GPoint.KEY_IDGeop+">" + gPointCursor.getString(1) + "</"+GPoint.KEY_IDGeop+">  \n" +
                                "        <"+GPoint.KEY_Lat+">" + gPointCursor.getString(2) + "</"+GPoint.KEY_Lat+">  \n" +
                                "        <"+GPoint.KEY_Lon+">" + gPointCursor.getString(3) + "</"+GPoint.KEY_Lon+">  \n" +
                                "    </" + GPoint.TABLE + ">  \n");
            } while (gPointCursor.moveToNext());
        }
        if (indiCursor.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+Individual.TABLE+">  \n" +
                                "        <"+Individual.KEY_ID_Indi+">" + indiCursor.getString(0) + "</"+Individual.KEY_ID_Indi+">  \n" +
                                "        <"+Individual.KEY_IndiName+">" + indiCursor.getString(1) + "</"+Individual.KEY_IndiName+">  \n" +
                                "        <"+Individual.KEY_IsCons+">" + indiCursor.getString(2) + "</"+Individual.KEY_IsCons+">  \n" +
                                "    </" + Individual.TABLE + ">  \n");
            } while (indiCursor.moveToNext());
        }
        if (indiCoopCursor.moveToFirst()){
            do  {
                XMLall0000.append(
                        "   \n <"+Indi_Coop.TABLE+">  \n" +
                                "        <"+Indi_Coop.KEY_FirstPartID+">" + indiCoopCursor.getString(0) + "</"+Indi_Coop.KEY_FirstPartID+">  \n" +
                                "        <"+Indi_Coop.KEY_SecondPartID+">" + indiCoopCursor.getString(1) + "</"+Indi_Coop.KEY_SecondPartID+">  \n" +
                                "        <"+Indi_Coop.KEY_Title+">" + indiCoopCursor.getString(2) + "</"+Indi_Coop.KEY_Title+">  \n" +
                                "        <"+Individual.KEY_IndiName+">" + indiCoopCursor.getString(3) + "</"+Individual.KEY_IndiName+">  \n" +
                                "    </" + Indi_Coop.TABLE + ">  \n");
            } while (indiCoopCursor.moveToNext());
        }
        Ensaf.insert(7,XMLall0000);
        XMLensaf0.append(Ensaf);
        return XMLensaf0.toString();
    }
    public String exportQuery(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        StringBuilder XMLensaf0 = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n");
        StringBuilder Ensaf = new StringBuilder("<ensaf></ensaf>");
        StringBuilder XMLall0000 = new StringBuilder();
        String selectQueryCons_Phase = "SELECT *"
                + " FROM "
                + Cons_Phase.TABLE ;
        String selectQueryCusAccount = "SELECT *"
                + " FROM "
                + CusAccount.TABLE;
        String selectQueryGPoint = "SELECT *"
                + " FROM "
                + GPoint.TABLE;
        String selectQueryIndi_Coop = "SELECT *"
                + " FROM "
                + Indi_Coop.TABLE;
        String selectQueryIndi_Geop = "SELECT *"
                + " FROM "
                + Indi_Geop.TABLE;
        String selectQueryIndividual = "SELECT *"
                + " FROM "
                + Individual.TABLE;
        String selectQueryPhoneNum = "SELECT *"
                + " FROM "
                + PhoneNum.TABLE;
        String selectQueryTend = "SELECT *"
                + " FROM "
                + Tend.TABLE;
        Cursor QueryCons_Phase = db.rawQuery(selectQueryCons_Phase, null);
        Cursor QueryCusAccount = db.rawQuery(selectQueryCusAccount, null);
        Cursor QueryGPoint = db.rawQuery(selectQueryGPoint, null);
        Cursor QueryIndi_Coop = db.rawQuery(selectQueryIndi_Coop, null);
        Cursor QueryIndi_Geop = db.rawQuery(selectQueryIndi_Geop, null);
        Cursor QueryIndividual = db.rawQuery(selectQueryIndividual, null);
        Cursor QueryPhoneNum = db.rawQuery(selectQueryPhoneNum, null);
        Cursor QueryTend = db.rawQuery(selectQueryTend, null);

        if (QueryCons_Phase.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+Cons_Phase.TABLE+">  \n" +
                                "        <"+Cons_Phase.KEY_ID_Cons_Phase+">" + QueryCons_Phase.getString(0) + "<"+Cons_Phase.KEY_ID_Cons_Phase+">  \n" +
                                "        <"+Cons_Phase.KEY_IndID+">" + QueryCons_Phase.getString(1) + "</"+Cons_Phase.KEY_IndID+">  \n" +
                                "        <"+Cons_Phase.KEY_Phase+">" + QueryCons_Phase.getString(2) + "</"+Cons_Phase.KEY_Phase+"> \n" +
                                "        <"+Cons_Phase.KEY_PhaseDate+">" + QueryCons_Phase.getString(3) + "</"+Cons_Phase.KEY_PhaseDate+"> \n" +
                                "    </" + Cons_Phase.TABLE + ">  \n");
            }while (QueryCons_Phase.moveToNext());
        }

        if (QueryCusAccount.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+CusAccount.TABLE+">  \n" +
                                "        <"+CusAccount.KEY_ID_Cus+">" + QueryCusAccount.getString(0) + "<"+CusAccount.KEY_ID_Cus+">  \n" +
                                "        <"+CusAccount.KEY_IndID+">" + QueryCusAccount.getString(1) + "</"+CusAccount.KEY_IndID+">  \n" +
                                "        <"+CusAccount.KEY_AccountName+">" + QueryCusAccount.getString(2) + "</"+CusAccount.KEY_AccountName+"> \n" +
                                "        <"+CusAccount.KEY_PassWord+">" + QueryCusAccount.getString(3) + "</"+CusAccount.KEY_PassWord+"> \n" +
                                "        <"+CusAccount.KEY_IsAct+">" + QueryCusAccount.getString(4) + "</"+CusAccount.KEY_IsAct+"> \n" +
                                "    </" + CusAccount.TABLE + ">  \n");
            }while (QueryCusAccount.moveToNext());
        }

        if (QueryGPoint.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+GPoint.TABLE+">  \n" +
                                "        <"+GPoint.KEY_IDGeop+">" + QueryGPoint.getString(0) + "<"+GPoint.KEY_IDGeop+">  \n" +
                                "        <"+GPoint.KEY_Lat+">" + QueryGPoint.getString(1) + "<"+GPoint.KEY_Lat+">  \n" +
                                "        <"+GPoint.KEY_Lon+">" + QueryGPoint.getString(2) + "<"+GPoint.KEY_Lon+">  \n" +
                                "        <"+GPoint.KEY_IsSolo+">" + QueryGPoint.getString(3) + "<"+GPoint.KEY_IsSolo+">  \n" +
                                "    </" + GPoint.TABLE + ">  \n");
            } while (QueryGPoint.moveToNext());
        }

        if (QueryIndi_Coop.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+Indi_Coop.TABLE+">  \n" +
                                "        <"+Indi_Coop.KEY_ID_Indi_Co+">" + QueryIndi_Coop.getString(0) + "<"+Indi_Coop.KEY_ID_Indi_Co+">  \n" +
                                "        <"+Indi_Coop.KEY_FirstPartID+">" + QueryIndi_Coop.getString(1) + "<"+Indi_Coop.KEY_FirstPartID+">  \n" +
                                "        <"+Indi_Coop.KEY_SecondPartID+">" + QueryIndi_Coop.getString(2) + "<"+Indi_Coop.KEY_SecondPartID+">  \n" +
                                "        <"+Indi_Coop.KEY_Title+">" + QueryIndi_Coop.getString(3) + "<"+Indi_Coop.KEY_Title+">  \n" +
                                "    </" + Indi_Coop.TABLE + ">  \n");
            }while (QueryIndi_Coop.moveToNext());
        }

        if (QueryIndi_Geop.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+Indi_Geop.TABLE+">  \n" +
                                "        <"+Indi_Geop.KEY_ID_Indi_Geop+">" + QueryIndi_Geop.getString(0) + "<"+Indi_Geop.KEY_ID_Indi_Geop+">  \n" +
                                "        <"+Indi_Geop.KEY_IndiID+">" + QueryIndi_Geop.getString(1) + "<"+Indi_Geop.KEY_IndiID+">  \n" +
                                "        <"+Indi_Geop.KEY_GeopID+">" + QueryIndi_Geop.getString(2) + "<"+Indi_Geop.KEY_GeopID+">  \n" +
                                "    </" + Indi_Geop.TABLE + ">  \n");
            } while (QueryIndi_Geop.moveToNext());
        }

        if (QueryIndividual.moveToFirst()) {
            do  {
                XMLall0000.append(
                        "   \n <"+Individual.TABLE+">  \n" +
                                "        <"+Individual.KEY_ID_Indi+">" + QueryIndividual.getString(0) + "<"+Individual.KEY_ID_Indi+">  \n" +
                                "        <"+Individual.KEY_IndiName+">" + QueryIndividual.getString(1) + "<"+Individual.KEY_IndiName+">  \n" +
                                "        <"+Individual.KEY_IsCons+">" + QueryIndividual.getString(2) + "<"+Individual.KEY_IsCons+">  \n" +
                                "    </" + Individual.TABLE + ">  \n");
            } while (QueryIndividual.moveToNext());
        }

        if (QueryPhoneNum.moveToFirst()) {
             do{
                XMLall0000.append(
                        "   \n <"+PhoneNum.TABLE +">  \n" +
                                "        <"+PhoneNum.KEY_ID_Phone+">" + QueryPhoneNum.getString(0) + "<"+PhoneNum.KEY_ID_Phone+">  \n" +
                                "        <"+PhoneNum.KEY_IndiID+">" + QueryPhoneNum.getString(1) + "<"+PhoneNum.KEY_IndiID+">  \n" +
                                "        <"+PhoneNum.KEY_Num+">" + QueryPhoneNum.getString(2) + "<"+PhoneNum.KEY_Num+">  \n" +
                                "    </" + PhoneNum.TABLE + ">  \n");
            }while (QueryPhoneNum.moveToNext());
        }

        if (QueryTend.moveToFirst()) {
             do {
                XMLall0000.append(
                        "   \n <"+Tend.TABLE+">  \n" +
                                "        <"+Tend.KEY_ID_Tend+">" + QueryTend.getString(0) + "<"+Tend.KEY_ID_Tend+">  \n" +
                                "        <"+Tend.KEY_Ind1ID+">" + QueryTend.getString(1) + "<"+Tend.KEY_Ind1ID+">  \n" +
                                "        <"+Tend.KEY_Ind2ID+">" + QueryTend.getString(2) + "<"+Tend.KEY_Ind2ID+">  \n" +
                                "        <"+Tend.KEY_Title+">" + QueryTend.getString(3) + "<"+Tend.KEY_Title+">  \n" +
                                "        <"+Tend.KEY_Detail+">" + QueryTend.getString(4) + "<"+Tend.KEY_Detail+">  \n" +
                                "        <"+Tend.KEY_TendDate+">" + QueryTend.getString(5) + "<"+Tend.KEY_TendDate+">  \n" +
                                "        <"+Tend.KEY_IsChecked+">" + QueryTend.getString(6) + "<"+Tend.KEY_IsChecked+">  \n" +
                                "    </" + Tend.TABLE + ">  \n");
            }while (QueryTend.moveToNext());
        }

        Ensaf.insert(7,XMLall0000);
        XMLensaf0.append(Ensaf);
        return XMLensaf0.toString();
    }
    public String dailyReport(){
        Date c = Calendar.getInstance().getTime();
        HomePageQuery homePageQuery = new HomePageQuery();
        Cursor homePageTaskCusor =  homePageQuery.getTask(ID_CONNECT_Indi1,getDate01Daily(c),getDate02Daily(c));

        StringBuilder report = new StringBuilder();

        if (homePageTaskCusor.getCount() >= 0 ){
            if (homePageTaskCusor.moveToFirst()){
                int i = 1;
                do{
                    String g_date = homePageTaskCusor.getString(4);
                    String [] arrOfFomattedDate = g_date.split(" ",2);
                    String [] arrOfGreDate = arrOfFomattedDate[0].split("-",3);
                    report.append(" \n " + i++ + ":");
                    report.append("   \n " + homePageTaskCusor.getString(1) +
                            " \n " + homePageTaskCusor.getString(2) +
                            " \n " + homePageTaskCusor.getString(3) +
                            " \n " + getPersianDate(Integer.valueOf(arrOfGreDate[0]), Integer.valueOf(arrOfGreDate[1]), Integer.valueOf(arrOfGreDate[2])));
                    report.append(" \n ");
                }while (homePageTaskCusor.moveToNext());
            }
        } else {
            report = new StringBuilder("error!");
        }
    return report.toString();
    }
    private String getDate01Daily(Date date){//TODO unify all Time Classes and Method
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date) + " 00:00";
    }
    private String getDate02Daily(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date) + " 23:59";
    }
}
