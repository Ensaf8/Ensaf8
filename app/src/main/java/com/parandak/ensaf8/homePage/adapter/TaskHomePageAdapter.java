package com.parandak.ensaf8.homePage.adapter;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.parandak.ensaf8.R;
import com.parandak.ensaf8.homePage.model.TaskHomePage;

import java.util.List;

public class TaskHomePageAdapter extends RecyclerView.Adapter<TaskHomePageAdapter.MyViewHolder> {

    private List <TaskHomePage> taskHomePageList;
    private OnItemClickListener mListener;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onCheckChange(int position);
        void onNameCick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customeName,title,main,date;
        public CheckBox checkBox;
        public MyViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            customeName = (TextView)itemView.findViewById(R.id.txt_tasks_customer_name);
            title = (TextView)itemView.findViewById(R.id.CusActReTitle);
            main = (TextView)itemView.findViewById(R.id.CusActReMain);
            date = (TextView)itemView.findViewById(R.id.CusActReDate);
            checkBox = (CheckBox)itemView.findViewById(R.id.CusActReCheckBox);

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

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onCheckChange(position);
                        }
                    }
                }
            });

            customeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onNameCick(position);
                        }
                    }
                }
            });
        }
    }

    public TaskHomePageAdapter(Context context,List<TaskHomePage> taskHomePageList){
        this.taskHomePageList = taskHomePageList;
        this.context = context;
    }


    @Override
    public TaskHomePageAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_list_row, viewGroup, false);
        TaskHomePageAdapter.MyViewHolder tHPA =new TaskHomePageAdapter.MyViewHolder(itemView,mListener);
        return tHPA;
    }

    @Override
    public void onBindViewHolder(TaskHomePageAdapter.MyViewHolder myViewHolder, int i) {
        TaskHomePage taskHomePage =taskHomePageList.get(i);
        myViewHolder.customeName.setText(taskHomePage.getCustomerName());
        myViewHolder.title.setText(taskHomePage.getTitle());
        myViewHolder.main.setText(taskHomePage.getMain());
        myViewHolder.date.setText(taskHomePage.getDateTime());
        myViewHolder.checkBox.setChecked(taskHomePage.isChecked());

    }

    @Override
    public int getItemCount() {
        return taskHomePageList.size();
    }


}
