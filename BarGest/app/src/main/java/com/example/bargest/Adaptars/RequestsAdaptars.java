package com.example.bargest.Adaptars;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bargest.Models.Requests;
import com.example.bargest.Models.views.ListRequests;
import com.example.bargest.R;
import com.example.bargest.Views.Fragments.EditRequestFragment;
import com.example.bargest.Views.Fragments.ListRequestProductFragment;

import java.util.ArrayList;

public class RequestsAdaptars extends RecyclerView.Adapter<RequestsAdaptars.MyViewHolder> {

    private static Context context;
    private static ArrayList<ListRequests> data;
    private FragmentManager fragment;

    public RequestsAdaptars(Context rcontext, ArrayList<ListRequests> objetcs, FragmentManager fragment){
        context=rcontext;
        data=objetcs;
        this.fragment=fragment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_list_request,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,final int position) {
        holder.Tablenumber.setText("Mesa "+ String.valueOf(data.get(position).getTable_number()));
        holder.RequestNumber.setText("#"+String.valueOf(data.get(position).getId()));
        switch (data.get(position).getStatus()){

            case 0:
                //Requested
                holder.card.setBackgroundColor(Color.parseColor("#F5C6CB"));
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("request_id", data.get(position).getId());
                        EditRequestFragment editRequestFragment = new EditRequestFragment();
                        editRequestFragment.setArguments(bundle);
                        fragment.beginTransaction().replace(R.id.container, editRequestFragment).addToBackStack("Requests").commit();
                    }
                });
                break;
            case 1:
                //Cooking
                holder.card.setBackgroundColor(Color.parseColor("#FFEEBA"));
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("request_id", data.get(position).getId());
                        ListRequestProductFragment listRequestProductFragment = new ListRequestProductFragment();
                        listRequestProductFragment.setArguments(bundle);
                        fragment.beginTransaction().replace(R.id.container, listRequestProductFragment).addToBackStack("Requests").commit();
                    }
                });
                break;
            case 2:
                //Ready
                holder.card.setBackgroundColor(Color.parseColor("#C3E6CB"));
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("request_id", data.get(position).getId());
                        ListRequestProductFragment listRequestProductFragment = new ListRequestProductFragment();
                        listRequestProductFragment.setArguments(bundle);
                        fragment.beginTransaction().replace(R.id.container, listRequestProductFragment).addToBackStack("Requests").commit();
                    }
                });
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
