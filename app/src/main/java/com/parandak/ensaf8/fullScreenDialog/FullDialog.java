package com.parandak.ensaf8.fullScreenDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.dataBase.model_Indivi.PhoneNum;
import com.parandak.ensaf8.dataBase.model_Indivi.repo_Indi.PhoneNumRepo;
import com.parandak.ensaf8.dateAndReminder.AddReminderDialouge;
import com.parandak.ensaf8.tendHistoryDialog.HistoryTend;
import com.parandak.ensaf8.tendHistoryDialog.TendHistoryDialog;

import java.util.ArrayList;
import java.util.List;

import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianDate;
import static com.parandak.ensaf8.homePage.HomePageActivity.isConnected;

public class FullDialog extends DialogFragment {

    public static final String TAG = "full_dialog";
    private Context mcontext;
    private Activity mactivity;
    List<CoopFirthPart> coopFirthPartList = new ArrayList<>();
    private RecyclerView recyclerView;
    CoopFirthPart coopFirthPartSelected = new CoopFirthPart();
    FullDialogQuery fullDialogQuery = new FullDialogQuery();
    private CoopFirstPartAdapter coopFirstPartAdapter;
    String CUS_ID;
    TextView full_txt_name;
    Button full_btn_addTend,full_btn_history,full_btn_call;
    Cursor cursor;

    private Toolbar toolbar;

    FullDialog(String CUS_ID){
        this.CUS_ID = CUS_ID;
    }

    public static FullDialog display(FragmentManager fragmentManager, String CUS_ID) {
        FullDialog fullDialog = new FullDialog(CUS_ID);
        fullDialog.show(fragmentManager, TAG);
        return fullDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.full_dialog, container, false);

        initCusProfile(view);
        initBtnReminder(view);
        initBtnHistory(view);
        initBtnCall(view);
        coopFirstPartRV(view);
        fillCoopFistList();

        toolbar = view.findViewById(R.id.toolbar);
        return view;
    }
    private void initCusProfile(View view){
        full_txt_name = view.findViewById(R.id.full_txt_name);
        full_txt_name.setText(fullDialogQuery.getIndiName(CUS_ID) +" : "+ CUS_ID);
        full_txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Cooperate"  , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initBtnReminder(View view){
        /////############
        full_btn_addTend = view.findViewById(R.id.full_btn_addTend);
        full_btn_addTend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected){
                    AddReminderDialouge addReminderDialouge = new AddReminderDialouge(mcontext,mactivity);
                    addReminderDialouge.showDialogueADD(CUS_ID);
                } else {
                    Toast.makeText(mcontext,"SignIn First !"  , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void initBtnHistory(View view){
        full_btn_history = view.findViewById(R.id.full_btn_history);
        full_btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TendHistoryDialog  tendHistoryDialog = new TendHistoryDialog(mcontext,mactivity,CUS_ID);
                tendHistoryDialog.showDialogHistory();
            }
        });
    }
    private void initBtnCall(View view){
        full_btn_call = view.findViewById(R.id.full_btn_call);
        full_btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullDialogQuery fullDiaCallQuery = new FullDialogQuery();
                Cursor cursor = fullDiaCallQuery.getIndiPhone(CUS_ID);
                if (cursor.moveToFirst()){
                    if (cursor.getCount()==1){
                        Toast.makeText(getContext(),"calling :  " + cursor.getString(1) , Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(),"calling !!!"  , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(),"NO number"  , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mactivity=  activity;
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
        mactivity = null;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullDialog.this.dismiss();
            }
        });
        toolbar.setTitle("Some Title");
        toolbar.inflateMenu(R.menu.example_dialog);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.action_delete:
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                        builderInner.setMessage("DELETE" );
                        builderInner.setTitle("Are you Sure?");
                        builderInner.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"deleteCustomer", Toast.LENGTH_SHORT).show();
                                FullDialog.this.dismiss();
                            }
                        });
                        builderInner.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                        break;
                    case R.id.action_add_phone:
                        //Toast.makeText(getContext(),"adding phone number", Toast.LENGTH_SHORT).show();
                        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
                        View viewAddPhone = layoutInflater.inflate(R.layout.add_phone_num,null);
                        final EditText edi_add_phone = viewAddPhone.findViewById(R.id.edi_add_phone);
                        AlertDialog.Builder builderAddPhone = new AlertDialog.Builder(getContext());
                        builderAddPhone.setMessage("ADDPhoneNumber" );
                        builderAddPhone.setView(viewAddPhone);
                        builderAddPhone.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PhoneNum phoneNum = new PhoneNum();
                                phoneNum.setIndiID(CUS_ID);
                                phoneNum.setNum(edi_add_phone.getText().toString());
                                PhoneNumRepo phoneNumRepo = new PhoneNumRepo();
                                if (phoneNumRepo.insert(phoneNum)>=1){
                                    Toast.makeText(getContext(),"the number is : " +phoneNum.getNum() + " is add to : "
                                            + phoneNum.getIndiID() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builderAddPhone.show();
                        break;
                }
                //FullDialog.this.dismiss();
                return true;
            }
        });
    }
    void coopFirstPartRV(View view){
        recyclerView = view.findViewById(R.id.recycler_view_coop);
        recyclerView.setHasFixedSize(true);
        coopFirstPartAdapter = new CoopFirstPartAdapter(coopFirthPartList);
        //recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep coop_full_list_row_row.xml width to `match_parent`
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep coop_full_list_row.xml.xml width to `wrap_content`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mcontext, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        recyclerView.addItemDecoration(new MyDividerItemDecoration(mcontext, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(coopFirstPartAdapter);
        coopFirstPartAdapter.setOnItemClickListener(new CoopFirstPartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mcontext, "Item clicked !!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fillCoopFistList(){
        FullDialogQuery fullDialogQuery = new FullDialogQuery();
        cursor = fullDialogQuery.getIndiCooperate(CUS_ID);
        Toast.makeText(mcontext,"Cooperate", Toast.LENGTH_SHORT).show();
        coopFirthPartList.clear();
        while (cursor.moveToNext()){
            CoopFirthPart coopFirthPart = new CoopFirthPart();
            coopFirthPart.setID_indi(cursor.getString(0));
            coopFirthPart.setIndiName(cursor.getString(1));
            coopFirthPart.setTitle(cursor.getString(2));
            coopFirthPart.setID_indi_CO(cursor.getString(3));
            coopFirthPartList.add(coopFirthPart);
        }
        coopFirstPartAdapter.notifyDataSetChanged();
    }
}
