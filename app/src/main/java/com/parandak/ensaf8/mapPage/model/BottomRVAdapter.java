package com.parandak.ensaf8.mapPage.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;

import java.util.List;

public class BottomRVAdapter extends RecyclerView.Adapter<BottomRVAdapter.MyViewHolder> {
    private List<Customer> customerList;
    private OnItemClickListener mListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public BottomRVAdapter(Context context, List<Customer> customerList){
        this.customerList = customerList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item4_viewpager,parent,false);
        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Customer customer =customerList.get(position);
        holder.txt_item04_consname.setText(customer.getName());
        holder.txt_item04_conposi.setText(customer.getPosition());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_item04_consname, txt_item04_conposi;

        public MyViewHolder(@NonNull View view,final OnItemClickListener listener) {
            super(view);
            txt_item04_consname = (TextView) view.findViewById(R.id.txt_item04_consname);
            txt_item04_conposi = (TextView) view.findViewById(R.id.txt_item04_conposi);

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
