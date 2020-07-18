package com.parandak.ensaf8.mapPage.drawer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.homePage.adapter.NavigationDrawerAdapter;
import com.parandak.ensaf8.mapPage.model.ConsState;

import java.util.ArrayList;
import java.util.List;

public class FragmentDrawer_map extends Fragment {
    private static String TAG = FragmentDrawer_map.class.getSimpleName();
    TextView txtFilterSeek;
    String titleFilterSeek;
    private Context mcontext;
    public int SeekProgress01 = 2;
    public int SeekProgress02 = 7;
    SeekBar seekBar01;
    SeekBar seekBar02;
    CheckBox checkBox01;
    public boolean isCheck01  = false;
    int [] seekProgress = new int[]{SeekProgress01,SeekProgress02};
    List<ConsState> consStateList = new ArrayList<>();
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    //private View containerView;

    public ImageView imgFilter;

    public FragmentDrawer_map() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

    public void setCheckBox01(Boolean isCheck01){
        this.isCheck01 = isCheck01;
        checkBox01.setChecked(isCheck01);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View view = inflater.inflate(R.layout.fragment_drawer_map, container, false);
        imgFilter = view.findViewById(R.id.imgFilter);
        txtFilterSeek = view.findViewById(R.id.txtFilterSeek);
        seekBar01 = view.findViewById(R.id.seekBar01);
        seekBar02 = view.findViewById(R.id.seekBar02);
        checkBox01 = view.findViewById(R.id.checkBoxFilter01);
        checkBox01.setChecked(isCheck01);
        initConsStateList();
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
                Toast.makeText(mcontext, "is Changed : " + isCheck01, Toast.LENGTH_SHORT).show();
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
    public void onDetach() {
        super.onDetach();
        mcontext = null;
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



}
