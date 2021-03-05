package com.parandak.ensaf8.searchPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parandak.ensaf8.R;
import com.parandak.ensaf8.app.BaseActivity;
import com.parandak.ensaf8.dataBase.model_Indivi.CusAccount;
import com.parandak.ensaf8.dataBase.model_Indivi.Indi_Coop;
import com.parandak.ensaf8.dataBase.model_Indivi.Individual;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.CusAccountRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.Indi_CoopRepo;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.IndividualRepo;
import com.parandak.ensaf8.homePage.MyDividerItemDecoration;
import com.parandak.ensaf8.fullScreenDialog.FullDialog;

import java.util.ArrayList;

import static com.parandak.ensaf8.homePage.HomePageActivity.ID_CONNECT_Indi1;

public class SearchPageActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    @Override
    public int getContentViewId() {
        return R.layout.activity_search_page;
    }
    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_search;
    }
    @Override
    public int getItemIdBefore() {
        return itemIdBefore;
    }
    int itemIdBefore;
    Context context = this;
    private FloatingActionButton fab;
    EditText editText_customer_name,editText_customer_phone,editText_customer_position;
    //EditText editText_search;
    SearchView sv;
    String CONS_ID ,CUS_ID,CUS_ID_SELECTED;
    //Recycler View
    //CustomerAdapter customerAdapter;
    ArrayList<SearchPageModel> searchPageModelList =new ArrayList<>();
    RecyclerView recyclerView;
    SearchPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_page);
        Toast.makeText(getBaseContext(), TAG + " : onCreate" , Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarsearch);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        CONS_ID = intent.getStringExtra("ID");
        itemIdBefore = intent.getIntExtra("itemIdBefore",R.id.navigation_home);
        String txtSet = "Construction ID : " + CONS_ID;

        recycleView();
        floatinB();
        getCustomer();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getBaseContext(), TAG + " : onStart" , Toast.LENGTH_SHORT).show();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getBaseContext(), TAG + " : onPause" , Toast.LENGTH_SHORT).show();
    }

    private void positionPromts(View view, int position){
        final SearchPageModel searchPageModel01 = searchPageModelList.get(position);
        if (CONS_ID != null){
            if (CONS_ID == "follow"){
                Toast.makeText(getBaseContext(),"New Follow For "+ searchPageModel01.getId()+ " Added ! by " + ID_CONNECT_Indi1,Toast.LENGTH_SHORT).show();
            }else {
                LayoutInflater po = LayoutInflater.from(context);
                View promptsViewPosition = po.inflate(R.layout.position,null);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(this);
                builderInner.setView(promptsViewPosition);
                editText_customer_position = (EditText)promptsViewPosition.findViewById(R.id.ediPosition);
                builderInner.setMessage("POSITION");
                builderInner.setTitle("chooose position?");
                builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Indi_Coop indi_coop = new Indi_Coop();
                        Indi_CoopRepo indi_coopRepo = new Indi_CoopRepo();
                        indi_coop.setFirstPartID(CONS_ID);
                        indi_coop.setSecondPartID(searchPageModel01.getId());
                        indi_coop.setTitle(editText_customer_position.getText().toString());
                        indi_coopRepo.insert(indi_coop);
                        finish();
                    }
                });
                builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
                Toast.makeText(getBaseContext(),"New CusAccount Added !" ,Toast.LENGTH_SHORT).show();
            }
        }else {
            String Cus_Id = searchPageModel01.getId();
            Toast.makeText(getApplicationContext(), searchPageModel01.getId()+" "+ searchPageModel01.getName() + " " + searchPageModel01.getTitle(), Toast.LENGTH_SHORT).show();
            FullDialog.display(getSupportFragmentManager(),Cus_Id);
        }

    }

    private void recycleView(){
        recyclerView = findViewById(R.id.rv);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sv= (SearchView) findViewById(R.id.sv);
        //customerAdapter = new CustomerAdapter(searchPageModelList);
        adapter = new SearchPageAdapter(context, searchPageModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(context, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final SearchPageModel searchPageModel01 = searchPageModelList.get(position);
                positionPromts(view,position);
                Toast.makeText(getBaseContext(), searchPageModel01.getId() + " AND " + searchPageModel01.getName() ,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getCustomer(newText);
                return false;
            }
        });
    }

    private void getCustomer(String searchTerm){
        searchPageModelList.clear();
        SearchPageQuery searchPageQuery = new SearchPageQuery();
        Cursor cursor =searchPageQuery.getAllCustomerSearchFilter03(searchTerm);
        while (cursor.moveToNext()){
            SearchPageModel searchPageModel = new SearchPageModel();
            String CUS_ID = cursor.getString(0);
            searchPageModel.setId(CUS_ID);
            searchPageModel.setName(cursor.getString(1));
            searchPageModel.setFirstPart(cursor.getString(2));
            searchPageModel.setTitle(cursor.getString(3));
            searchPageModelList.add(searchPageModel);
        }
        recyclerView.setAdapter(adapter);
    }

    private void getCustomer(){
        searchPageModelList.clear();
        SearchPageQuery searchPageQuery = new SearchPageQuery();
        Cursor cursor =searchPageQuery.getAllCustomerSearchFilter03();
        while (cursor.moveToNext()){
            SearchPageModel searchPageModel = new SearchPageModel();
            String CUS_ID = cursor.getString(0);
            searchPageModel.setId(CUS_ID);
            searchPageModel.setName(cursor.getString(1));
            searchPageModel.setFirstPart(cursor.getString(2));
            searchPageModel.setTitle(cursor.getString(3));
            searchPageModelList.add(searchPageModel);
        }
        recyclerView.setAdapter(adapter);
    }


    private void floatinB(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setAlpha(0.50f);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                editText_customer_name = (EditText) promptsView.findViewById(R.id.ediCustomerName);
                editText_customer_phone = (EditText)promptsView.findViewById(R.id.ediCustomerPhone);
                alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Individual individual = new Individual();
                        CusAccount cusAccount = new CusAccount();
                        IndividualRepo individualRepo = new IndividualRepo();
                        CusAccountRepo cusAccountRepo = new CusAccountRepo();
                        individual.setIndiName(editText_customer_name.getText().toString());
                        individual.setIsCons("0");
                        individualRepo.insert(individual);
                        cusAccount.setIndID(individualRepo.lastIndividual());
                        cusAccount.setAccountName(null);
                        cusAccount.setPassWord(null);
                        cusAccount.setIsAct("0");
                        cusAccountRepo.insert(cusAccount);
                        getCustomer();
                        //adapter2.notifyDataSetChanged();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                //
                alertDialog.show();
            }
        });
    }


}
