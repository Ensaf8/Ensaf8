package com.parandak.ensaf8.homePage;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.TendRepo;
import com.parandak.ensaf8.dateAndReminder.AddReminderDialouge;
import com.parandak.ensaf8.fullScreenDialog.FullDialog;
import com.parandak.ensaf8.homePage.adapter.TaskHomePageAdapter;
import com.parandak.ensaf8.homePage.model.TaskHomePage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getDayOfMonthJalali;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getDayOfWeek;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getMonthOfYearJalali;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianDate;
import static com.parandak.ensaf8.homePage.HomePageActivity.ID_CONNECT_Indi1;
import static com.parandak.ensaf8.homePage.HomePageActivity.isConnected;

public class TasksFragment extends Fragment {
    private List<TaskHomePage> taskHomePageList= new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskHomePageAdapter taskHomePageAdapter;
    private String date01 = "2020-01-01 00:00",date02 = "2020-12-29 23:59";
    private Context mcontext;
    private Activity mactivity = getActivity();
    private Date c;
    TextView txt_tasks;
    private FloatingActionButton fab;
    public TasksFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (taskHomePageAdapter!=null){
            preparingDataReminder(date01,date02);
            taskHomePageAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (isConnected){
            c = Calendar.getInstance().getTime();
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
            String todaydate = df1.format(c);
            String todayhoure = df2.format(c);
            String [] arrOfGreDate = todaydate.split("-",3);
            View view = inflater.inflate(R.layout.fragment_tasks, container, false);
            floatinBAdd(view);
            taskRecyclerView(view);
            txt_tasks = (TextView)view.findViewById(R.id.txt_tasks);
            date01 = getDate01Daily(c);
            date02 = getDate02Daily(c);
            txt_tasks.setText("امروز");
            preparingDataReminder(date01,date02);
            final ImageButton buttonTasks = (ImageButton) view.findViewById(R.id.buttonTasks);
            buttonTasks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(buttonTasks,"Ali");
                }
            });
            return view;
        } else {
            return inflater.inflate(R.layout.fragment_tasks02, container, false);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mactivity=  activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mcontext = null;
        mactivity = null;
    }
    private String getDate01Weekly(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        if (c.get(Calendar.DAY_OF_WEEK)==7){
            cal.add(Calendar.DATE,0);
        }else {
            cal.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime())+ " 00:00";
    }
    private String getDate01NextWeek(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        if (c.get(Calendar.DAY_OF_WEEK)==7){
            cal.add(Calendar.DATE,7);
        }else {
            cal.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK)+7);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime())+ " 00:00";
    }
    private String getDate01Daily(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date) + " 00:00";
    }
    private String getDate01Tomorrow(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE,+1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime()) + " 00:00";
    }
    private String getDate01Monthly(Date date){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -(getDayOfMonthJalali(c)-1));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime())+ " 00:00";
    }
    private String getDate02Weekly(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        if (c.get(Calendar.DAY_OF_WEEK)==7){
            cal.add(Calendar.DATE,6);
        }else {
            cal.add(Calendar.DATE, +(6-c.get(Calendar.DAY_OF_WEEK)));
        }
        //
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime())+ " 23:59";
    }
    private String getDate02NextWeek(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        if (c.get(Calendar.DAY_OF_WEEK)==7){
            cal.add(Calendar.DATE,13);
        }else {
            cal.add(Calendar.DATE, +(13-c.get(Calendar.DAY_OF_WEEK)));
        }
        //
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime())+ " 23:59";
    }
    private String getDate02Daily(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date) + " 23:59";
    }
    private String getDate02Tomorrow(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE,+1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime()) + " 23:59";
    }
    private String getDate02Monthly(Date date){
        int daysOfMonth = 29;
        if(getMonthOfYearJalali(c)<7){
            daysOfMonth = 30;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, +(daysOfMonth-(getDayOfMonthJalali(c)-1)));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime())+ " 23:59";
    }
    private void taskRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_task);
        recyclerView.setHasFixedSize(true);
        taskHomePageAdapter = new TaskHomePageAdapter(mcontext,taskHomePageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(taskHomePageAdapter);
        taskHomePageAdapter.setOnItemClickListener(new TaskHomePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TaskHomePage taskHomePage = taskHomePageList.get(position);
                AddReminderDialouge addReminderDialouge = new AddReminderDialouge(mcontext,mactivity);
                if (addReminderDialouge.showDialogueEdit(taskHomePage.getTendID(),taskHomePage.getCustomerName())){
                    taskHomePageAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "notifyDataSetChange", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "notifyDataSetChange NOT CHANGE !!!!!", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getActivity(), taskHomePage.getIndiName() + " باید "+taskHomePage.getTitle(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCheckChange(int position) {
                HomePageQuery homePageQuery = new HomePageQuery();
                Tend tend = new Tend();
                TendRepo tendRepo = new TendRepo();
                Cursor cursor = homePageQuery.getTasksID(taskHomePageList.get(position).getTendID());
                tend.setID_Tend(taskHomePageList.get(position).getTendID());
                if (cursor.moveToFirst()) {
                    tend.setInd1ID(cursor.getString(0));
                    tend.setInd2ID(cursor.getString(1));
                    tend.setTitle(cursor.getString(2));
                    tend.setDetail(cursor.getString(3));
                    tend.setTendDate(cursor.getString(4));
                    String s;
                    if (taskHomePageList.get(position).isChecked()) {
                       // s = " is unchecked !";
                        tend.setIsChecked("0");
                        //taskHomePageList.get(position).setChecked(false);
                    } else {
                        //s = " is checked !";
                        tend.setIsChecked("1");
                        //taskHomePageList.get(position).setChecked(true);
                    }
                    if (tendRepo.update(tend)) {
                        //Toast.makeText(getActivity(), taskHomePageList.get(position).getTendID() + s, Toast.LENGTH_SHORT).show();
                    }else {
                        //Toast.makeText(getActivity(), taskHomePageList.get(position).getTendID() + "not change", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNameCick(int position) {
                HomePageQuery homePageQuery = new HomePageQuery();
                int isCons = homePageQuery.isCons(taskHomePageList.get(position).getIndi2ID());
                if (isCons == 0){
                    FullDialog.display(getFragmentManager(),taskHomePageList.get(position).getIndi2ID());
                }else if(isCons == 1){
                    Toast.makeText(getActivity(), taskHomePageList.get(position).getIndi2ID() + "is Construction"  , Toast.LENGTH_SHORT).show();
                }else if(isCons == -1){
                    Toast.makeText(getActivity(), "errorIsCons"  , Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    private void preparingDataReminder(String date01, String date02){
        taskHomePageList.clear();
        HomePageQuery homePageQuery = new HomePageQuery();
        Cursor homePageTaskCusor =  homePageQuery.getTask(ID_CONNECT_Indi1,date01,date02);
        if (homePageTaskCusor.getCount() >= 0 ){
        if (homePageTaskCusor.moveToFirst()) {
            do{
                String g_date = homePageTaskCusor.getString(4);
                String [] arrOfFomattedDate = g_date.split(" ",2);
                String [] arrOfGreDate = arrOfFomattedDate[0].split("-",3);
                TaskHomePage taskHomePage = new TaskHomePage(homePageTaskCusor.getString(0),homePageTaskCusor.getString(1),homePageTaskCusor.getString(2),
                        getPersianDate(Integer.valueOf(arrOfGreDate[0]), Integer.valueOf(arrOfGreDate[1]), Integer.valueOf(arrOfGreDate[2]))+ " " + arrOfFomattedDate[1],homePageTaskCusor.getString(3),homePageTaskCusor.getString(5).equals("1"),homePageTaskCusor.getString(6));
                taskHomePageList.add(taskHomePage);
            }while (homePageTaskCusor.moveToNext());
        }
        } else {
            TaskHomePage taskHomePage = new TaskHomePage( "error","error","error error","error     error","error",true,"error");
            taskHomePageList.add(taskHomePage);
        }
    }
    private void preparingDataReminder(){
        taskHomePageList.clear();
        HomePageQuery homePageQuery = new HomePageQuery();
        Cursor homePageTask =  homePageQuery.getTask(ID_CONNECT_Indi1);
        if (homePageTask.getCount() > 0 ){
            if (homePageTask.moveToFirst()) {
                do{
                    String g_date = homePageTask.getString(4);
                    String [] arrOfFomattedDate = g_date.split(" ",2);
                    String [] arrOfGreDate = arrOfFomattedDate[0].split("-",3);
                    TaskHomePage taskHomePage = new TaskHomePage(homePageTask.getString(0),homePageTask.getString(1),homePageTask.getString(2),getPersianDate(Integer.valueOf(arrOfGreDate[0]), Integer.valueOf(arrOfGreDate[1]), Integer.valueOf(arrOfGreDate[2]))+ " " + arrOfFomattedDate[1],homePageTask.getString(3),homePageTask.getString(5).equals("1"),homePageTask.getString(6));
                    taskHomePageList.add(taskHomePage);
                }while (homePageTask.moveToNext());
            }
        } else {
            TaskHomePage taskHomePage = new TaskHomePage( "error","error","error error","error     error","error",true,"error");
            taskHomePageList.add(taskHomePage);
        }
    }
    private void showPopupMenu(View view,String name) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_tasks, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(name));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        String name;
        public MyMenuItemClickListener(String name) {
            this.name = name;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_task_Today:
                    date01 = getDate01Daily(c);
                    date02 = getDate02Daily(c);
                    //txt_tasks.setText(date02+" : "+date01);
                    txt_tasks.setText("امروز");
                    preparingDataReminder(date01,date02);
                    taskHomePageAdapter.notifyDataSetChanged();
                    return true;

                case R.id.menu_task_Tomorrow:
                    date01 = getDate01Tomorrow(c);
                    date02 = getDate02Tomorrow(c);
                    //txt_tasks.setText(date02+" : "+date01);
                    txt_tasks.setText("فردا");
                    preparingDataReminder(date01,date02);
                    taskHomePageAdapter.notifyDataSetChanged();
                    return true;

                case R.id.menu_task_ThisWeek:
                    date01 = getDate01Weekly(c);
                    date02 = getDate02Weekly(c);
                    //txt_tasks.setText(date02+" : "+date01);
                    txt_tasks.setText("این هفته");
                    preparingDataReminder(date01,date02);
                    taskHomePageAdapter.notifyDataSetChanged();
                    return true;
                case R.id.menu_task_NextWeek:
                    date01 = getDate01NextWeek(c);
                    date02 = getDate02NextWeek(c);
                    //txt_tasks.setText(date01);
                    txt_tasks.setText("هفته آینده");
                    preparingDataReminder(date01,date02);
                    taskHomePageAdapter.notifyDataSetChanged();
                    return true;
                case R.id.menu_task_ThisMonth:
                    date01 = getDate01Monthly(c);
                    date02 = getDate02Monthly(c);
                    preparingDataReminder(date01,date02);
                    taskHomePageAdapter.notifyDataSetChanged();
                    txt_tasks.setText("این ماه");

                    return true;
                case R.id.menu_task_All:
                    preparingDataReminder();
                    taskHomePageAdapter.notifyDataSetChanged();
                    txt_tasks.setText("همه");
                    return true;
                default:
            }
            return false;
        }
    }
    private void floatinBAdd(View view){
        fab = (FloatingActionButton)view.findViewById(R.id.fabAddFollow);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent010 = new Intent(getActivity(), SearchPageActivity.class);
                intent010.putExtra("ID","follow");
                mcontext.startActivity(intent010);*/
                Toast.makeText(v.getContext() ,"New Follow !" ,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        if (taskHomePageAdapter!=null){
            preparingDataReminder(date01,date02);
            taskHomePageAdapter.notifyDataSetChanged();
        }


    }


}
