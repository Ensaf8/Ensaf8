package com.parandak.ensaf8.fullScreenDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.tendHistoryDialog.HistoryTend;

import java.util.ArrayList;
import java.util.List;

public class HistoryTendAdapter extends RecyclerView.Adapter<HistoryTendAdapter.Holder> {
    private LayoutInflater inflater;
    private List<HistoryTend> historyTendList = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public HistoryTendAdapter(Context context,List<HistoryTend> historyTendList){
        this.historyTendList = historyTendList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_row_tend_list,parent,false);
        Holder holder = new Holder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        HistoryTend historyTend = new HistoryTend();
        historyTend = historyTendList.get(position);
        holder.txtHistoryTendTitle.setText(historyTend.getTendTitle());
        holder.txtHistoryTendDate.setText(historyTend.getTendDate());
    }

    @Override
    public int getItemCount() {
        return historyTendList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txtHistoryTendTitle,txtHistoryTendDate;
        public Holder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            txtHistoryTendTitle = (TextView) itemView.findViewById(R.id.txtHistoryTendTitle);
            txtHistoryTendDate = (TextView) itemView.findViewById(R.id.txtHistoryTendDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
