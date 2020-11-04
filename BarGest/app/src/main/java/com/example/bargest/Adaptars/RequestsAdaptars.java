package com.example.bargest.Adaptars;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bargest.Models.Requests;
import com.example.bargest.R;

import java.util.ArrayList;

public class RequestsAdaptars extends RecyclerView.Adapter<RequestsAdaptars.MyViewHolder> {

    private Context context;
    private ArrayList<Requests> data;

    public RequestsAdaptars(Context context, ArrayList<Requests> objetcs){
        this.context=context;
        this.data=objetcs;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_list_request,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
        holder.Tablenumber.setText("Mesa "+ String.valueOf( data.get(position).getTable()));
        switch (data.get(position).getStatus()){
            case 0:
                //Recipt request
                holder.card.setBackgroundColor(Color.parseColor("#5AD985"));
                break;
            case 1:
                //Recipt request
                holder.card.setBackgroundColor(Color.parseColor("#DEF273"));
                break;
            case 2:
                //Recipt request
                holder.card.setBackgroundColor(Color.parseColor("#FC7B7B"));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Tablenumber;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tablenumber = itemView.findViewById(R.id.TVListRequestTableNumber);
            card = itemView.findViewById(R.id.CardViewImteRequest);

        }
    }
}
