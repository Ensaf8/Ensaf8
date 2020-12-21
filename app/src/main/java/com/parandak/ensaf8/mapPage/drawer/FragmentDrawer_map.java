package com.parandak.ensaf8.mapPage.drawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMarkType;
import com.parandak.ensaf8.dateAndReminder.AddReminderDialouge;
import com.parandak.ensaf8.dateAndReminder.PersianCalendarAli;
import com.parandak.ensaf8.homePage.adapter.NavigationDrawerAdapter;
import com.parandak.ensaf8.mapPage.MapPageQuery;
import com.parandak.ensaf8.mapPage.model.ConsState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentDrawer_map extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static String TAG = FragmentDrawer_map.class.getSimpleName();
    TextView txtFilterSeek,txtFilterDate;//TODO solve all warnings
    String titleFilterSeek;
    private Context mcontext;
    private Activity mactivity;
    public int SeekProgress01 = 2;
    public int SeekProgress02 = 7;
    RelativeLayout map_drawer_container;
    SeekBar seekBar01;
    SeekBar seekBar02;
    CheckBox checkBox01,checkBox02,checkBox03,checkBox04,checkBoxBook;
    EditText ediFilterTend;
    Button btnDateFilter01,btnDateFilter02;
    String DateFilter01,DateFilter02;
    boolean btnDateFilter01_Isclick;
    RatingBar ratingBarFilter;
    private boolean isCheck01  = false;
    private boolean isCheck02  = false;
    private boolean isCheck03  = false;
    private boolean isCheck04  = false;
    private boolean isCheckBook  = false;
    String fa_date01 = "1399-07-01",fa_date02 = "1399-08-27" ;
    int [] seekProgress = new int[]{SeekProgress01,SeekProgress02};
    List<ConsState> consStateList = new ArrayList<>();
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    //private View containerView;
    String finalResult,finalResult_fa;

    public ImageView imgFilter;

    public FragmentDrawer_map() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {///TODO simplify and organize all code
        super.onCreate(savedInstanceState);

        // drawer labels
        ///titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    public void setProgressOn01(int p){
        seekBar01.setProgress(p);
    }

    public void setProgressOn02(int p){
        seekBar02.setProgress(p);
    }


    public int getSeekProgress01() {
        return SeekProgress01;
    }

    public int getSeekProgress02() {
        return SeekProgress02;
    }

    public void setDateFilter01 (String date01){
        DateFilter01 = date01;
    }

    public void setDateFilter02 (String date02){
        DateFilter02 = date02;
    }

    public void setRating04(float r){
        ratingBarFilter.setRating(r);
    }

    public Float getRating(){
        return ratingBarFilter.getRating();
    }

    public String getDateFilter01(){
        return  DateFilter01;
    }

    public String getDateFilter02(){
        return DateFilter02;
    }

    public void setCheckBox01(Boolean isCheck01){
        this.isCheck01 = isCheck01;
        checkBox01.setChecked(isCheck01);
    }

    public void setCheckBox02(Boolean isCheck02){
        this.isCheck02 = isCheck02;
        checkBox02.setChecked(isCheck02);
    }

    public void setCheckBox03(Boolean isCheck03){
        this.isCheck03 = isCheck03;
        checkBox03.setChecked(isCheck03);
    }

    public void setCheckBox04(Boolean isCheck04){
        this.isCheck04 = isCheck04;
        checkBox04.setChecked(isCheck04);
    }

    public void setCheckBoxBook(Boolean isCheckBook){
        this.isCheckBook = isCheckBook;
        checkBoxBook.setChecked(isCheckBook);
    }

    public boolean isCheck01() {
        return isCheck01;
    }

    public boolean isCheck02() {
        return isCheck02;
    }

    public boolean isCheck03() {
        return isCheck03;
    }

    public boolean isCheck04() {
        return isCheck04;
    }

    public boolean isCheckBook() {
        return isCheckBook;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View view = inflater.inflate(R.layout.fragment_drawer_map, container, false);//TODO redesign view
        imgFilter = view.findViewById(R.id.imgFilter);
        txtFilterSeek = view.findViewById(R.id.txtFilterSeek);
        txtFilterDate = view.findViewById(R.id.txtFilterDate);
        seekBar01 = view.findViewById(R.id.seekBar01);
        seekBar02 = view.findViewById(R.id.seekBar02);
        checkBox01 = view.findViewById(R.id.checkBoxFilter01);
        checkBox01.setChecked(isCheck01);
        checkBox02 = view.findViewById(R.id.checkBoxFilter02);
        checkBox02.setChecked(isCheck02);
        checkBox03 = view.findViewById(R.id.checkBoxFilter03);
        checkBox03.setChecked(isCheck03);
        checkBox04 = view.findViewById(R.id.checkBoxFilter04);
        checkBox04.setChecked(isCheck04);
        initSpinner(view);
        map_drawer_container = view.findViewById(R.id.map_drawer_container);
        map_drawer_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"container is clicked : " , Toast.LENGTH_LONG).show();
                ratingBarFilter.setRating(0);
                checkBox04.setChecked(false);
            }
        });
        ratingBarFilter = view.findViewById(R.id.ratingFilter);
        ratingBarFilter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                checkBox04.setChecked(true);
            }
        });
        checkBoxBook = view.findViewById(R.id.checkBoxBookmark);
        checkBoxBook.setChecked(isCheckBook);
        ediFilterTend = view.findViewById(R.id.ediTendFilter);
        btnDateFilter01 = view.findViewById(R.id.btnDateFilter01);
        btnDateFilter02 = view.findViewById(R.id.btnDateFilter02);
        initConsStateList();
        btnDateFilter01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        FragmentDrawer_map.this,
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );
                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(mactivity.getFragmentManager(), "Datepickerdialog");
                btnDateFilter01_Isclick =true;
                checkBox03.setChecked(true);
            }
        });
        btnDateFilter02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        FragmentDrawer_map.this,
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );
                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(mactivity.getFragmentManager(), "Datepickerdialog");
                btnDateFilter01_Isclick =false;
                checkBox03.setChecked(true);
            }
        });
        seekBar01.setProgress(SeekProgress01);
        seekBar01.setThumb(mcontext.getResources().getDrawable(consStateList.get(SeekProgress01).getDrawable()));
        titleFilterSeek = consStateList.get(SeekProgress01).getState()+" تا " +consStateList.get(SeekProgress02).getState(); ;
        txtFilterSeek.setText(titleFilterSeek);
        seekBar01.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                SeekProgress01 = progress;
                seekBar.setThumb(mcontext.getResources().getDrawable(consStateList.get(SeekProgress01).getDrawable()));
                titleFilterSeek = consStateList.get(SeekProgress01).getState()+" تا " +consStateList.get(SeekProgress02).getState();
                txtFilterSeek.setText(titleFilterSeek);
                checkBox01.setChecked(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar02.setProgress(SeekProgress02);
        seekBar02.setThumb(mcontext.getResources().getDrawable(consStateList.get(SeekProgress02).getDrawable()));
        seekBar02.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                SeekProgress02 = progress;
                seekBar.setThumb(mcontext.getResources().getDrawable(consStateList.get(SeekProgress02).getDrawable()));
                titleFilterSeek = consStateList.get(SeekProgress01).getState()+" تا " +consStateList.get(SeekProgress02).getState();
                txtFilterSeek.setText(titleFilterSeek);
                checkBox01.setChecked(true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck01 = isChecked;
            }
        });

        checkBox02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck02 = isChecked;
                checkBoxBook.setChecked(false);
                if (isChecked)
                    checkBox02.setChecked(true);
                Toast.makeText(mcontext,"checkBox02 isChecked : " +  isChecked, Toast.LENGTH_LONG).show();
            }
        });

        checkBox03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck03 = isChecked;
            }
        });
        checkBox04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && ratingBarFilter.getRating()== 0f){
                    checkBox04.setChecked(false);
                    isCheck04 = false;
                }else {
                    isCheck04 =isChecked;
                }

                Toast.makeText(mcontext,"stars isChecked : " +  isChecked, Toast.LENGTH_LONG).show();
            }
        });
        checkBoxBook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckBook = isChecked;
                checkBox02.setChecked(false);
                if (isChecked)
                    checkBoxBook.setChecked(true);
                Toast.makeText(mcontext,"BOOK MARK isChecked : " +  isChecked, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mactivity = activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mcontext = null;
        mactivity = null;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout ) {
        //containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    private void initConsStateList(){
        ConsState consState;
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue1);
        consState.setState("گود برداری");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue2);
        consState.setState("اجرای اسکلت");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue3);
        consState.setState("اتمام اسکلت");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mgreen4);
        consState.setState("شروع دیوارچینی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mgreen5);
        consState.setState("دیوارچینی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_myellow6);
        consState.setState("اتمام دیوارچینی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_myellow7);
        consState.setState("شروع تاسیسات");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_morange8);
        consState.setState("اجرای تاسیسات");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_morange9);
        consState.setState("اتمام تاسیسات");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mred10);
        consState.setState("دکوراسیون داخلی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mred11);
        consState.setState("اتمام کار");
        consStateList.add(consState);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int correct = monthOfYear+1;
        final Calendar[] gregorian = new Calendar[1];
        final PersianCalendarAli persianCalendarAli = new PersianCalendarAli();
        gregorian[0] = persianCalendarAli.getGregorianCalendar(year,correct,dayOfMonth);
        int gregorianMonth = gregorian[0].get(Calendar.MONTH)+1;
        String month = String.valueOf(gregorianMonth);
        if (gregorianMonth<10){
            month = "0" + month;
        }
        //if ((gregorian[0].get(Calendar.MONTH)+1)<10)
        //    month = "0"+(gregorian[0].get(Calendar.MONTH)+1);

        String day = String.valueOf(gregorian[0].get(Calendar.DAY_OF_MONTH));
        if ((gregorian[0].get(Calendar.DAY_OF_MONTH))<10){
            day = "0" + day;
        }
        finalResult = gregorian[0].get(Calendar.YEAR)+"-"+month+"-"+day;
        finalResult_fa =  year + "/" + correct + "/" + dayOfMonth;
        Log.d("ensaf::::::::", TAG + "> onDateSet > day : " + day + " month : " + month + " year : " + gregorian[0].get(Calendar.YEAR));
        if(btnDateFilter01_Isclick){
            btnDateFilter01.setText(finalResult_fa);
            DateFilter01 = finalResult;
            fa_date01 = finalResult_fa;
            txtFilterDate.setText(fa_date01 + " تا " + fa_date02);
        }else {
            fa_date02 = finalResult_fa;
            txtFilterDate.setText(fa_date01 + " تا " + fa_date02);
            btnDateFilter02.setText(finalResult_fa);
            DateFilter02 = finalResult;
        }

    }

    private void initSpinner(View view){
        MultiSelectionSpinner spinner = (MultiSelectionSpinner) view.findViewById(R.id.spinner);
        spinner.setItems(new MapPageQuery().getBookMarkTypeList());
        spinner.selectFirstItem();//Important
        //spinner.getSelectedItemsAsStringID();
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                checkBoxBook.setChecked(true);
                return false;
            }
        });
    }

}
