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

public class NewRequestAdaptar  extends RecyclerView.Adapter<NewRequestAdaptar.MyViewHolder>{

    private Context context;
    private ArrayList<Bills> data;
    public NewRequestAdaptar(Context context, ArrayList<Bills> objetcs) {
        //TODO:Create class Product and change de last parameter
        this.context=context;
        this.data=objetcs;

    }

    @NonNull
    @Override
    public NewRequestAdaptar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_new_request,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.productName.setText(data.get(position).getProductName());
        holder.productQuantity.setText(String.valueOf("???"));
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Remove quantity
               /* int quantidade = data.get(position).getQuantidade();
                if(quantidade>0){
                    data.get(position).setQuantidade(quantidade-1);
                    holder.productQuantity.setText(String.valueOf(data.get(position).getQuantidade()));
                }*/

            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*data.get(position).setQuantidade(data.get(position).getQuantidade()+1);
                holder.productQuantity.setText(String.valueOf(data.get(position).getQuantidade()));*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productQuantity;
        ImageView btnRemove;
        ImageView btnAdd;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.TVProductNewRequest);
            productQuantity = itemView.findViewById(R.id.TVQuantityNewRequest);
            btnRemove = itemView.findViewById(R.id.BtnRemoveNewRequest);
            btnAdd = itemView.findViewById(R.id.BtnAddNewRequest);

        }
    }
}
