package com.parandak.ensaf8.mapPage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.parandak.ensaf8.R;
import com.parandak.ensaf8.mapPage.model.ConsState;

import java.util.ArrayList;
import java.util.List;


/////JournalDev.com

public class ViewPagerAdapter2 extends RecyclerView.Adapter<ViewPagerAdapter2.ViewHolder> {

    private ArrayList<Drawable> drawableList = new ArrayList<>();
    private List<ConsState> mData;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;


    public ViewPagerAdapter2(Context context, List<ConsState> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewPagerAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = mInflater.inflate(R.layout.waypoint_viewpager, parent, false);
        View view = mInflater.inflate(R.layout.item2_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter2.ViewHolder holder, int position) {
        String animal = mData.get(position).getState();
        holder.myTextView.setText(animal);
        holder.imageView.setImageDrawable(drawableList.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageView imageView;
        LinearLayout linearLayout;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview2);
            myTextView = itemView.findViewById(R.id.tvTitle);
            linearLayout = itemView.findViewById(R.id.container);
            for (int i=0;i<mData.size();i++){
                drawableList.add(itemView.getContext().getResources().getDrawable(mData.get(i).getDrawable()));
            }

        }
    }

}
