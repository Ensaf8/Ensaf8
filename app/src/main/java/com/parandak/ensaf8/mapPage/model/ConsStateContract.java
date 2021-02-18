package com.parandak.ensaf8.mapPage.model;

import com.parandak.ensaf8.R;

import java.util.ArrayList;
import java.util.List;

public class ConsStateContract {
    private List<ConsState> consStateList = new ArrayList<>();
    public ConsStateContract(){
        ConsState consState;
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue1);
        consState.setState("گود برداری");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue2);
        consState.setState("شروع اسکلت");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue22);
        consState.setState("اجرای اسکلت");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mblue3);
        consState.setState("اتمام اسکلت");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mgreen4);
        consState.setState("شروع دیوارچینی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mgreen5);
        consState.setState("دیوارچینی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_myellow6);
        consState.setState("اتمام دیوارچینی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_myellow7);
        consState.setState("شروع تاسیسات");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_morange8);
        consState.setState("اجرای تاسیسات");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_morange9);
        consState.setState("اتمام تاسیسات");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mred10);
        consState.setState("دکوراسیون داخلی");
        consStateList.add(consState);
        consState = new ConsState();
        consState.setDrawable(R.drawable.ic_mred11);
        consState.setState("اتمام کار");
        consStateList.add(consState);
    }

    public List<ConsState> getConsStateList(){
        return consStateList;
    }
}
