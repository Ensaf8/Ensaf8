package com.parandak.ensaf8.fullScreenDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;

import java.util.List;

public class CoopFirstPartAdapter extends RecyclerView.Adapter<CoopFirstPartAdapter.MyViewHolder> {

    private  List<CoopFirthPart> coopFirthPartList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView firstPart,title;

        public MyViewHolder(View view,final OnItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.coop_title);
            firstPart = (TextView)view.findViewById(R.id.firstPart);
            view.setOnClickListener(new View.OnClickListener() {
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


    public CoopFirstPartAdapter(List<CoopFirthPart> coopFirthPartList) {
        this.coopFirthPartList = coopFirthPartList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coop_full_list_row, parent, false);
        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CoopFirthPart coopFirthPart = new CoopFirthPart();
        coopFirthPart = coopFirthPartList.get(position);
        holder.firstPart.setText(coopFirthPart.getIndiName());
        holder.title.setText(coopFirthPart.getTitle());
    }

    @Override
    public int getItemCount() {
        return coopFirthPartList.size();
    }
}
