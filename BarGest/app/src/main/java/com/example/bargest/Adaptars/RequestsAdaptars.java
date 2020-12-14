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
import com.example.bargest.Models.views.ListRequests;
import com.example.bargest.R;

import java.util.ArrayList;

public class RequestsAdaptars extends RecyclerView.Adapter<RequestsAdaptars.MyViewHolder> {

    private static Context context;
    private static ArrayList<ListRequests> data;

    public RequestsAdaptars(Context rcontext, ArrayList<ListRequests> objetcs){
        context=rcontext;
        data=objetcs;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_list_request,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.Tablenumber.setText("Mesa "+ String.valueOf(data.get(position).getTable_number()));
        holder.RequestNumber.setText("#"+String.valueOf(data.get(position).getId()));
        switch (data.get(position).getStatus()){

            case 0:
                //Requested
                holder.card.setBackgroundColor(Color.parseColor("#F5C6CB"));
                break;
            case 1:
                //Cooking
                holder.card.setBackgroundColor(Color.parseColor("#FFEEBA"));
                break;
            case 2:
                //Ready
                holder.card.setBackgroundColor(Color.parseColor("#C3E6CB"));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Tablenumber;
        TextView RequestNumber;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tablenumber = itemView.findViewById(R.id.TVListRequestTableNumber);
            card = itemView.findViewById(R.id.CardViewImteRequest);
            RequestNumber = itemView.findViewById(R.id.TVRequestNumber);

        }
    }
}
