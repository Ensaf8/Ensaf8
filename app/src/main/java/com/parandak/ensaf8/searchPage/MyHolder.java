package com.parandak.ensaf8.searchPage;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.parandak.ensaf8.R;


/**
 * Created by Oclemmy on 5/2/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class MyHolder extends RecyclerView.ViewHolder {

    TextView nameTxt,titleTxt;

    public MyHolder(View itemView) {
        super(itemView);
        this.nameTxt= (TextView) itemView.findViewById(R.id.customerName);
        this.titleTxt = (TextView) itemView.findViewById(R.id.customerTitle);
    }
}
