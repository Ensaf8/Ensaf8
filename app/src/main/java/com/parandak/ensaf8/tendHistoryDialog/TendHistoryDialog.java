package com.parandak.ensaf8.tendHistoryDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.dateAndReminder.AddReminderDialouge;
import com.parandak.ensaf8.fullScreenDialog.HistoryTendAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.parandak.ensaf8.dateAndReminder.PersianCalendarAli.getPersianDate;

public class TendHistoryDialog {
    Activity mactivity;
    Context mcontext;
    String CUS_ID;
    List<HistoryTend> historyTendList = new ArrayList<>();
    TendHistoryQuery tendHistoryQuery;
    public TendHistoryDialog(Context context,Activity activity, String CUS_ID){
        this.mactivity = activity;
        this.CUS_ID = CUS_ID;
        this.mcontext = context;
    }
    public void initHistoryTendList(){
        tendHistoryQuery = new TendHistoryQuery();
        Cursor cursor = tendHistoryQuery.getHistoryTend(CUS_ID);
        HistoryTend historyTend ;
        if (cursor.moveToFirst()){
            do{
                historyTend = new HistoryTend();
                historyTend.setTendID(cursor.getString(0));
                historyTend.setTendTitle(cursor.getString(1));
                String [] arrOfFomattedDate1 = cursor.getString(2).split(" ",2);
                String [] arrOfGreDate1 = arrOfFomattedDate1[0].split("-",3);
                historyTend.setTendDate(getPersianDate(Integer.valueOf(arrOfGreDate1[0]), Integer.valueOf(arrOfGreDate1[1]), Integer.valueOf(arrOfGreDate1[2]))+ " " + arrOfFomattedDate1[1]);
                historyTendList.add(historyTend);
            }while (cursor.moveToNext());
        }
    }
    public void showDialogHistory(){
        initHistoryTendList();
        tendHistoryQuery = new TendHistoryQuery();
        Dialog dialog = new Dialog(mactivity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.history_dialo_tend_recycler);

        TextView txtHistoryTitleName = dialog.findViewById(R.id.txtHistoryNameTend);
        txtHistoryTitleName.setText(tendHistoryQuery.getIndiName(CUS_ID));
        RecyclerView recyclerView = dialog.findViewById(R.id.history_tend_recycler);
        HistoryTendAdapter historyTendAdapter = new HistoryTendAdapter(mcontext,historyTendList);
        recyclerView.setAdapter(historyTendAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false));
        historyTendAdapter.setOnItemClickListener(new HistoryTendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AddReminderDialouge addReminderDialouge = new AddReminderDialouge(mcontext,mactivity);
                addReminderDialouge.showDialogueEdit(historyTendList.get(position).tendID,tendHistoryQuery.getIndiName(CUS_ID));
            }
        });

        dialog.show();
    }
}







