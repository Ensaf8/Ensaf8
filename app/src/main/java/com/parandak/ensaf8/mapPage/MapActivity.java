package com.parandak.ensaf8.mapPage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.app.BaseActivity;
import com.parandak.ensaf8.bookMarkPage.BookMarkPageQuery;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Rating;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Cons_PhaseRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.GPointRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_GeopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.RatingRepo;
import com.parandak.ensaf8.dateAndReminder.AddReminderDialouge;
import com.parandak.ensaf8.fullScreenDialog.FullDialog;
import com.parandak.ensaf8.mapPage.drawer.FragmentDrawer_map;
import com.parandak.ensaf8.mapPage.model.BottomRVAdapter;
import com.parandak.ensaf8.mapPage.model.ConsState;
import com.parandak.ensaf8.mapPage.model.Customer;
import com.parandak.ensaf8.mapPage.model.History;
import com.parandak.ensaf8.mapPage.model.HistoryAdapter;
import com.parandak.ensaf8.searchPage.SearchPageActivity;
import com.parandak.ensaf8.tendHistoryDialog.TendHistoryDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.parandak.ensaf8.dataBase.DataContract.dateFormat;
import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianDate;
import static com.parandak.ensaf8.homePage.HomePageActivity.isConnected;

public class MapActivity extends BaseActivity implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public int getContentViewId() {
        return R.layout.activity_map;
    }
    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_map;
    }
    @Override
    public int getItemIdBefore() {
        return itemIdBefore;
    }
    int itemIdBefore;
    Context context = this;
    Activity activity = this;

    Double lat,lon;
    int inisatatus , status = 0;
    String statusdate;
    ///////MapView

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private boolean mPermissionsGranted;
    private List<String> mMissingPermissions;



    String INSTANCE_LATITUDE_MAIN_MAP = "latitudeMainMap";
    String INSTANCE_LONGITUDE_MAIN_MAP = "longitudeMainMap";
    String INSTANCE_ZOOM_LEVEL_MAIN_MAP = "zoomLevelMainMap";
    double DEFAULT_LATITUDE = 29.65257;
    double DEFAULT_LONGITUDE = 52.48157;
    double DEFAULT_ZOOM_LEVEL_MAIN_MAP = 14.5;




    MapView map;
    DrawerLayout drawerLayoutMap;
    FragmentDrawer_map drawerFragmentMap;
    private MyLocationNewOverlay mLocationOverlay;
    IMapController mController;
    List<OverlayItem> mStartGoalItems = new ArrayList<>();
    ItemizedOverlayWithFocus<OverlayItem> mOverlay;
    Cursor showCursor;
    Drawable marker_home,historyBlack,historyGrey;
    /////BottomSheet
    LinearLayout bottom_container;
    private BottomSheetBehavior mBottomSheetBehaviour;

    Button button_edit,bottom_sheet_status_data,button_add_customer,bottom_sheet_delete_cons,bottom_sheet_add_reminder;
    CheckBox checkBoxBookmark;
    TextView txt_bottom_book_type;
    RatingBar ratingBottom;
    ImageButton bottom_tend_history;
    EditText bottom_sheet_name;
    String init_bottom_sheet_name;
    boolean BOTTOM_SHEET_IS_HIDDEN = true;
    boolean isSingle = true;
    String ID_CONS_SELECTED;
    boolean isRatingBottomChange = false;
    String initBookedTypeID,bookedTypeID = "0";
    float initRating = 0;

    boolean isEdiNameChange = false;
    BottomRVAdapter bottomRVAdapter;
    List<Customer> customerList =new ArrayList<>();
    RecyclerView recyclerView;
    List<History> historyList = new ArrayList<>();
    ////ViewPager
    ViewPager2 viewPager2;
    Dialog dialog;
    ////ConsSate
    List<ConsState> consStateList = new ArrayList<>();
    /////Filter
    String state01 = "2";
    String state02 = "6";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d("ensaf::::::::", TAG + "> onCreate ");
        Toast.makeText(getBaseContext(), TAG + " : onCreate" , Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        itemIdBefore = intent.getIntExtra("itemIdBefore",R.id.navigation_home);
        hideKeyboard();
        checkExternalStorageState();
        checkAndroid6 ();
        osmInternal();
        bottomRecyclerView();
        bottomSheet();
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        locationButton();
        initDrawer();
        filterButton();
        if (mPermissionsGranted){
            initMap(savedInstanceState);
            viewPager();
            initDrawable ();
            initConsStateList();
            showOnMap();
            imgFilter();
            mapEvent();
        } else {
            showOnboarding();
        }
    }////End of onCreate
    public void showOnMap(){
        MapPageQuery mapPageQuery = new MapPageQuery();
        showCursor = mapPageQuery.showConsIndiWhereFilter02(drawerFragmentMap);
        drawerLayoutMap.closeDrawer(GravityCompat.START);
        showAllWaypoints(showCursor);
    }
    public void imgFilter(){
        drawerFragmentMap.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state01 = String.valueOf(drawerFragmentMap.getSeekProgress01());
                state02 = String.valueOf(drawerFragmentMap.getSeekProgress02());
                showOnMap();
            }
        });
    }
    public void initDrawer(){
        drawerLayoutMap = (DrawerLayout)findViewById(R.id.drawer_map);
        drawerFragmentMap = (FragmentDrawer_map)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_map);
        assert drawerFragmentMap != null;
        drawerFragmentMap.setUp(R.id.fragment_navigation_drawer_map,drawerLayoutMap);
        drawerFragmentMap.setProgressOn01(Integer.valueOf(state01));
        drawerFragmentMap.setProgressOn02(Integer.valueOf(state02));
        drawerFragmentMap.setCheckBox01(false);
        drawerFragmentMap.setCheckBox02(false);
        drawerFragmentMap.setCheckBox03(false);
        drawerFragmentMap.setCheckBoxBook(false);
        drawerFragmentMap.setDateFilter01("2020-09-22");
        drawerFragmentMap.setDateFilter02("2020-11-18");

    }
    public void initMap(Bundle savedInstanceState){
        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mController = map.getController();
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this),map);
        map.getOverlays().add(this.mLocationOverlay);
        mLocationOverlay.enableMyLocation();
        Bitmap mylocation = BitmapFactory.decodeResource(getResources(), R.drawable.arrow30);
        Bitmap mylocation_rotated = BitmapFactory.decodeResource(getResources(), R.drawable.arrow30_rotated);
        mLocationOverlay.setPersonIcon(mylocation);
        mLocationOverlay.setDirectionArrow(mylocation,mylocation_rotated);
        mLocationOverlay.setDrawAccuracyEnabled(true);

        // initiate map state
        if (savedInstanceState != null) {
            // restore saved instance of map
            GeoPoint position = new GeoPoint(savedInstanceState.getDouble(INSTANCE_LATITUDE_MAIN_MAP, DEFAULT_LATITUDE), savedInstanceState.getDouble(INSTANCE_LONGITUDE_MAIN_MAP, DEFAULT_LONGITUDE));
            mController.setCenter(position);
            mController.setZoom(savedInstanceState.getDouble(INSTANCE_ZOOM_LEVEL_MAIN_MAP, DEFAULT_ZOOM_LEVEL_MAIN_MAP));
        } else {
            mController.setZoom(DEFAULT_ZOOM_LEVEL_MAIN_MAP);
            GeoPoint startPoint = new GeoPoint(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
            mController.setCenter(startPoint);
            //mController.
        }
    }
    public void osmInternal(){
        org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
        File basePath = new File(getCacheDir().getAbsolutePath(), "osmdroid");
        osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tile");
        osmConf.setOsmdroidTileCache(tileCache);
    }
    public void locationButton(){
        FloatingActionButton fab_map;
        fab_map = findViewById(R.id.fab_map);
        fab_map.setAlpha(0.45f);
        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followDisableEnable();

            }
        });
    }
    public void filterButton(){
        FloatingActionButton fab_filter_map;
        fab_filter_map = findViewById(R.id.fab_map_filter);
        fab_filter_map.setAlpha(0.45f);
        fab_filter_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMap.openDrawer(GravityCompat.START);
            }
        });
    }
    public void followDisableEnable(){
        if (!mLocationOverlay.isFollowLocationEnabled()) {
            mLocationOverlay.enableFollowLocation();
        } else {
            mLocationOverlay.disableFollowLocation();
        }
    }
    private void bottomRecyclerView(){
        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvbottom);
        //customerAdapter = new CustomerAdapter(customerList);
        bottomRVAdapter = new BottomRVAdapter(context,customerList);
        //final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setAdapter(customerAdapter);
        recyclerView.setAdapter(bottomRVAdapter);

        bottomRVAdapter.setOnItemClickListener(new BottomRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Customer customer = customerList.get(position);
                String Cus_Id = customer.getId();
                Toast.makeText(getApplicationContext(),customer.getId()+" "+ customer.getName() + " " + customer.getPosition(), Toast.LENGTH_SHORT).show();
                FullDialog.display(getSupportFragmentManager(),Cus_Id);
            }
        });
    }
    private void initDrawable (){
        marker_home = map.getContext().getResources().getDrawable(R.drawable.home30);
        historyBlack = map.getContext().getResources().getDrawable(R.drawable.ic_history_24dp);
        historyGrey = map.getContext().getResources().getDrawable(R.drawable.ic_history_gray_30dp);
    }
    private void initConsStateList(){
        ConsState consState;
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue1);
        consState.setState("گود برداری");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue2);
        consState.setState("شروع اسکلت");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue22);
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
    private void viewPager(){
        viewPager2 = findViewById(R.id.viewPager2);
        //viewPager2.setAdapter(new ViewPagerAdapter(this, statuslist, viewPager2));
        viewPager2.setAdapter(new ViewPagerAdapter2(this, consStateList, viewPager2));
        //viewPager2.setAdapter(new ImageViewAdapter(this, viewPager2));
        viewPager2.setOffscreenPageLimit(15);
        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
        viewPager2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (viewPager2.getOrientation() == viewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });

        //viewPager2.setPageTransformer(new ZoomOutPageTransformer());

        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                status = viewPager2.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });
    }
    private void bottomSheet(){
        button_edit = (Button)findViewById(R.id.button_edit);
        checkBoxBookmark = (CheckBox) findViewById(R.id.checkBoxBookmark);
        txt_bottom_book_type = (TextView) findViewById(R.id.txt_bottom_book_type);
        bottom_sheet_name = (EditText)findViewById(R.id.bottom_sheet_name);
        bottom_container = (LinearLayout)findViewById(R.id.bottom_container);

        bottom_sheet_status_data = (Button)findViewById(R.id.btn_date);
        bottom_sheet_delete_cons = (Button) findViewById(R.id.bottom_sheet_delete_cons);
        bottom_sheet_add_reminder = (Button) findViewById(R.id.bottom_sheet_add_reminder);
        button_add_customer = (Button) findViewById(R.id.button_add_customer);
        bottom_tend_history = (ImageButton) findViewById(R.id.bottom_tend_history);

        checkBoxBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    txt_bottom_book_type.setEnabled(false);
                    txt_bottom_book_type.setText("* * *");
                    txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.darkGray));
                    bookedTypeID = "0";
                }else {
                    if (bookedTypeID.equals("0")){
                        bookedTypeID = "1";
                    }
                    MapPageQuery mapPageQuery = new MapPageQuery();
                    txt_bottom_book_type.setEnabled(true);
                    txt_bottom_book_type.setText(mapPageQuery.getBookmarkTypeTitle(bookedTypeID));
                    txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                    checkBoxBookmark.setChecked(true);
                }
            }
        });
        checkBoxBookmark.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final BookMarkPageQuery bookMarkPageQuery = new BookMarkPageQuery();
                final AlertDialog.Builder builderInner = new AlertDialog.Builder(MapActivity.this);

                View rowList = getLayoutInflater().inflate(R.layout.bookmark_listview, null);
                ListView listView = rowList.findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, bookMarkPageQuery.getBookMarkFolderListString());
                builderInner.setCancelable(true);
                listView.setAdapter(adapter);
                builderInner.setView(rowList);
                builderInner.setTitle("BOOKMARKS FOLDERS");
                final Dialog dialog = builderInner.create();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getBaseContext(), "BOOKMARK : " + bookMarkPageQuery.getBookMarkFoldeList().get(position).getId() , Toast.LENGTH_SHORT).show();
                        bookedTypeID = bookMarkPageQuery.getBookMarkFoldeList().get(position).getId();
                        MapPageQuery mapPageQuery = new MapPageQuery();
                        txt_bottom_book_type.setEnabled(true);
                        txt_bottom_book_type.setText(mapPageQuery.getBookmarkTypeTitle(bookedTypeID));
                        txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                        checkBoxBookmark.setChecked(true);
                        dialog.dismiss();
                    }
                });
                adapter.notifyDataSetChanged();
                dialog.show();
                return false;
            }
        });
        bottom_tend_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "TEND HISTORY . . . . ." , Toast.LENGTH_SHORT).show();
                TendHistoryDialog tendHistoryDialog = new TendHistoryDialog(context,activity,ID_CONS_SELECTED);
                tendHistoryDialog.showDialogHistory();
            }
        });
        bottom_sheet_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected){
                    AddReminderDialouge addReminderDialouge = new AddReminderDialouge(context,activity);
                    addReminderDialouge.showDialogueADD(ID_CONS_SELECTED);
                } else {
                    Toast.makeText(getApplicationContext(),"SignIn First !"  , Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottom_sheet_status_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(MapActivity.this);
            }
        });
        button_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSingle){
                    Intent intent = new Intent(v.getContext(), SearchPageActivity.class);
                    intent.putExtra("ID",ID_CONS_SELECTED);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(getBaseContext(), "First Insert Cons!!!!!!" , Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetEdiButton();
            }
        });
        bottom_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"container is clicked !!! "  ,Toast.LENGTH_LONG).show();
                ratingBottom.setRating(0);
            }
        });
        ratingBottom = (RatingBar)findViewById(R.id.ratingBottom);
        ratingBottom.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getBaseContext(),"rating Bottom is : " + rating ,Toast.LENGTH_LONG).show();
                isRatingBottomChange = true;
            }
        });
        bottom_sheet_delete_cons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MapActivity.this);
                builderInner.setMessage("DELETE" + ID_CONS_SELECTED);
                builderInner.setTitle("Are you Sure?");
                builderInner.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String test = "nothing";
                        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
                        IndividualRepo individualRepo = new IndividualRepo();
                        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
                        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
                        BookMarkRepo bookMarkRepo = new BookMarkRepo();
                        GPointRepo gPointRepo = new GPointRepo();
                        MapPageQuery mapPageQuery = new MapPageQuery();
                        boolean gpr = false,cpr,igr,ir,bm;
                        if (mapPageQuery.getGeopID(ID_CONS_SELECTED)!="!solo"){
                            gpr = gPointRepo.deleteIDGeop(mapPageQuery.getGeopID(ID_CONS_SELECTED)) ;
                        }
                        cpr = cons_phaseRepo.deleteIndiID(ID_CONS_SELECTED);
                        igr = indi_geopRepo.deleteIndiID(ID_CONS_SELECTED);
                        ir = individualRepo.deleteIndiID(ID_CONS_SELECTED);
                        bm = bookMarkRepo.delete_indID_BookMark(ID_CONS_SELECTED);

                        if (!gpr){
                            test = test + " gPoint ";
                        }

                        if (!cpr){
                            test = test + " ConsPhase ";
                        }

                        if (!igr){
                            test = test + " IndiGeop ";
                        }

                        if (!ir){
                            test = test + " Individual ";
                        }

                        if(!bm){
                            test = test + " bookMark ";
                        }


                        if (gpr && cpr && igr && ir && bm){
                            Toast.makeText(getBaseContext(), "Successfully All Deleted!!!" , Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getBaseContext(), test , Toast.LENGTH_LONG).show();
                        }
                        showOnMap();
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
        });
        View nestedScrollView = (View) findViewById(R.id.nestedScrollView);
        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                String state = "";
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        state = "DRAGGING";
                        BOTTOM_SHEET_IS_HIDDEN = false;
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        state = "SETTLING";
                        BOTTOM_SHEET_IS_HIDDEN = false;
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        state = "EXPANDED";
                        BOTTOM_SHEET_IS_HIDDEN = false;
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        bottomRVAdapter.notifyDataSetChanged();
                        state = "COLLAPSED";
                        BOTTOM_SHEET_IS_HIDDEN = false;
                        break;
                    }
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        state = "HIDDEN";
                        BOTTOM_SHEET_IS_HIDDEN = true;
                        bottom_sheet_name.setText("");
                        bottom_sheet_status_data.setText("");
                        statusdate = "";
                        customerList.clear();
                        //customerAdapter.notifyDataSetChanged();
                        bottomRVAdapter.notifyDataSetChanged();
                        historyList.clear();
                        checkBoxBookmark.setChecked(false);
                        txt_bottom_book_type.setText("پیش فرض");
                        ratingBottom.setRating(0);
                        isRatingBottomChange = false;
                        bookedTypeID = "0";
                        initRating = 0;
                        break;
                    }
                    case BottomSheetBehavior.STATE_HALF_EXPANDED: {
                        //customerAdapter.notifyDataSetChanged();
                        bottomRVAdapter.notifyDataSetChanged();
                        state = "HALF";
                        break;
                    }

                }
                //Toast.makeText(getBaseContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }
    private void bottomSheetEdiButton(){
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        if(isSingle){
            editSelectedCons();
        }else {
            inseringNewCons();
        }
        showOnMap();
        hideKeyboard();
    }
    private void editSelectedCons(){
        if (!init_bottom_sheet_name.equals(bottom_sheet_name.getText().toString())) {
            Individual individual = new Individual();
            individual.setID_Indi(ID_CONS_SELECTED);
            individual.setIsCons("1");
            individual.setIndiName(bottom_sheet_name.getText().toString());
            IndividualRepo individualRepo = new IndividualRepo();
            if (individualRepo.update(individual)) {
                Toast.makeText(getBaseContext(), "CONS Name with ID : " + ID_CONS_SELECTED + " Edited ! ", Toast.LENGTH_SHORT).show();
            }
        }
        if (inisatatus != status){
            Cons_Phase cons_phase = new Cons_Phase();
            cons_phase.setIndID(ID_CONS_SELECTED);
            cons_phase.setPhase(String.valueOf(status));
            cons_phase.setPhaseDate(statusdate);
            Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
            if (cons_phaseRepo.insert(cons_phase)>0){
                Toast.makeText(getBaseContext(), "CONS_phase with ID : "+ ID_CONS_SELECTED + " Edited ! ", Toast.LENGTH_SHORT).show();
            }
            drawerFragmentMap.setCheckBox01(false);
        }
        if (!initBookedTypeID.equals(bookedTypeID)){
            BookMark bookMark = new BookMark();
            bookMark.setIndID(ID_CONS_SELECTED);
            bookMark.setB_type_id(bookedTypeID);
            BookMarkRepo bookMarkRepo = new BookMarkRepo();
            if (initBookedTypeID.equals("0")){
                if(bookMarkRepo.insert(bookMark)>0)
                    Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + " isBookMarked ", Toast.LENGTH_SHORT).show();
            }else if (bookedTypeID.equals("0")){
                if(bookMarkRepo.delete_indID_BookMark(ID_CONS_SELECTED))
                    Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + " isNOT BookMarked ", Toast.LENGTH_SHORT).show();
            }else{
                if (bookMarkRepo.updateTypeByIndiID(bookMark)){
                    Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + "'s BookMark isUpdated ", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (isRatingBottomChange){
            Rating rating = new Rating();
            rating.setIndID(ID_CONS_SELECTED);
            RatingRepo ratingRepo = new RatingRepo();
            if (initRating!=0){
                if (ratingBottom.getRating()==0){
                    if (ratingRepo.delete_indID_Rating(ID_CONS_SELECTED)){
                        Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + " delete Star ", Toast.LENGTH_SHORT).show();
                    }
                }else if (initRating != ratingBottom.getRating()){
                    rating.setRate(String.valueOf(ratingBottom.getRating()));
                    if(ratingRepo.update_indID_Rating(rating)){
                        Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + " update Star ", Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                if(ratingBottom.getRating()!=0){
                    rating.setRate(String.valueOf(ratingBottom.getRating()));
                    if(ratingRepo.insert(rating)>0){
                        Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + " insert Star : " + rating.getRate(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    private void inseringNewCons(){
        Individual individual = new Individual();
        IndividualRepo individualRepo = new IndividualRepo();
        individual.setIndiName(bottom_sheet_name.getText().toString());
        individual.setIsCons("1");
        individualRepo.insert(individual);
        GPoint gPoint = new GPoint();
        gPoint.setLon(String.valueOf(lon));
        gPoint.setLat(String.valueOf(lat));
        gPoint.setIsSolo("1");
        GPointRepo gPointRepo = new GPointRepo();
        gPointRepo.insert(gPoint);
        Indi_Geop indi_geop = new Indi_Geop();
        indi_geop.setIndiID(individualRepo.lastIndividual());
        indi_geop.setGeopID(gPointRepo.lastGPoint());
        Indi_GeopRepo indi_geopRepo = new Indi_GeopRepo();
        indi_geopRepo.insert(indi_geop);
        Cons_Phase cons_phase = new Cons_Phase();
        cons_phase.setIndID(individualRepo.lastIndividual());
        cons_phase.setPhase(String.valueOf(status));
        cons_phase.setPhaseDate(statusdate);
        Cons_PhaseRepo cons_phaseRepo = new Cons_PhaseRepo();
        if (cons_phaseRepo.insert(cons_phase)>0){
            Toast.makeText(getBaseContext(), "New Cons ID : " + individualRepo.lastIndividual() + " in " + lat + " & " + lon , Toast.LENGTH_SHORT).show();
        }
        drawerFragmentMap.setCheckBox01(false);
        drawerFragmentMap.setCheckBox02(false);
        drawerFragmentMap.setCheckBox03(false);
        drawerFragmentMap.setCheckBoxBook(false);
    }
    public void showDialog(Activity activity){
        dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.history_dialog_recycler);

        RecyclerView recyclerView = dialog.findViewById(R.id.history_recycler);
        HistoryAdapter historyAdapter = new HistoryAdapter(MapActivity.this,historyList);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
    }
    private void hideKeyboard(){
        ///////Close/hide the Android Soft Keyboard###########################
        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if(view != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            ///////Close/hide the Android Soft Keyboard
        }
    }
    private void mapEvent(){
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                //Toast.makeText(getBaseContext(),"Short",Toast.LENGTH_SHORT).show();
                if (!BOTTOM_SHEET_IS_HIDDEN){
                    mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                return false;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) {
                isSingle = false;
                button_edit.setText("Insert");
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                String formattedDate = df.format(c);
                statusdate = formattedDate;
                bottom_sheet_status_data.setText("Now : " + formattedDate);
                lat = p.getLatitude();
                lon = p.getLongitude();
                mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mController.animateTo(p);
                return false;
            }
        };

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(getBaseContext(),mapEventsReceiver);
        map.getOverlays().add(mapEventsOverlay);
    }
    public void showAllWaypoints (Cursor cursor){///TODO replace cursor with overLayItemList
        mStartGoalItems.clear();
        map.getOverlays().remove(mOverlay);
        if (cursor.getCount()==0) {
            Toast.makeText(getBaseContext(),"No data in query",Toast.LENGTH_LONG).show();
            Log.d("ensaf::::::::", TAG + "> showAllWaypoints > No data in query");
        }else {
            while (cursor.moveToNext()) {
                String ID = cursor.getString(0);
                String NAME = cursor.getString(1);
                String DES = cursor.getString(5);
                Double LAT = cursor.getDouble(2);
                Double LON = cursor.getDouble(3);
                int STA = cursor.getInt(4);
                GeoPoint point = new GeoPoint(LAT, LON);
                OverlayItem Item = new OverlayItem(ID, NAME, DES, point);
                if (STA<=10){
                    Item.setMarker(map.getContext().getResources().getDrawable(consStateList.get(STA).getDrawable()));
                }else{
                    Item.setMarker(map.getContext().getResources().getDrawable(R.drawable.home30));
                }
                mStartGoalItems.add(Item);
            }/////
        }
        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(mStartGoalItems,this,this);
        map.getOverlays().add(mOverlay);
    }//////end of showAllWaypoint
    private void checkAndroid6 (){
        // check permissions on Android 6 and higher
        mPermissionsGranted = false;
        if (Build.VERSION.SDK_INT >= 23) {
            // check permissions
            Log.d("MainActivity", "Checking permissions...");
            mMissingPermissions = checkPermissions();
            mPermissionsGranted = mMissingPermissions.size() == 0;
        } else {
            mPermissionsGranted = true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onStart() {
        super.onStart();
        Toast.makeText(getBaseContext(), TAG + " : onStart" , Toast.LENGTH_SHORT).show();

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onResume() {
        super.onResume();
        Toast.makeText(getBaseContext(), TAG + " : onResume" , Toast.LENGTH_SHORT).show();
        if(mPermissionsGranted){
            bottomRVAdapter.notifyDataSetChanged();
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        hideKeyboard();
        if(map != null) {
            //this will refresh the osmdroid configuration on resuming.
            //if you make changes to the configuration, use
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
            map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onPause() {
        super.onPause();
        Toast.makeText(getBaseContext(), TAG + " : onPause" , Toast.LENGTH_SHORT).show();
        if(map != null) {
            //this will refresh the osmdroid configuration on resuming.
            //if you make changes to the configuration, use
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            //Configuration.getInstance().save(this, prefs);
            map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
        }
        /*} else {
            //boarding();
        }*/

    }
    public void onStop() {
        super.onStop();
        Toast.makeText(getBaseContext(), TAG + " : onStop" , Toast.LENGTH_SHORT).show();
    }
    public void onRestart() {
        super.onRestart();
        //customerAdapter.notifyDataSetChanged();
        bottomRVAdapter.notifyDataSetChanged();
        hideKeyboard();
        Toast.makeText(getBaseContext(), TAG + " : onRestart" , Toast.LENGTH_SHORT).show();
    }
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), TAG + " : onDestroy" , Toast.LENGTH_SHORT).show();
    }
    ///####permission Staff
    /* Check which permissions have been granted */
    private List<String> checkPermissions() {
        List<String> permissions = new ArrayList<>();

        // check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // add missing permission
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        // check for storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // add missing permission
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        return permissions;
    }
    /* Checks the state of External Storage */
    private void checkExternalStorageState() {

        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            // move MainActivity to back
            moveTaskToBack(true);
            // shutting down app
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void showOnboarding() {
        if(mPermissionsGranted) {
            //setContentView(R.layout.activity_main);
        } else {
            // point to the on main onboarding layout
            //setContentView(R.layout.map_page_onboarding);
            PermissionDialog.display(getSupportFragmentManager(),mMissingPermissions,REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            // show the okay button and attach listener
            /*Button okayButton = (Button) findViewById(R.id.button_okay);
            okayButton.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    if (mMissingPermissions != null && !mMissingPermissions.isEmpty()) {
                        // request permissions
                        String[] params = mMissingPermissions.toArray(new String[mMissingPermissions.size()]);
                        requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    }
                }
            });*/
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(map != null) {
            outState.putDouble(INSTANCE_LATITUDE_MAIN_MAP, map.getMapCenter().getLatitude());
            outState.putDouble(INSTANCE_LONGITUDE_MAIN_MAP, map.getMapCenter().getLongitude());
            outState.putDouble(INSTANCE_ZOOM_LEVEL_MAIN_MAP, map.getZoomLevelDouble());
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:	{
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                // check for ACCESS_FINE_LOCATION and WRITE_EXTERNAL_STORAGE
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (location && storage) {
                    // permissions granted - notify user
                    Toast.makeText(this, R.string.toast_message_permissions_granted, Toast.LENGTH_SHORT).show();
                    mPermissionsGranted = true;
                    // for refresh Activity
                    finish();
                    startActivity(getIntent());
                    //setContentView(R.layout.activity_map);
                } else {
                    // permissions denied - notify user
                    Toast.makeText(this, R.string.toast_message_unable_to_start_app, Toast.LENGTH_SHORT).show();
                    mPermissionsGranted = false;
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (BOTTOM_SHEET_IS_HIDDEN){
                this.finish();
            }else {
                mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
            }           //moveTaskToBack(true);



            return true;

        }

        return super.onKeyDown(keyCode, event);

    }
    @Override
    public boolean onItemSingleTapUp(int index, OverlayItem item) {
        historyList.clear();
        ID_CONS_SELECTED = item.getUid();
        button_edit.setText("Edit");
        isSingle = true;
        /////////
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String formattedDate = df.format(c);
        statusdate = formattedDate;

        MapPageQuery mapPageQuery = new MapPageQuery();
        Cursor cursorHistory = mapPageQuery.getHistoryConsPhase(ID_CONS_SELECTED);
        if (cursorHistory.moveToFirst()){
            do {
                History history =new History();
                if (cursorHistory.getInt(1)<11){
                    String [] arrOfFomattedDate1 = cursorHistory.getString(2).split(" ",2);
                    String [] arrOfGreDate1 = arrOfFomattedDate1[0].split("-",3);
                    history.setState(consStateList.get(cursorHistory.getInt(1)).getState());
                    history.setDate(getPersianDate(Integer.valueOf(arrOfGreDate1[0]), Integer.valueOf(arrOfGreDate1[1]), Integer.valueOf(arrOfGreDate1[2]))+ " " + arrOfFomattedDate1[1]);
                }else {
                    history.setState("ثبت شده");
                    history.setDate(cursorHistory.getString(2));
                }

                historyList.add(history);
            }while (cursorHistory.moveToNext());
        }
        ////////
        //L_or_S = "single";
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //Toast.makeText(getBaseContext(), "The Item ID is : " + item.getPoint().getLatitude(), Toast.LENGTH_SHORT).show();
        Cursor cursor = mapPageQuery.singleTapOnConsIndFirst(ID_CONS_SELECTED);
        Cursor cursor1 = mapPageQuery.singleTapOnConsIndSecond(ID_CONS_SELECTED);

        if (cursor.moveToFirst()) {
            bottom_sheet_name.setText(cursor.getString(1));
            init_bottom_sheet_name = cursor.getString(1);
            viewPager2.setCurrentItem(cursor.getInt(4));
            inisatatus =cursor.getInt(4);
            String [] arrOfFomattedDate = cursor.getString(5).split(" ",2);
            String [] arrOfGreDate = arrOfFomattedDate[0].split("-",3);
            if (cursor.getInt(4)<10){
                bottom_sheet_status_data.setText("آخرین بروز رسانی : " + getPersianDate(Integer.valueOf(arrOfGreDate[0]), Integer.valueOf(arrOfGreDate[1]), Integer.valueOf(arrOfGreDate[2]))+ " " + arrOfFomattedDate[1]);
            } else {
                bottom_sheet_status_data.setText("Last UpDate : " + cursor.getString(5));
            }

            if(cursor.getString(6)!=null){
                checkBoxBookmark.setChecked(true);
                txt_bottom_book_type.setText(mapPageQuery.getBookmarkTypeTitle(cursor.getString(6)));
                txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                initBookedTypeID = cursor.getString(6);
            }else {
                checkBoxBookmark.setChecked(false);
                txt_bottom_book_type.setEnabled(false);
                txt_bottom_book_type.setText("* * *");
                txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.darkGray));
                initBookedTypeID = "0";
            }

            bookedTypeID = initBookedTypeID;

            if (cursor.getString(7)!=null){
                ratingBottom.setRating(cursor.getFloat(7));
                initRating = cursor.getFloat(7);
            }else {
                ratingBottom.setRating(0);
                initRating = 0;
            }

        }
        if (cursor1.moveToFirst()){
            customerList.clear();
            do {
                Customer customer = new Customer();
                String CUS_ID = cursor1.getString(0);
                customer.setId(CUS_ID);
                customer.setName(cursor1.getString(1));
                customer.setPosition(cursor1.getString(2));
                customerList.add(customer);
            } while (cursor1.moveToNext());
            bottomRVAdapter.notifyDataSetChanged();
        }
        mController.animateTo(item.getPoint());
        return false;
    }
    @Override
    public boolean onItemLongPress(int index, OverlayItem item) {

        return false;
    }

}
