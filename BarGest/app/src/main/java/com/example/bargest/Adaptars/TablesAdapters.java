package com.example.bargest.Adaptars;

import android.content.Context;
import android.graphics.Color;
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

public class TablesAdapters  extends ArrayAdapter<Tables> implements Filterable {

    private Context context;
    private  int resource;
    private ArrayList<Tables> originalData;

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

        if(getItem(position).isStatus()){
            //Table ocupped
            card.setBackgroundColor(Color.parseColor("#EDF8B3"));
        }
        else{
            //Table free
            card.setBackgroundColor(Color.parseColor("#A4EABC"));
        }

        numberText.setText(String.valueOf(getItem(position).getNumber()));
        cost.setText(String.valueOf(getItem(position).getMoney()));


        return convertView;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(constraint == null || constraint.length() == 0)
                {
                    results.values = constraint;
                    results.count = constraint.length();
                }
                else
                {
                    ArrayList<Tables> filterResultsData = new ArrayList<Tables>();

                    for(Tables data : originalData)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                        if(String.valueOf(data.getNumber()).contains(constraint))
                        {
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}
