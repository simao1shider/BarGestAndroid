package com.example.bargest.Adaptars;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.bargest.Models.Tables;
import com.example.bargest.R;

import java.util.ArrayList;
import java.util.List;

public class TablesAdapters  extends ArrayAdapter<Tables> {

    private Context context;
    private  int resource;
    public ArrayList<Tables> originalData;
    private ArrayList<Tables> FilteredResult;

    public TablesAdapters(@NonNull Context context, int resource, @NonNull ArrayList<Tables> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.originalData=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(resource,parent,false);

        TextView numberText = convertView.findViewById(R.id.TVtableNumber);

        TextView cost = convertView.findViewById(R.id.TVTBillsProductPrice);

        CardView card = convertView.findViewById(R.id.CardListTables);

        if(getItem(position).getStatus() == 1){
            //Table ocupped
            card.setBackgroundColor(Color.parseColor("#FFEEBA"));
            cost.setText(String.valueOf(getItem(position).getTotal())+"€");
        }
        else{
            //Table free
            card.setBackgroundColor(Color.parseColor("#C3E6CB"));
            cost.setVisibility(View.GONE);
        }

        numberText.setText(String.valueOf(getItem(position).getNumber()));



        return convertView;

    }
}
