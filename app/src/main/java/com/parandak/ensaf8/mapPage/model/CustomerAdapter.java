package com.parandak.ensaf8.mapPage.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.parandak.ensaf8.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> implements Filterable {

    private List<Customer> customerList;
    List<Customer> customerListFiltered;
    NewFilter mfilter;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView CustomerName,CustomerPosition;

        public MyViewHolder(@NonNull View view) {
            super(view);
            CustomerName = (TextView) view.findViewById(R.id.customerName);
            CustomerPosition = (TextView) view.findViewById(R.id.customerTitle);

        }
    }

    public CustomerAdapter(List<Customer> customerList){
        this.customerList = customerList;
        this.customerListFiltered = customerList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Customer customer =customerListFiltered.get(position);
        holder.CustomerName.setText(customer.getName());
        holder.CustomerPosition.setText(customer.getPosition());


    }

    @Override
    public int getItemCount() {
        return customerListFiltered.size();
    }
    @Override
    public Filter getFilter(){
        return mfilter;
    }

    
    public class NewFilter extends Filter {
        public CustomerAdapter mAdapter;
        public NewFilter(CustomerAdapter mAdapter){
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            customerList.clear();
            final FilterResults results = new FilterResults();
            if(constraint.length() == 0){
                customerListFiltered.addAll(customerList);
            }else{
                List<Customer> customerFiltered = new ArrayList<>();
                for(Customer row : customerList){
                    if(row.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        customerFiltered.add(row);
                    }
                }
                customerListFiltered.addAll(customerFiltered);
            }
            results.values = customerListFiltered;
            results.count = customerListFiltered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }
}
