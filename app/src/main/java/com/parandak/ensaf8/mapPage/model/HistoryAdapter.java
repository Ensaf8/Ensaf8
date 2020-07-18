package com.parandak.ensaf8.mapPage.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {
    private LayoutInflater inflater;
    private List<History> historyList = new ArrayList();
    public HistoryAdapter(Context context, List<History> historyList){
        this.historyList = historyList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_row_list,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        History history = new History();
        history = historyList.get(position);
        holder.txthistorystate.setText(history.getState());
        holder.txthistorydate.setText(history.getDate());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txthistorydate,txthistorystate;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txthistorydate = (TextView) itemView.findViewById(R.id.txtHistoryDate);
            txthistorystate = (TextView) itemView.findViewById(R.id.txtHistoryState);

        }
    }
}
