package com.parandak.ensaf8.homePage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parandak.ensaf8.DirectionManagement.XmlPullParserHandlerForEnsaf;
import com.parandak.ensaf8.DirectionManagement.wpt;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.app.BaseActivity;
import com.parandak.ensaf8.bookMarkPage.BookMarkPage;
import com.parandak.ensaf8.broadCast.ConnectivityReceiver;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Tend;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Cons_PhaseRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.CusAccountRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.GPointRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_CoopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_GeopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.TendRepo;

import com.parandak.ensaf8.homePage.drawer.FragmentDrawer;

import com.parandak.ensaf8.homePage.sessionManager.AlertDialogManager;
import com.parandak.ensaf8.homePage.sessionManager.ConnectSQLite;
import com.parandak.ensaf8.homePage.sessionManager.SessionManager;
import com.parandak.ensaf8.mapPage.MapActivity;
import com.parandak.ensaf8.searchPage.SearchPageActivity;
import com.parandak.ensaf8.storage.EnsafQueryExport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getDayOfMonthJalali;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getGregorian_dwg;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getMonthOfYearGreString;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getMonthOfYearJalaliString;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianYear;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersian_dwj;

public class HomePageActivity extends BaseActivity implements FragmentDrawer.
        FragmentDrawerListener,ConnectivityReceiver.ConnectivityReceiverListener {
    private final String TAG = this.getClass().getSimpleName();
    int itemIdBefore;
    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    @Override
    public int getItemIdBefore() {
        return itemIdBefore;
    }

    public static String ID_CONNECT_Indi1;
    String mimeType = "text/xml";
    ProgressDialog progressDialog;
    String Filename;
    public static final int REQUEST_CODE_OPEN_DIRECTORY = 123;
    private static final int WRITE_REQUEST_CODE = 43;
    List<wpt> wpts;

    String __mj, _dj, _dwj;

    Button loginbtn,btnLogin;
    EditText ediUserName,ediPassWord;
    TextView txtForPassword;
    TextView txtProfileName;
    TextView txtNetStatus;
    private Toolbar mToolbar;
    private TabLayout tabLayout;

    DrawerLayout drawerLayout;
    FragmentDrawer drawerFragment;

    AlertDialogManager alert = new AlertDialogManager();

    SessionManager sessionManager;
    ConnectSQLite connectSQLite;

    public static boolean isConnected = false;
    Context context = this;
    private int[] tabIcons = {
            R.drawable.ic_event_task_gray_24dp,
            R.drawable.ic_event_note_gray_24dp,
            R.drawable.ic_event_task_black_24dp,
            R.drawable.ic_event_note_black_24dp
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        itemIdBefore = intent.getIntExtra("itemIdBefore",R.id.navigation_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        txtNetStatus = findViewById(R.id.txtNetStatus);
        initCollapsingToolbar();
        initDrawerFragment();
        initViewPager();
        //imageLogo();
        accountManage();
        checkConnection();

        Date c = Calendar.getInstance().getTime();

        headerinit(c);
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String formattedDate = df.format(c);
        Filename = "backUp " + formattedDate;
        sessionManaging();

    }///end of on create

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        txtNetStatus.setText("Net Status : " + isConnected);
        Toast.makeText(getApplicationContext(), "Net Status : " + isConnected, Toast.LENGTH_SHORT).show();
    }
    private void sessionManaging() {
        txtProfileName = (TextView)findViewById(R.id.txtProfileName);
        sessionManager = new SessionManager(getApplicationContext());
        connectSQLite = new ConnectSQLite();
        if (!sessionManager.isLoggedIn()){
            drawerLayout.openDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(),"You Are Not Login !", Toast.LENGTH_SHORT).show();
        } else {
            // get user data from session
            HashMap<String, String> user = sessionManager.getUserDetails();

            String name = user.get(SessionManager.KEY_NAME);

            String password = user.get(SessionManager.KEY_PASSWORD);
            if (connectSQLite.checkIsUserName(name)){
                if (connectSQLite.checkUserNameCount(name)==1){
                    if (password.equals(connectSQLite.getPassWord(name))){
                        txtProfileName.setText(name + " " + connectSQLite.getID_UserName(name));
                        ID_CONNECT_Indi1 = connectSQLite.getID_UserName(name);
                        loginbtn.setText("LOG OUT");
                        isConnected = true;
                        initViewPager();
                        Toast.makeText(getApplicationContext(),"You have been Logged in" + ID_CONNECT_Indi1 , Toast.LENGTH_SHORT).show();
                    }else{
                        alert.showAlertDialog(HomePageActivity.this , "Login failed..", "Wrong PassWord", false);
                    }
                }else {
                    alert.showAlertDialog(HomePageActivity.this , "Login failed..", "error in data base call admin", false);
                }
            } else {
                alert.showAlertDialog(HomePageActivity.this , "Login failed..", "You are not sign up yet !", false);
            }
        }
    }
    private void headerinit(Date date){
        SimpleDateFormat dfy = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfd = new SimpleDateFormat("dd");

        TextView txt_jalali,txt_dwg,txt_gre;
        txt_jalali = (TextView)findViewById(R.id.txt_jalali);
        txt_dwg = (TextView)findViewById(R.id.txt_dwg);
        __mj = String.valueOf(getMonthOfYearJalaliString(date));
        _dj = String.valueOf(getDayOfMonthJalali(date));
        _dwj = String.valueOf(getPersian_dwj(date));
        txt_jalali.setText(_dwj + " " + _dj + " " + __mj + " " +getPersianYear(date));
        txt_dwg.setText(getGregorian_dwg(date));
        txt_gre = (TextView)findViewById(R.id.txt_gre);
        txt_gre.setText(dfy.format(date) + " " + getMonthOfYearGreString(date) + " " + dfd.format(date));

    }
    private void initViewPager(){
        ViewPager viewPager;
        ////////////////////////
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TasksFragment(), "TASK !");
        adapter.addFragment(new NewsFragment(), "NEWS !");
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[3]);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==1){
                    tabLayout.getTabAt(0).setIcon(tabIcons[2]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                }else if (tab.getPosition()==0){
                    tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                    tabLayout.getTabAt(1).setIcon(tabIcons[3]);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void accountManage(){
        loginbtn = (Button)findViewById(R.id.btnLoginLogout);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected){
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
                    builderInner.setTitle("Are you Sure to ");
                    builderInner.setMessage("Log Out ! ! !");
                    builderInner.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txtProfileName.setText("* * *");
                            loginbtn.setText("LOG IN");
                            ID_CONNECT_Indi1 = null ;
                            isConnected = false;
                            sessionManager.logoutUser();
                            Toast.makeText(getApplicationContext(), "LogingOut ! ! !", Toast.LENGTH_SHORT).show();
                            initViewPager();
                        }
                    });
                    builderInner.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }else {
                    Toast.makeText(getApplicationContext(),"LOGing  IN !", Toast.LENGTH_SHORT).show();
                    LayoutInflater inflater = LayoutInflater.from(HomePageActivity.this);
                    View view = inflater.inflate(R.layout.login_promp,null);
                    ediUserName = view.findViewById(R.id.ediUserName);
                    ediPassWord = view.findViewById(R.id.ediPassword);
                    txtForPassword = view.findViewById(R.id.txtForPassword);
                    btnLogin = view.findViewById(R.id.btnLogin);
                    final AlertDialog builderInner = new AlertDialog.Builder(HomePageActivity.this).create();
                    builderInner.setView(view);
                    builderInner.setTitle("Login");
                    builderInner.show();
                    builderInner.setCancelable(true);
                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //HomePageQuery homePageQuery = new HomePageQuery();
                            String username = ediUserName.getText().toString();
                            String password = ediPassWord.getText().toString();
                            if(username.trim().length() > 0 && password.trim().length() > 0){
                                //Cursor cursor = homePageQuery.checkUserName(username);
                                //if (cursor.moveToFirst()) {
                                if (connectSQLite.checkIsUserName(username)){
                                    //if (cursor.getCount() == 1) {
                                    if (connectSQLite.checkUserNameCount(username)==1){
                                        //if (password.equals(cursor.getString(1))){
                                        if (password.equals(connectSQLite.getPassWord(username))){
                                            //txtProfileName.setText(cursor.getString(0)+ " " + cursor.getString(2));
                                            txtProfileName.setText(username + " " + connectSQLite.getID_UserName(username));
                                            //ID_CONNECT_Indi1 = cursor.getString(2);
                                            ID_CONNECT_Indi1 = connectSQLite.getID_UserName(username);
                                            loginbtn.setText("LOG OUT");
                                            isConnected = true;
                                            initViewPager();
                                            Toast.makeText(getApplicationContext(),"You have been Logged in" , Toast.LENGTH_SHORT).show();
                                            sessionManager.createLoginSession(username, password);
                                            builderInner.dismiss();
                                        }else{
                                            alert.showAlertDialog(HomePageActivity.this , "Login failed..", "Wrong PassWord", false);
                                        }
                                    }else {
                                        alert.showAlertDialog(HomePageActivity.this , "Login failed..", "error in data base call admin", false);
                                    }
                                } else {
                                    alert.showAlertDialog(HomePageActivity.this , "Login failed..", "You are not sign up yet !", false);
                                }
                            }else {
                                alert.showAlertDialog(HomePageActivity.this , "Login failed..", "Please enter username and password", false);
                            }
                        }
                    });
                }
            }
        });
    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbar.setTitle(getString(R.string.app_name));
                    collapsingToolbar.setTitle(_dwj + " " + _dj + " " + __mj);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void initDrawerFragment(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setDrawerListener(this);

        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                drawerLayout, mToolbar);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        txtNetStatus.setText("Net Status is Change : " + isConnected);
        Toast.makeText(getApplicationContext(), "Net Status is Change : " + isConnected, Toast.LENGTH_SHORT).show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                checkConnection();
                break;
            case 1:
                BookMarkPage.display(getSupportFragmentManager());
                Toast.makeText(getApplicationContext(), "BookMarks. . .", Toast.LENGTH_SHORT).show();
                Log.d("ensaf::::::::", TAG + "> transaction : " + BookMarkRepo.transactionBookMarkType());
                break;
            case 2:
                AlertDialog.Builder builderInner02 = new AlertDialog.Builder(context);
                builderInner02.setTitle("Clear Date Base? ");
                builderInner02.setMessage("This Delete data ! ! !");
                builderInner02.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearDataBase();
                    }
                });
                builderInner02.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner02.show();
                break;
            case 3:
                createFile(mimeType,Filename);
                Toast.makeText(getApplicationContext(), "Exporting", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Intent intent01 = new Intent()
                        .setType("*/*")
                        .addCategory(Intent.CATEGORY_OPENABLE)
                        .setAction("open")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent01,"Select a file"), REQUEST_CODE_OPEN_DIRECTORY);
                Toast.makeText(getApplicationContext(), "Importing", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    private void insertingIndiData(){
        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
        CusAccountRepo cusAccountRepo = new CusAccountRepo();
        GPointRepo gPointRepo = new GPointRepo();
        Indi_CoopRepo indi_coopRepo = new Indi_CoopRepo();
        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
        IndividualRepo individualRepo = new IndividualRepo();
        TendRepo tendRepo = new TendRepo();

        cons_phaseRepo.delete();
        cusAccountRepo.delete();
        gPointRepo.delete();
        indi_coopRepo.delete();
        indi_geopRepo.delete();
        individualRepo.delete();
        tendRepo.delete();

        Toast.makeText(getApplicationContext(), "dataClear!", Toast.LENGTH_SHORT).show();

        Cons_Phase cons_phase = new Cons_Phase();
        cons_phase.setIndID("1");
        cons_phase.setPhase("7");
        cons_phase.setPhaseDate("2019-01-27 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("1");
        cons_phase.setPhase("2");
        cons_phase.setPhaseDate("2018-08-28 00:00:00 ");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("1");
        cons_phase.setPhase("1");
        cons_phase.setPhaseDate("2018-05-07 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("2");
        cons_phase.setPhase("5");
        cons_phase.setPhaseDate("2019-01-27 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("2");
        cons_phase.setPhase("2");
        cons_phase.setPhaseDate("2018-05-27 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("2");
        cons_phase.setPhase("1");
        cons_phase.setPhaseDate("2017-06-29 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("3");
        cons_phase.setPhase("2");
        cons_phase.setPhaseDate("2019-01-07 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("3");
        cons_phase.setPhase("1");
        cons_phase.setPhaseDate("2018-02-21 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("4");
        cons_phase.setPhase("10");
        cons_phase.setPhaseDate("2019-01-10 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("4");
        cons_phase.setPhase("2");
        cons_phase.setPhaseDate("2018-06-20 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("4");
        cons_phase.setPhase("1");
        cons_phase.setPhaseDate("2018-02-11 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("2");
        cons_phase.setPhase("3");
        cons_phase.setPhaseDate("2019-01-29 00:00:00");
        cons_phaseRepo.insert(cons_phase);

        cons_phase.setIndID("18");
        cons_phase.setPhase("9");
        cons_phase.setPhaseDate("2019-02-04 00:00:00");
        cons_phaseRepo.insert(cons_phase);


        //################################################

        CusAccount cusAccount = new CusAccount();
        cusAccount.setIndID("23");
        cusAccount.setAccountName("AliParandak");
        cusAccount.setPassWord("1");
        cusAccount.setIsAct("1");
        cusAccountRepo.insert(cusAccount);

        cusAccount.setIndID("16");
        cusAccount.setAccountName("AliRezaGozar");
        cusAccount.setPassWord("2");
        cusAccount.setIsAct("1");
        cusAccountRepo.insert(cusAccount);

        cusAccount.setIndID("5");
        cusAccount.setAccountName(null);
        cusAccount.setPassWord(null);
        cusAccount.setIsAct("0");
        cusAccountRepo.insert(cusAccount);

        cusAccount.setIndID("6");
        cusAccount.setAccountName(null);
        cusAccount.setPassWord(null);
        cusAccount.setIsAct("0");
        cusAccountRepo.insert(cusAccount);

        cusAccount.setIndID("7");
        cusAccount.setAccountName(null);
        cusAccount.setPassWord(null);
        cusAccount.setIsAct("0");
        cusAccountRepo.insert(cusAccount);

        cusAccount.setIndID("22");
        cusAccount.setAccountName(null);
        cusAccount.setPassWord(null);
        cusAccount.setIsAct("0");
        cusAccountRepo.insert(cusAccount);


        ///#########################################
        GPoint gPoint = new GPoint();

        gPoint.setLat("29.62506");
        gPoint.setLon("52.49120");
        gPoint.setIsSolo("1");
        gPointRepo.insert(gPoint);



        gPoint.setLat("29.66596");
        gPoint.setLon("52.4847");
        gPoint.setIsSolo("1");
        gPointRepo.insert(gPoint);


        gPoint.setLat("29.64963");
        gPoint.setLon("52.47922");
        gPoint.setIsSolo("1");
        gPointRepo.insert(gPoint);


        gPoint.setLat("29.65735");
        gPoint.setLon("52.48497");
        gPoint.setIsSolo("1");
        gPointRepo.insert(gPoint);


        gPoint.setLat("29.63132");
        gPoint.setLon("52.53194");
        gPoint.setIsSolo("1");
        gPointRepo.insert(gPoint);
        Toast.makeText(getApplicationContext(), "GPoint!", Toast.LENGTH_SHORT).show();

        /////##########
        Indi_Coop indi_coop = new Indi_Coop();
        indi_coop.setFirstPartID("1");
        indi_coop.setSecondPartID("5");
        indi_coop.setTitle("سرپرست کارگاه");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("1");
        indi_coop.setSecondPartID("6");
        indi_coop.setTitle("مالک");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("2");
        indi_coop.setSecondPartID("7");
        indi_coop.setTitle("شریک");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("2");
        indi_coop.setSecondPartID("10");
        indi_coop.setTitle("شریک");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("3");
        indi_coop.setSecondPartID("7");
        indi_coop.setTitle("مالک");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("4");
        indi_coop.setSecondPartID("8");
        indi_coop.setTitle("سرپرست کارگاه");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("4");
        indi_coop.setSecondPartID("9");
        indi_coop.setTitle("نماینده مالک");
        indi_coopRepo.insert(indi_coop);

        indi_coop.setFirstPartID("19");
        indi_coop.setSecondPartID("22");
        indi_coop.setTitle("سازنده");
        indi_coopRepo.insert(indi_coop);

        ///########
        Indi_Geop indi_geop = new Indi_Geop();
        indi_geop.setIndiID("1");
        indi_geop.setGeopID("1");
        indi_geopRepo.insert(indi_geop);

        indi_geop.setIndiID("2");
        indi_geop.setGeopID("2");
        indi_geopRepo.insert(indi_geop);

        indi_geop.setIndiID("3");
        indi_geop.setGeopID("3");
        indi_geopRepo.insert(indi_geop);

        indi_geop.setIndiID("4");
        indi_geop.setGeopID("4");
        indi_geopRepo.insert(indi_geop);

        indi_geop.setIndiID("18");
        indi_geop.setGeopID("5");
        indi_geopRepo.insert(indi_geop);


        /////#########
        Individual individual = new Individual();
        //individual.setID_Indi("0");
        //individual.setIndiName("Mr.P");
        //individual.setIsCons("0");
        //individualRepo.insert(individual);

        individual.setIndiName("پروژه مهند");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("پروژه نیایش");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("28 همت شمالی");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("2 گلخون");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("علیرضا باقی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("آقای محقق");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("محسن سرتیپی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("مهندس آزادی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("امراللهی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("آقای رحیمی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("آقای صوفی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("آقای محجوب");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("فروزانی نگهبان");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("مهندس پیروانی");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("پروژه ته ک 3 قذوسی");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("gozar");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("پروژه کوچه 9 ارم");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("پروژه کوچه 13 ارم");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("دوبلوک قدوسی غربی");
        individual.setIsCons("1");
        individualRepo.insert(individual);

        individual.setIndiName("میرعظیم");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("مهندس یارشناس");
        individual.setIsCons("0");
        individualRepo.insert(individual);

        individual.setIndiName("Mr.P");
        individual.setIsCons("0");
        individualRepo.insert(individual);
        //Toast.makeText(getApplicationContext(), "GPoint!" + a , Toast.LENGTH_SHORT).show();

        ///##################




        ///////########
        Tend tend = new Tend();
        tend.setInd1ID("23");
        tend.setInd2ID("4");
        tend.setTitle("پی گیری خرید");
        tend.setDetail("");
        tend.setTendDate("2019-10-17 00:00");
        tend.setIsChecked("1");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("5");
        tend.setTitle("خرید تجهیزات");
        tend.setDetail("");
        tend.setTendDate("2019-10-30 00:00");
        tend.setIsChecked("1");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("2");
        tend.setTitle("سرکشی");
        tend.setDetail("");
        tend.setTendDate("2019-11-01 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("7");
        tend.setTitle("پی گیری تلفنی");
        tend.setDetail("");
        tend.setTendDate("2019-10-20 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("8");
        tend.setTitle("صحبت حضوری در مورد پروژه شیمی گیاه");
        tend.setDetail("");
        tend.setTendDate("2019-11-01 00:00");
        tend.setIsChecked("1");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("9");
        tend.setTitle("دادن هدیه");
        tend.setDetail("");
        tend.setTendDate("2019-12-20 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("3");
        tend.setTitle("سرکشی ");
        tend.setDetail("");
        tend.setTendDate("2019-10-27 09:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("6");
        tend.setTitle("پی گیری خرید");
        tend.setDetail("");
        tend.setTendDate("2019-10-27 11:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("10");
        tend.setTitle("پی گیری پلان");
        tend.setDetail("");
        tend.setTendDate("2019-10-26 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("11");
        tend.setTitle("پی گیری طرح قیمتی");
        tend.setDetail("");
        tend.setTendDate("2019-10-27 11:30");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("12");
        tend.setTitle("دادن کارت شارژ");
        tend.setDetail("");
        tend.setTendDate("2019-10-28 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("13");
        tend.setTitle("تماس برای پی گیری شیمی گیاه");
        tend.setDetail("");
        tend.setTendDate("2019-10-28 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("14");
        tend.setTitle("پرزنت");
        tend.setDetail("");
        tend.setTendDate("2019-10-30 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("23");
        tend.setInd2ID("15");
        tend.setTitle("پرزنت");
        tend.setDetail("");
        tend.setTendDate("2019-10-30 00:00");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        tend.setInd1ID("16");
        tend.setInd2ID("15");
        tend.setTitle("پی گیری پلان");
        tend.setDetail("");
        tend.setTendDate("2019-10-27 11:30");
        tend.setIsChecked("0");
        tendRepo.insert(tend);

        Toast.makeText(getApplicationContext(), "dataInserted!", Toast.LENGTH_SHORT).show();

    }
    private void clearDataBase(){
        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
        CusAccountRepo cusAccountRepo = new CusAccountRepo();
        GPointRepo gPointRepo = new GPointRepo();
        Indi_CoopRepo indi_coopRepo = new Indi_CoopRepo();
        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
        IndividualRepo individualRepo = new IndividualRepo();
        TendRepo tendRepo = new TendRepo();

        cons_phaseRepo.delete();
        cusAccountRepo.delete();
        gPointRepo.delete();
        indi_coopRepo.delete();
        indi_geopRepo.delete();
        individualRepo.delete();
        tendRepo.delete();

        Toast.makeText(getApplicationContext(), "dataClear!", Toast.LENGTH_SHORT).show();
    }
    private void createFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a file with the requested MIME type.
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_DIRECTORY && resultCode == AppCompatActivity.RESULT_OK) {
            //Log.d(TAG, String.format("Open Directory result Uri : %s", data.getData()));


            Uri treeUri = data.getData();
            AsyncTaskExample asyncTask=new AsyncTaskExample();
            try {
                asyncTask.execute(treeUri);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(requestCode == WRITE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){

            final EnsafQueryExport ensafQueryExport = new EnsafQueryExport();
            final Uri treeUri = data.getData();
            alterDocument(treeUri,ensafQueryExport.exportQuery());
        }
    }
    private class AsyncTaskExample extends AsyncTask<Uri, String, List<wpt>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomePageActivity.this);
            progressDialog.setMessage("WAIT...Importing ! ");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected List<wpt> doInBackground(Uri... uri) {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(uri[0]);
                XmlPullParserHandlerForEnsaf parserHandlerForEnsaf = new XmlPullParserHandlerForEnsaf();
                wpts = parserHandlerForEnsaf.parse(inputStream);

                //XmlPullParserHandlerForWpt parserHandlerForWpt = new XmlPullParserHandlerForWpt();
                //wpts = parserHandlerForWpt.parse(inputStream);


                Individual individual = new Individual();
                IndividualRepo individualRepo = new IndividualRepo();

                GPoint gPoint = new GPoint();
                GPointRepo gPointRepo = new GPointRepo();

                Indi_Geop indi_geop = new Indi_Geop();
                Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();

                Cons_Phase cons_phase = new Cons_Phase();
                Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();

                for (int i = 0; i < wpts.size(); i++){
                    wpt wpt;

                    wpt = wpts.get(i);
                    individual.setIndiName(wpt.getName());
                    individual.setIsCons("1");
                    individualRepo.insert(individual);

                    gPoint.setLat(wpt.getLat());
                    gPoint.setLon(wpt.getLon());
                    gPoint.setIsSolo("1");
                    gPointRepo.insert(gPoint);

                    indi_geop.setIndiID(individualRepo.lastIndividual());
                    indi_geop.setGeopID(gPointRepo.lastGPoint());
                    indi_geopRepo.insert(indi_geop);

                    cons_phase.setIndID(individualRepo.lastIndividual());
                    cons_phase.setPhase("11");
                    cons_phase.setPhaseDate(wpt.getDate());
                    cons_phaseRepo.insert(cons_phase);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wpts;
        }
        @Override
        protected void onPostExecute(List<wpt> wptss) {
            super.onPostExecute(wptss);
            if(wptss!=null) {
                progressDialog.hide();
                wpts = wptss;
            }else {
                progressDialog.hide();
                Toast.makeText(getBaseContext(),"Ensaf file has been loaded",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void alterDocument(Uri uri,String txt) {
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            //fileOutputStream.write(("Overwritten by MyCloud at " +
            //System.currentTimeMillis() + "\n").getBytes());
            fileOutputStream.write((txt+"\n").getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
