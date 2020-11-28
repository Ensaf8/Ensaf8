package com.parandak.ensaf8.bookMarkPage.rv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;

import java.util.ArrayList;
import java.util.List;

public class BookMarkFolderAdapter extends RecyclerView.Adapter<BookMarkFolderAdapter.MyViewHolder> {
    private List<String> title_folder_list = new ArrayList<>();
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_book_mark_folder;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            txt_book_mark_folder = (TextView) view.findViewById(R.id.txt_book_mark_folder);
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

    public BookMarkFolderAdapter(List<String> title_folder_list){
        this.title_folder_list = title_folder_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_mark_folder, parent, false);
        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_book_mark_folder.setText(title_folder_list.get(position));
    }

    @Override
    public int getItemCount() {
        return title_folder_list.size();
    }


}
