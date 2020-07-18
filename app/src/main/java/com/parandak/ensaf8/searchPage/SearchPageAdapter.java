package com.parandak.ensaf8.searchPage;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.parandak.ensaf8.R;

import java.util.ArrayList;

/**
 * Created by Oclemmy on 5/2/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class SearchPageAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<SearchPageModel> searchPageModels;

    public SearchPageAdapter(Context c, ArrayList<SearchPageModel> searchPageModels) {
        this.c = c;
        this.searchPageModels = searchPageModels;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_row,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTxt.setText(searchPageModels.get(position).getName());
        holder.titleTxt.setText(searchPageModels.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return searchPageModels.size();
    }
}
