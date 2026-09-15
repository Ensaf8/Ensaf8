package com.parandak.ensaf8.mapPage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.parandak.ensaf8.dataBase.DBQuery;
import com.parandak.ensaf8.dataBase.DataContract;
import com.parandak.ensaf8.dataBase.model_Indivi.Atten;
import com.parandak.ensaf8.dataBase.model_Indivi.BookMark;
import com.parandak.ensaf8.dataBase.model_Indivi.Cons_Phase;
import com.parandak.ensaf8.dataBase.model_Indivi.GPoint;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.Place_Geop;
import com.parandak.ensaf8.dataBase.model_Indivi.Rating;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.AttenRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.BookMarkRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Cons_PhaseRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.GPointRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_GeopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Place_GeopRepo;
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
import org.osmdroid.views.overlay.Polygon;
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
import static com.parandak.ensaf8.homePage.HomePageActivity.ID_CONNECT_Indi1;

public class MapActivity extends BaseActivity implements ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
    public final String TAG = this.getClass().getSimpleName();

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

    @Override
    public Bundle getLastState() {
        Bundle bundle = new Bundle();
        bundle.putString("NAME","MAP PAGE! from MapActivity!");
        return bundle;
    }

    private MapPermissionManager mapPermissionManager;

    int itemIdBefore;
    Context context = MapActivity.this;
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
    List<OverlayItem> PlaceItems = new ArrayList<>();
    ItemizedOverlayWithFocus<OverlayItem> mOverlay;
    ItemizedOverlayWithFocus<OverlayItem> placeOverlay;
    Drawable marker_home,historyBlack,historyGrey;
    /////BottomSheet
    LinearLayout bottom_container;
    private BottomSheetBehavior mBottomSheetBehaviour;

    Button button_edit,bottom_sheet_status_data;
    ImageButton bottom_sheet_add_reminder,bottom_sheet_attendance,button_add_customer;
    FloatingActionButton fab_map;
    CheckBox checkBoxBookmark;
    TextView txt_consCount;
    TextView txt_bottom_book_type;
    RatingBar ratingBottom;
    ImageButton bottom_tend_history;
    EditText bottom_sheet_name;
    String init_bottom_sheet_name;
    boolean BOTTOM_SHEET_IS_HIDDEN = true;
    int isTapOn = 0;
    //boolean isSingle = true;
    String ID_CONS_SELECTED;
    boolean isRatingBottomChange = false;
    boolean isBookTouch = true;
    //String initBookedTypeID,bookedTypeID = "0";
    ArrayList<String> bookMarkFolderList = new ArrayList<>();
    ArrayList<String> bookMarkSelectedList = new ArrayList<>();

    ArrayList<String> bookMarkFolderListID = new ArrayList<>();
    ArrayList<String> initBookMarkFolderListID = new ArrayList<>();
    ArrayList<String> initBookMarkFolderList = new ArrayList<>();
    float initRating = 0;

    boolean isEdiNameChange = false;
    BottomRVAdapter bottomRVAdapter;
    List<Customer> customerList =new ArrayList<>();
    RecyclerView recyclerView;
    List<History> historyList = new ArrayList<>();
    ////ViewPager
    ViewPager2 viewPager2;
    Dialog dialog;
    ////
    ImageButton imgBtnBottom;
    ////ConsSate
    List<ConsState> consStateList = new ArrayList<>();
    /////Filter
    String state01 = "2";
    String state02 = "6";
    ///polygon
    List<Polygon> regioList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        itemIdBefore = intent.getIntExtra("itemIdBefore",R.id.navigation_home);
        Log.d("ensaf::::::::", TAG + "> onCreate savedInstanceState : " + savedInstanceState);
        MapUtils.hideKeyboard(this);
        checkExternalStorageState();
        mapPermissionManager = new MapPermissionManager();
        checkAndroid6 ();
        osmInternal();
        locationButton();
        bottomRecyclerView();
        bottomSheet();
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        initDrawer();
        filterButton();
        if (mPermissionsGranted){
            initMap(savedInstanceState);
            //viewPager();
            //imageBottomSheet();
            initDrawable ();
            initConsStateList();
            showOnMap();
            imgFilter();
            mapEvent();
        } else {
            showOnboarding();
        }
    }////End of onCreate
    public void initPolygonList(){
        regioList = getPolygonList();
    }
    public List<Polygon> getPolygonList(){
        List<Polygon> polygonList = new ArrayList<>();

        int color1 = Color.argb(75,255,255,0);
        Polygon polygon = new Polygon();
        polygon.setFillColor(color1);
        polygon.setPoints(getGeoPointList());
        polygon.setStrokeWidth(1);
        polygon.setTitle("Test");
        polygon.setId("0");
        polygonList.add(polygon);
        return polygonList;
    }
    public List<GeoPoint> getGeoPointList(){
        List<GeoPoint> geoPointList = new ArrayList<>();
        GeoPoint point;
        //1
        point = new GeoPoint(29.665005,52.476917);
        geoPointList.add(point);
        //2
        point = new GeoPoint(29.654347,52.485015);
        geoPointList.add(point);
        //3
        point = new GeoPoint(29.656721,52.490300);
        geoPointList.add(point);
        //4
        point = new GeoPoint(29.662761,52.486626);
        geoPointList.add(point);
        //5
        point = new GeoPoint(29.665654,52.486509);
        geoPointList.add(point);
        //6
        point = new GeoPoint(29.666301,52.480922);
        geoPointList.add(point);
        return geoPointList;
    }
    public void showOnMap(){
        MapPageQuery mapPageQuery = new MapPageQuery();
        txt_consCount = (TextView) findViewById(R.id.consCountTxt);
        Cursor showCursor = mapPageQuery.showConsIndiWhereFilter02(drawerFragmentMap);
        drawerLayoutMap.closeDrawer(GravityCompat.START);
        showOnMapCons(showCursor);
        showOnMapPolygon();
        Cursor showPlaceCursor = mapPageQuery.showAllPlaceGPoint();
        showOnMapPlace(showPlaceCursor);
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
        ///TODO NullException
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
        viewPager2.setAdapter(new ViewPagerAdapter2(this, consStateList, viewPager2));
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
        bottom_sheet_add_reminder = (ImageButton) findViewById(R.id.bottom_sheet_add_reminder);
        bottom_sheet_attendance = (ImageButton) findViewById(R.id.bottom_sheet_attendance);
        button_add_customer = (ImageButton) findViewById(R.id.button_add_customer);
        bottom_tend_history = (ImageButton) findViewById(R.id.bottom_tend_history);

        checkBoxBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBookTouch = true;
                //Log.d("ensaf::::::::", TAG + "setOnCheckedChange isBookTouch is " + isBookTouch);
                if (!isChecked){
                    txt_bottom_book_type.setEnabled(false);
                    txt_bottom_book_type.setText("* * *");
                    txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.darkGray));
                    bookMarkSelectedList.clear();
                    //bookedTypeID = "0";
                }else {
                    /*if (bookedTypeID.equals("0")){
                        bookedTypeID = "1";
                    }*/
                    if (bookMarkSelectedList.size() == 0){
                        bookMarkSelectedList.add("1");
                    }
                    MapPageQuery mapPageQuery = new MapPageQuery();
                    txt_bottom_book_type.setEnabled(true);
                    txt_bottom_book_type.setText(mapPageQuery.getBookmarkTypeTitle(bookMarkSelectedList.get(0)));
                    txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                    checkBoxBookmark.setChecked(true);
                }
            }
        });

        checkBoxBookmark.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isBookTouch = true;
                //Log.d("ensaf::::::::", TAG + "setOnLongClickListener isBookTouch is " + isBookTouch);
                final BookMarkPageQuery bookMarkPageQuery = new BookMarkPageQuery();
                //Log.d("ensaf::::::::", TAG + "> onLongClick");
                bookMarkFolderList =  bookMarkPageQuery.getBookMarkFolderListString();
                bookMarkFolderListID = bookMarkPageQuery.getBookMarkFolderListIntID();
                //Log.d("ensaf::::::::", TAG + "> bookMarkFolderList>" + bookMarkFolderList.size());
                //Log.d("ensaf::::::::", TAG + "> bookMarkFolderListID>" + bookMarkFolderListID.size());
                //Log.d("ensaf::::::::", TAG + "> initBookMarkFolderList>" + initBookMarkFolderList.size());
                final AlertDialog.Builder builderInner = new AlertDialog.Builder(MapActivity.this);

                View rowList = getLayoutInflater().inflate(R.layout.bookmark_listview, null);
                ListView listView = rowList.findViewById(R.id.listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                        bookMarkFolderList);
                builderInner.setCancelable(true);
                listView.setAdapter(adapter);
                int ij = 0;
                while (bookMarkFolderList.size()>ij){
                    //Log.d("ensaf::::::::", TAG + "> bookMarkFolderList : "+ bookMarkFolderList.get(ij) +" while >" + ij);
                    int ijj = 0;
                    while (initBookMarkFolderList.size()>ijj){
                        //Log.d("ensaf::::::::", TAG + "> initBookMarkFolderList : " + initBookMarkFolderList.get(ijj) +" while >" + ijj);
                        if (bookMarkFolderList.get(ij).equals(initBookMarkFolderList.get(ijj))){
                            Log.d("ensaf::::::::", TAG + "> initBookMarkFolderList : " + initBookMarkFolderList.get(ijj) +" > " + ijj +
                                    " Equal  bookMarkFolderList : " + bookMarkFolderList.get(ij) + " > " + ij);
                            listView.setItemChecked(ij,true);
                        }
                        ijj++;
                    }
                    ij++;
                }

                final SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
                builderInner.setView(rowList);
                builderInner.setTitle("BOOKMARKS FOLDERS");

                builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int ii = 0 ;
                        String bookTxtTitle = "";
                        MapPageQuery mapPageQuery = new MapPageQuery();
                        bookMarkSelectedList.clear();
                        while (ii < sparseBooleanArray.size()) {
                            if (sparseBooleanArray.valueAt(ii)) {
                                bookMarkSelectedList.add(bookMarkFolderListID.get(sparseBooleanArray.keyAt(ii)));
                                Log.d("ensaf::::::::", TAG + "> bookMarkSelectedList + " + bookMarkFolderListID.get(sparseBooleanArray.keyAt(ii)));
                                bookTxtTitle += mapPageQuery.getBookmarkTypeTitle(bookMarkFolderListID.get(sparseBooleanArray.keyAt(ii))) + ",";
                            }
                            ii++ ;
                        }

                        if (bookMarkSelectedList.size()>0){
                            txt_bottom_book_type.setText(bookTxtTitle);
                            checkBoxBookmark.setChecked(true);
                            txt_bottom_book_type.setEnabled(true);
                            txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                        }else {
                            checkBoxBookmark.setChecked(false);
                            txt_bottom_book_type.setText("* * *");
                            txt_bottom_book_type.setEnabled(false);
                        }
                        Log.d("ensaf::::::::", TAG + "> bookMarkSelectedListSize : "+ bookMarkSelectedList.size());
                    }
                });
                final Dialog dialog = builderInner.create();
                adapter.notifyDataSetChanged();
                dialog.show();
                return false;
            }
        });
        bottom_tend_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MapActivity.this);
                builderInner.setMessage("Wanna DELETE : " + ID_CONS_SELECTED);
                builderInner.setTitle("Are you Sure?");
                builderInner.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isTapOn == 1){
                            deleteCons();
                        }else if (isTapOn == 2){
                            deletePlace();
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
        bottom_sheet_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected){
                    AddReminderDialouge addReminderDialouge = new AddReminderDialouge(context,activity);
                    ///TODO NullException
                    addReminderDialouge.showDialogueADD(ID_CONS_SELECTED);
                } else {
                    Toast.makeText(getApplicationContext(),"SignIn First !"  , Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottom_sheet_add_reminder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TendHistoryDialog tendHistoryDialog = new TendHistoryDialog(context,activity,ID_CONS_SELECTED);
                tendHistoryDialog.showDialogHistory();
                return true;
            }
        });
        bottom_sheet_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected){
                    //TODO Add Attendance
                    //AddReminderDialouge addReminderDialouge = new AddReminderDialouge(context,activity);
                    ///TODO NullException
                    //addReminderDialouge.showDialogueADD(ID_CONS_SELECTED,mLocationOverlay.getMyLocation());
                    DBQuery dbQuery = new DBQuery();
                    final AlertDialog.Builder builderInner = new AlertDialog.Builder(MapActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
                    View view = inflater.inflate(R.layout.attendance_promp,null);
                    ImageView imageViewAtten = view.findViewById(R.id.img_atten_promp);
                    TextView txtAtten = view.findViewById(R.id.txt_atten_promp);
                    int distance = MapUtils.distance(dbQuery.getConsGeoPoint(ID_CONS_SELECTED),mLocationOverlay.getMyLocation());
                    String posBtn = "ok";
                    String Message = "ثبت حضور مقدور نیست.";
                    boolean isInRange = false;
                    if (distance<30){
                        posBtn = "ثبت";
                        Message = "ثبت حضور.";
                        isInRange = true;
                        imageViewAtten.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_event_available_50));
                    }
                    builderInner.setView(view);
                    builderInner.setTitle("فاصله شما : " + distance + " متر                   ");
                    txtAtten.setText(Message);
                    final boolean finalIsInRange = isInRange;
                    builderInner.setPositiveButton(posBtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (finalIsInRange){
                                if (isConnected && ID_CONNECT_Indi1!=null){
                                    Atten atten = new Atten();
                                    AttenRepo attenRepo = new AttenRepo();
                                    atten.setInd1ID(ID_CONNECT_Indi1);
                                    atten.setInd2ID(ID_CONS_SELECTED);
                                    Date c = Calendar.getInstance().getTime();
                                    SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                                    String formattedDate = df.format(c);
                                    atten.setAttenDate(formattedDate);
                                    if (attenRepo.insert(atten)>0){
                                        AlertDialog.Builder builderInnerAtten = new AlertDialog.Builder(MapActivity.this);
                                        builderInnerAtten.setTitle("حضور شما ثبت شد.                       ");
                                        String [] arrOfFomattedDate1 = formattedDate.split(" ",2);
                                        String [] arrOfGreDate1 = arrOfFomattedDate1[0].split("-",3);
                                        builderInnerAtten.setMessage("                  " + getPersianDate(Integer.valueOf(arrOfGreDate1[0]), Integer.valueOf(arrOfGreDate1[1]), Integer.valueOf(arrOfGreDate1[2]))+ "         " + arrOfFomattedDate1[1]);
                                        builderInnerAtten.setPositiveButton("ok",null);
                                        builderInnerAtten.show();
                                        Toast.makeText(getApplicationContext(),"Atten Successfully Inserted"  , Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Atten NOT Inserted !!!"  , Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),"You Are Not Connected !!!"  , Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    });
                    builderInner.show();
                } else {
                    Toast.makeText(getApplicationContext(),"SignIn First !"  , Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottom_sheet_attendance.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                historyList.clear();
                MapPageQuery mapPageQuery = new MapPageQuery();
                Cursor cursorAttenHistory = mapPageQuery.getAtten(ID_CONS_SELECTED);
                if (cursorAttenHistory.moveToFirst()){
                    do {
                        History history =new History();
                        String [] arrOfFomattedDate1 = cursorAttenHistory.getString(1).split(" ",2);
                        String [] arrOfGreDate1 = arrOfFomattedDate1[0].split("-",3);
                        history.setDate(getPersianDate(Integer.valueOf(arrOfGreDate1[0]), Integer.valueOf(arrOfGreDate1[1]), Integer.valueOf(arrOfGreDate1[2]))+ " " + arrOfFomattedDate1[1]);
                        history.setState("ID : " + cursorAttenHistory.getString(0));
                        historyList.add(history);
                    }while (cursorAttenHistory.moveToNext());
                }
                showDialog(MapActivity.this);
                return true;
            }
        });
        bottom_sheet_status_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///TODO organizing to better way
                historyList.clear();
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
                showDialog(MapActivity.this);
            }
        });
        button_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchPageActivity.class);
                intent.putExtra("ID",ID_CONS_SELECTED);
                context.startActivity(intent);
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
                isRatingBottomChange = true;
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
                        fab_map.hide();
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        bottomRVAdapter.notifyDataSetChanged();
                        state = "COLLAPSED";
                        BOTTOM_SHEET_IS_HIDDEN = false;
                        fab_map.hide();
                        break;
                    }
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        state = "HIDDEN";
                        BOTTOM_SHEET_IS_HIDDEN = true;
                        fab_map.show();
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
                        bookMarkSelectedList.clear();
                        initRating = 0;
                        isBookTouch = false;
                        /*List<ConsState> emtyList = new ArrayList<>();
                        viewPager2 = findViewById(R.id.viewPager2);
                        viewPager2.setAdapter(new ViewPagerAdapter2(context, emtyList, viewPager2));*/
                        imgBtnBottom = (ImageButton) findViewById(R.id.imgBtnBottom);
                        imgBtnBottom.setImageDrawable(null);
                        //Log.d("ensaf::::::::", TAG + "BottomSheetBehavior.STATE_HIDDEN isBookTouch is " + isBookTouch);
                        break;
                    }
                    case BottomSheetBehavior.STATE_HALF_EXPANDED: {
                        //customerAdapter.notifyDataSetChanged();
                        bottomRVAdapter.notifyDataSetChanged();
                        fab_map.hide();
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
    private void deletePlace(){
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        IndividualRepo individualRepo = new IndividualRepo();
        GPointRepo gPointRepo = new GPointRepo();
        MapPageQuery mapPageQuery = new MapPageQuery();
        if(mapPageQuery.getGeopIDFromPlace(ID_CONS_SELECTED)!=null){
            if (individualRepo.deleteIndiID(ID_CONS_SELECTED)&&gPointRepo.deleteIDGeop(mapPageQuery.getGeopIDFromPlace(ID_CONS_SELECTED))){
                Toast.makeText(getBaseContext(), "Place Successfully Deleted!!!" , Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void deleteCons(){
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
    }
    private void imageBottomSheet(){
        imgBtnBottom = (ImageButton) findViewById(R.id.imgBtnBottom);
        imgBtnBottom.setImageDrawable(context.getDrawable(R.drawable.home30));
        imgBtnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "imgBtnBottom is Clicked!!! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void bottomSheetEdiButton(){
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        if(isTapOn == 1 ){
            editSelectedCons(true);
        }else if (isTapOn == 2){
            editSelectedCons(false);
        }else if (isTapOn == 0){
            //insertNewPlace();
            inseringNewCons();
        }
        showOnMap();
        MapUtils.hideKeyboard(this);
    }
    private void editSelectedCons(boolean isCons){
        if (!init_bottom_sheet_name.equals(bottom_sheet_name.getText().toString())) {
            Individual individual = new Individual();
            individual.setID_Indi(ID_CONS_SELECTED);
            individual.setIndiName(bottom_sheet_name.getText().toString());
            IndividualRepo individualRepo = new IndividualRepo();
            if (individualRepo.updateName(individual)) {
                Toast.makeText(getBaseContext(), "Individual Name with ID : " + ID_CONS_SELECTED + " Edited ! ", Toast.LENGTH_SHORT).show();
            }
        }
        if (isCons){
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
            //Log.d("ensaf::::::::", TAG + " isBookTouch is " + isBookTouch);
        }/////else if () TODO changing Place Icon
        if (isBookTouch){
            int bk = 0;
            while (bk < bookMarkSelectedList.size()) {

                int ibk = 0;
                boolean isInsert = true;
                while (ibk < initBookMarkFolderListID.size()){
                    if (isInsert && (initBookMarkFolderListID.get(ibk).equals(bookMarkSelectedList.get(bk)))){
                        isInsert = false;
                    }
                    ibk++;
                }
                if (isInsert){
                    BookMark bookMark = new BookMark();
                    bookMark.setIndID(ID_CONS_SELECTED);
                    bookMark.setB_type_id(bookMarkSelectedList.get(bk));
                    BookMarkRepo bookMarkRepo = new BookMarkRepo();
                    Log.d("ensaf::::::::", TAG + "> bookMark "+ bookMarkSelectedList.get(bk) + " is added.");
                    if(bookMarkRepo.insert(bookMark)>0)
                        Toast.makeText(getBaseContext(),  ID_CONS_SELECTED + " isBookMarked ", Toast.LENGTH_SHORT).show();
                }

                bk++;
            }
            /////
            /*


             */
            int ibk2 = 0;
            while (ibk2 < initBookMarkFolderListID.size()) {
                int bk2 = 0;
                boolean isDeleted = true;
                while (bk2 < bookMarkSelectedList.size()){
                    if (isDeleted && (initBookMarkFolderListID.get(ibk2).equals(bookMarkSelectedList.get(bk2)))){
                        isDeleted = false;
                    }
                    bk2++;
                }
                if (isDeleted){
                    BookMarkRepo bookMarkRepo = new BookMarkRepo();
                    bookMarkRepo.delete_indiID_BtypeID(ID_CONS_SELECTED,initBookMarkFolderListID.get(ibk2));
                    Log.d("ensaf::::::::", TAG + "> bookMark "+ initBookMarkFolderListID.get(ibk2) + " is deleted.");
                }
                ibk2++;
            }
        }

        /*if (!initBookedTypeID.equals(bookedTypeID)){
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
        }*/
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
        individual.setIsCons(String.valueOf(DataContract.CONS_UNI_INDI_TYPE_ID));
        individualRepo.insert(individual);
        ID_CONS_SELECTED = individualRepo.lastIndividual();
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
    /*private void insertNewPlace(){
        Individual individual = new Individual();
        IndividualRepo individualRepo = new IndividualRepo();
        individual.setIndiName(bottom_sheet_name.getText().toString());
        individual.setIsCons(String.valueOf(DataContract.PLACE_UNI_INDI_TYPE_ID));
        individualRepo.insert(individual);
        ID_CONS_SELECTED = individualRepo.lastIndividual();
        GPoint gPoint = new GPoint();
        gPoint.setLon(String.valueOf(lon));
        gPoint.setLat(String.valueOf(lat));
        gPoint.setIsSolo("1");
        GPointRepo gPointRepo = new GPointRepo();
        gPointRepo.insert(gPoint);
        Place_Geop place_geop =new Place_Geop();
        place_geop.setIndiID(individualRepo.lastIndividual());
        place_geop.setGeopID(gPointRepo.lastGPoint());
        place_geop.setIconID("0");////TODO change placeIcon
        Place_GeopRepo place_geopRepo = new Place_GeopRepo();        ;
        if (place_geopRepo.insert(place_geop)>0){
            Toast.makeText(getBaseContext(), "New Place ID : " + individualRepo.lastIndividual() + " in " + lat + " & " + lon , Toast.LENGTH_SHORT).show();
        }
        drawerFragmentMap.setCheckBox01(false);
        drawerFragmentMap.setCheckBox02(false);
        drawerFragmentMap.setCheckBox03(false);
        drawerFragmentMap.setCheckBoxBook(false);
    }*/
    public void showDialog(Activity activity){
        dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.history_dialog_recycler);

        RecyclerView recyclerView = dialog.findViewById(R.id.history_recycler);
        TextView txtHistoryName = dialog.findViewById(R.id.txtHistoryName);
        txtHistoryName.setText("Attendance List");
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
                isTapOn = 0 ;
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
                Toast.makeText(getBaseContext(), "longPressHelper", Toast.LENGTH_SHORT).show();
                return false;
            }
        };

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(getBaseContext(),mapEventsReceiver);
        map.getOverlays().add(mapEventsOverlay);
    }
    public void showOnMapCons(Cursor cursor){
        mStartGoalItems.clear();
        map.getOverlays().remove(mOverlay);
        if (cursor.getCount()==0) {
            Toast.makeText(getBaseContext(),"No data in query",Toast.LENGTH_LONG).show();
            txt_consCount.setText("No data in query");
            Log.d("ensaf::::::::", TAG + "> showAllWaypoints > No data in query");
        }else {
            txt_consCount.setText("Cons : " + cursor.getCount());
            while (cursor.moveToNext()) {
                String ID = cursor.getString(0);
                String NAME = cursor.getString(1);
                String DES = cursor.getString(5);
                Double LAT = cursor.getDouble(2);
                Double LON = cursor.getDouble(3);
                int STA = cursor.getInt(4);
                GeoPoint point = new GeoPoint(LAT, LON);
                OverlayItem Item = new OverlayItem(ID, NAME, DES, point);
                if (STA<consStateList.size()){
                    Item.setMarker(map.getContext().getResources().getDrawable(consStateList.get(STA).getDrawable()));
                }else{
                    Item.setMarker(map.getContext().getResources().getDrawable(R.drawable.home30));
                }
                mStartGoalItems.add(Item);
            }/////
        }
        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(mStartGoalItems,this,this);
        map.getOverlays().add(mOverlay);

    }//////end of showOnMapCons
    private void showOnMapPolygon(){
        regioList.clear();
        initPolygonList();
        for (int ii = 0;ii<regioList.size() ;ii++){
            map.getOverlayManager().add(regioList.get(ii));
        }
    }
    private void showOnMapPlace(Cursor cursor){
        PlaceItems.clear();
        map.getOverlays().remove(placeOverlay);
        if (cursor.getCount()==0) {
            Toast.makeText(getBaseContext(),"No place in query",Toast.LENGTH_LONG).show();
            Log.d("ensaf::::::::", TAG + "> showOnMapPlace > No data in query");
        }else {
            //txt_consCount.setText("Cons : " + cursor.getCount());
            while (cursor.moveToNext()) {
                String ID = cursor.getString(0);
                String NAME = cursor.getString(1);
                Double LAT = cursor.getDouble(2);
                Double LON = cursor.getDouble(3);
                int STA = cursor.getInt(4);
                GeoPoint point = new GeoPoint(LAT, LON);
                OverlayItem Item = new OverlayItem(ID, NAME, "Place", point);
                ////TODO change Icon
                Item.setMarker(map.getContext().getResources().getDrawable(R.drawable.home30));
                PlaceItems.add(Item);
            }/////
        }
        placeOverlay = new ItemizedOverlayWithFocus<OverlayItem>(PlaceItems,this,this);
        map.getOverlays().add(placeOverlay);
    }
    private void checkAndroid6 (){
        // check permissions on Android 6 and higher
        mPermissionsGranted = false;
        if (Build.VERSION.SDK_INT >= 23) {
            // check permissions
            Log.d("MainActivity", "Checking permissions...");
            mMissingPermissions = mapPermissionManager.checkPermissions(this);
            mPermissionsGranted = mMissingPermissions.size() == 0;
        } else {
            mPermissionsGranted = true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onStart() {
        super.onStart();
        Log.d("ensaf::::::::", TAG + "> : onStart");
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onResume() {
        super.onResume();
        Log.d("ensaf::::::::", TAG + "> : onResume");
        if(mPermissionsGranted){
            bottomRVAdapter.notifyDataSetChanged();
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        MapUtils.hideKeyboard(this);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);


        Log.d("ensaf::::::::", TAG + "> : onResume : lat :" + preferences.getFloat(INSTANCE_LATITUDE_MAIN_MAP, (float) DEFAULT_LATITUDE));
        Log.d("ensaf::::::::", TAG + "> : onResume : lon :" + preferences.getFloat(INSTANCE_LONGITUDE_MAIN_MAP, (float) DEFAULT_LONGITUDE));
        Log.d("ensaf::::::::", TAG + "> : onResume : zoomLevel :" + preferences.getFloat(INSTANCE_ZOOM_LEVEL_MAIN_MAP, (float) DEFAULT_ZOOM_LEVEL_MAIN_MAP));
        if(map != null) {
            //this will refresh the osmdroid configuration on resuming.
            //if you make changes to the configuration, use
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
            map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
            mController.setZoom(preferences.getFloat(INSTANCE_ZOOM_LEVEL_MAIN_MAP, (float) DEFAULT_ZOOM_LEVEL_MAIN_MAP));
            GeoPoint startPoint = new GeoPoint(preferences.getFloat(INSTANCE_LATITUDE_MAIN_MAP, (float) DEFAULT_LATITUDE)
                    ,preferences.getFloat(INSTANCE_LONGITUDE_MAIN_MAP, (float) DEFAULT_LONGITUDE));
            mController.setCenter(startPoint);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onPause() {
        super.onPause();
        Log.d("ensaf::::::::", TAG + "> : onPause");
        if(map != null) {
            //this will refresh the osmdroid configuration on resuming.
            //if you make changes to the configuration, use
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            //Configuration.getInstance().save(this, prefs);
              //needed for compass, my location overlays, v6.0.0 and up
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI
            editor.putFloat(INSTANCE_LATITUDE_MAIN_MAP, (float) map.getMapCenter().getLatitude());
            editor.putFloat(INSTANCE_LONGITUDE_MAIN_MAP,(float) map.getMapCenter().getLongitude());
            editor.putFloat(INSTANCE_ZOOM_LEVEL_MAIN_MAP, (float) map.getZoomLevelDouble());
            // Commit to storage
            editor.apply();
            map.onPause();
        }

    }
    public void onStop() {
        super.onStop();
        Log.d("ensaf::::::::", TAG + "> : onStop");
    }
    public void onRestart() {
        super.onRestart();
        //customerAdapter.notifyDataSetChanged();
        bottomRVAdapter.notifyDataSetChanged();
        MapUtils.hideKeyboard(this);
    }
    public void onDestroy() {
        super.onDestroy();
        Log.d("ensaf::::::::", TAG + "> : onDestroy");
    }
    ///####permission Staff
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
        ID_CONS_SELECTED = item.getUid();
        button_edit.setText("Edit");
        /////////
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String formattedDate = df.format(c);
        statusdate = formattedDate;

        MapPageQuery mapPageQuery = new MapPageQuery();

        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //Toast.makeText(getBaseContext(), "The Item ID is : " + item.getPoint().getLatitude(), Toast.LENGTH_SHORT).show();
        Cursor cursor = mapPageQuery.singleTapOnConsIndFirst(ID_CONS_SELECTED);
        Cursor cursorPlace = mapPageQuery.singleTapOnPlaceIndFirst(ID_CONS_SELECTED);
        Cursor cursor1 = mapPageQuery.singleTapOnConsIndSecond(ID_CONS_SELECTED);
        Cursor cursor2 = mapPageQuery.singleTapOnConsIndThirdBook(ID_CONS_SELECTED);

        if (cursor.moveToFirst()) {
            isTapOn = 1;
            viewPager();
            bottom_sheet_name.setText(cursor.getString(1));
            init_bottom_sheet_name = cursor.getString(1);
            viewPager2.setCurrentItem(cursor.getInt(4));
            Toast.makeText(getBaseContext(), "viewPager2 setCurrentItem : " + String.valueOf(cursor.getInt(4)), Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "consStateList.size() : " + String.valueOf(consStateList.size()), Toast.LENGTH_SHORT).show();
            inisatatus =cursor.getInt(4);
            String [] arrOfFomattedDate = cursor.getString(5).split(" ",2);
            String [] arrOfGreDate = arrOfFomattedDate[0].split("-",3);
            if (cursor.getInt(4)<consStateList.size()){
                bottom_sheet_status_data.setText("آخرین بروز رسانی : " + getPersianDate(Integer.valueOf(arrOfGreDate[0]), Integer.valueOf(arrOfGreDate[1]), Integer.valueOf(arrOfGreDate[2]))+ " " + arrOfFomattedDate[1]);
            } else {
                bottom_sheet_status_data.setText("Last UpDate : " + cursor.getString(5));
            }

            if (cursor.getString(6)!=null){
                ratingBottom.setRating(cursor.getFloat(6));
                initRating = cursor.getFloat(6);
            }else {
                ratingBottom.setRating(0);
                initRating = 0;
            }
        }else if (cursorPlace.moveToFirst()){//TODO this is for test
            Toast.makeText(getBaseContext(), "This is Place", Toast.LENGTH_LONG).show();
            isTapOn = 2;
            imageBottomSheet();
            bottom_sheet_name.setText(cursorPlace.getString(1));
            init_bottom_sheet_name = cursorPlace.getString(1);
            bottom_sheet_status_data.setText("Place ID : " + cursorPlace.getString(0));
            if (cursorPlace.getString(5)!=null){
                ratingBottom.setRating(cursorPlace.getFloat(5));
                initRating = cursorPlace.getFloat(5);
            }else {
                ratingBottom.setRating(0);
                initRating = 0;
            }
        }else {
            bottom_sheet_name.setText("Error !! !! ");
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


        if (cursor2.moveToFirst()){
            initBookMarkFolderListID.clear();
            initBookMarkFolderList.clear();
            String bookmarkTitle = "";
            do {
                initBookMarkFolderListID.add(cursor2.getString(0));
                initBookMarkFolderList.add(cursor2.getString(1));
                Log.d("ensaf::::::::", TAG + "> initBookMarkFolderListID + "+ cursor2.getString(0));
                bookmarkTitle += cursor2.getString(1);
            }while (cursor2.moveToNext());
            checkBoxBookmark.setChecked(true);
            isBookTouch = false;
            //Log.d("ensaf::::::::", TAG + " onItemSingleTapUp isBookTouch is " + isBookTouch);
            txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
            txt_bottom_book_type.setText(bookmarkTitle);
        }else {
            checkBoxBookmark.setChecked(false);
            txt_bottom_book_type.setEnabled(false);
            txt_bottom_book_type.setText("* * *");
            txt_bottom_book_type.setTextColor(ContextCompat.getColor(context,R.color.darkGray));
            initBookMarkFolderListID.clear();
            initBookMarkFolderList.clear();
        }
        mController.animateTo(item.getPoint());
        return false;
    }
    @Override
    public boolean onItemLongPress(int index, OverlayItem item) {

        return false;
    }

}
