package com.example.bargest.Adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bargest.Models.Bills;
import com.example.bargest.R;

import java.util.ArrayList;

public class DivideAdaptar extends RecyclerView.Adapter<DivideAdaptar.MyViewHolder>{

    private Context context;
    private ArrayList<Bills> data;

    public DivideAdaptar(Context context, ArrayList<Bills> objetcs) {
        this.context=context;
        this.data=objetcs;
    }

    @NonNull
    @Override
    public DivideAdaptar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_divide_bill,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DivideAdaptar.MyViewHolder holder, final int position) {
        holder.productName.setText(data.get(position).getProductName());
        holder.productQuantity.setText("???");
        holder.Price.setText("???");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productQuantity;
        TextView Price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.TVDivideBillsNameProduct);
            productQuantity = itemView.findViewById(R.id.TVDivideBillsQuantity)   ;
            Price = itemView.findViewById(R.id.TVTDivideBillsProductPrice);

        }
    }
}

