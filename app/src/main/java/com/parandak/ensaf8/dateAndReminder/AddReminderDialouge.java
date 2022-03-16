package com.parandak.ensaf8.dateAndReminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.dataBase.DBQuery;
import com.parandak.ensaf8.dataBase.DataContract;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.TendRepo;
import com.parandak.ensaf8.homePage.HomePageQuery;
import com.parandak.ensaf8.homePage.adapter.TaskHomePageAdapter;
import com.parandak.ensaf8.homePage.model.TaskHomePage;
import com.parandak.ensaf8.fullScreenDialog.FullDialogQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianDate;
import static com.parandak.ensaf8.homePage.HomePageActivity.ID_CONNECT_Indi1;

import org.osmdroid.util.GeoPoint;

public class AddReminderDialouge implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Context context;
    Activity activity;
    Button btnAddDateToFollow;
    String finalResult,finalResult_fa;
    boolean isTimeEdited = false;
    EditText editText_coaperate, ediTxtTitle , ediTxtMain;
    TextView txtTitleDialogue;
    FullDialogQuery fullDialogQuery = new FullDialogQuery();
    Tend tend = new Tend();
    TendRepo tendRepo = new TendRepo();
    public AddReminderDialouge(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    @SuppressLint("SetTextI18n")
    public void showDialogueADD(final String ID_Indi2){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogueView = layoutInflater.inflate(R.layout.follow_dialogue_add,null);
        btnAddDateToFollow = dialogueView.findViewById(R.id.buttonAddDateToFollow);
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(DataContract.dateFormat);
        btnAddDateToFollow.setText(getPersianDate(c)+" "+ sdf.format(c));
        finalResult = df.format(c);
        ediTxtTitle = dialogueView.findViewById(R.id.ediTxtTitle);
        ediTxtMain = dialogueView.findViewById(R.id.ediTxtMain);
        txtTitleDialogue = dialogueView.findViewById(R.id.txtNameIndi2);
        txtTitleDialogue.setText(fullDialogQuery.getIndiName(ID_Indi2));

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tend.setInd1ID(ID_CONNECT_Indi1);
                                tend.setInd2ID(ID_Indi2);
                                tend.setTitle(ediTxtTitle.getText().toString());
                                tend.setDetail(ediTxtMain.getText().toString());
                                tend.setTendDate(finalResult);
                                tend.setIsChecked("0");
                                if (tendRepo.insert(tend)>0)
                                    Toast.makeText(context,"Tend Added"  , Toast.LENGTH_SHORT).show();

                            }
                        }).create();
        alertDialog.setView(dialogueView);
        alertDialog.show();

        btnAddDateToFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        AddReminderDialouge.this,
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );
                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(activity.getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void showDialogueADD(final String ID_Indi2, GeoPoint geoPoint){////runForAttendance
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DBQuery dbQuery = new DBQuery();
        View dialogueView = layoutInflater.inflate(R.layout.follow_dialogue_add,null);
        btnAddDateToFollow = dialogueView.findViewById(R.id.buttonAddDateToFollow);
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(DataContract.dateFormat);
        btnAddDateToFollow.setText(getPersianDate(c)+" "+ sdf.format(c));
        finalResult = df.format(c);
        ediTxtTitle = dialogueView.findViewById(R.id.ediTxtTitle);
        ediTxtTitle.setText(dbQuery.getConsCoordination(ID_Indi2));
        ediTxtMain = dialogueView.findViewById(R.id.ediTxtMain);
        ediTxtMain.setText(geoPoint.toString());
        txtTitleDialogue = dialogueView.findViewById(R.id.txtNameIndi2);
        txtTitleDialogue.setText(fullDialogQuery.getIndiName(ID_Indi2));

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tend.setInd1ID(ID_CONNECT_Indi1);
                                tend.setInd2ID(ID_Indi2);
                                tend.setTitle(ediTxtTitle.getText().toString());
                                tend.setDetail(ediTxtMain.getText().toString());
                                tend.setTendDate(finalResult);
                                tend.setIsChecked("0");
                                if (tendRepo.insert(tend)>0)
                                    Toast.makeText(context,"Tend Added"  , Toast.LENGTH_SHORT).show();

                            }
                        }).create();
        alertDialog.setView(dialogueView);
        alertDialog.show();

        btnAddDateToFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        AddReminderDialouge.this,
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );
                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(activity.getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public boolean showDialogueEdit(final String tendID,String customerName){
        final boolean[] isChange = {false};
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogueView = layoutInflater.inflate(R.layout.follow_dialogue_add,null);
        btnAddDateToFollow = dialogueView.findViewById(R.id.buttonAddDateToFollow);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        btnAddDateToFollow.setText(getPersianDate(c)+" "+ sdf.format(c));
        String formattedDate = df.format(c);
        finalResult = formattedDate;
        ediTxtTitle = dialogueView.findViewById(R.id.ediTxtTitle);
        ediTxtMain = dialogueView.findViewById(R.id.ediTxtMain);
        txtTitleDialogue = dialogueView.findViewById(R.id.txtNameIndi2);
        txtTitleDialogue.setText(customerName);
        /////////fill editText
        HomePageQuery homePageQuery = new HomePageQuery();
        final Tend tend = new Tend();
        final TendRepo tendRepo = new TendRepo();
        final Cursor cursor = homePageQuery.getTasksID(tendID);
        tend.setID_Tend(tendID);
        if (cursor.moveToFirst()) {
            ediTxtTitle.setText(cursor.getString(2));
            ediTxtMain.setText(cursor.getString(3));
            ////*******////*****////
            String g_date = cursor.getString(4);
            String [] arrOfFomattedDate = g_date.split(" ",2);
            String [] arrOfGreDate = arrOfFomattedDate[0].split("-",3);
            String persianDate = getPersianDate(Integer.valueOf(arrOfGreDate[0]), Integer.valueOf(arrOfGreDate[1]), Integer.valueOf(arrOfGreDate[2]))+ " " + arrOfFomattedDate[1] ;
            btnAddDateToFollow.setText(persianDate);

            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setPositiveButton("EDIT",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tend.setInd1ID(cursor.getString(0));
                                    tend.setInd2ID(cursor.getString(1));
                                    tend.setTitle(ediTxtTitle.getText().toString());///2
                                    tend.setDetail(ediTxtMain.getText().toString());///3
                                    if(isTimeEdited){
                                        tend.setTendDate(finalResult);
                                    }else{
                                        tend.setTendDate(cursor.getString(4));
                                    }
                                    tend.setIsChecked(cursor.getString(5));

                                    if (tendRepo.update(tend)) {
                                        Toast.makeText(context, "Tend Edited !", Toast.LENGTH_SHORT).show();
                                        //taskHomePageAdapter.notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(context, "Error !" + "not change", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton("DELETE",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
                                    builderInner.setMessage("DELETE" );
                                    builderInner.setTitle("Are you Sure?");
                                    builderInner.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                    if (tendRepo.deleteID_Tend(tend.getID_Tend())) {
                                        Toast.makeText(context, "Tend Deleted !", Toast.LENGTH_SHORT).show();
                                        ///taskHomePageAdapter.notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(context, "Error !" + "not change", Toast.LENGTH_SHORT).show();
                                    }


                                        }
                                    });
                                    builderInner.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    builderInner.show();
                                }
                            })
                    .create();
            alertDialog.setView(dialogueView);
            alertDialog.show();

            btnAddDateToFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PersianCalendar persianCalendar = new PersianCalendar();
                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                            AddReminderDialouge.this,
                            persianCalendar.getPersianYear(),
                            persianCalendar.getPersianMonth(),
                            persianCalendar.getPersianDay()
                    );
                    datePickerDialog.setThemeDark(true);
                    datePickerDialog.show(activity.getFragmentManager(), "Datepickerdialog");
                }
            });

        } else {
            Toast.makeText(context, "Error ! No serult for query !", Toast.LENGTH_SHORT).show();
        }

       //////////

    return isChange[0];
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int correct = monthOfYear+1;
        final Calendar[] gregorian = new Calendar[1];
        final PersianCalendarAli persianCalendarAli = new PersianCalendarAli();
        gregorian[0] = persianCalendarAli.getGregorianCalendar(year,correct,dayOfMonth);
        String month = String.valueOf(gregorian[0].get(Calendar.MONTH)+1);
        if ((gregorian[0].get(Calendar.MONTH)+1)<10)
            month = "0"+(gregorian[0].get(Calendar.MONTH)+1);

        String day = String.valueOf(gregorian[0].get(Calendar.DAY_OF_MONTH));
        if ((gregorian[0].get(Calendar.DAY_OF_MONTH))<10)
            day = "0"+(gregorian[0].get(Calendar.DAY_OF_MONTH));
        finalResult = gregorian[0].get(Calendar.YEAR)+"-"+month+"-"+day;
        finalResult_fa =  year + "/" + correct + "/" + dayOfMonth;

        PersianCalendar persianCalendar = new PersianCalendar();
        TimePickerDialog timePickerDialog = (TimePickerDialog) TimePickerDialog.newInstance(
                this,
                persianCalendar.getTime().getHours(),
                persianCalendar.getTime().getMinutes(),
                true
        );
        timePickerDialog.setThemeDark(true);
        timePickerDialog.show(activity.getFragmentManager(),"Timepickerdialog");
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {


            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourDay = String.valueOf(hourOfDay);
        if (hourOfDay<10)
            hourDay = "0"+ hourOfDay;

        String minuteDay = String.valueOf(minute);
        if (minute<10)
            minuteDay = "0"+minute;

        finalResult_fa = finalResult_fa +" "+ hourDay +":"+ minuteDay;
        finalResult = finalResult +" "+ hourDay +":"+ minuteDay;
        btnAddDateToFollow.setText(finalResult_fa);
        isTimeEdited = true ;
    }
}
