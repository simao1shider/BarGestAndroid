package com.example.bargest.Adaptars;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;

import java.util.ArrayList;

public class BillsDetailsAdaptar extends ArrayAdapter<ProductsToBePaid> {

    private Context context;
    private int resource;
    private ArrayList<ProductsToBePaid> originalData;

    public BillsDetailsAdaptar(@NonNull Context context, int resource, @NonNull ArrayList<ProductsToBePaid> objects) {
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

        TextView nameProductView = convertView.findViewById(R.id.TVBillsNameProduct);

        TextView productPriceView = convertView.findViewById(R.id.TVTBillsProductPrice);

        TextView productQuatityView = convertView.findViewById(R.id.TVTBillsQuantity);


        nameProductView.setText(getItem(position).getName());
        productPriceView.setText(String.format("%.2f", getItem(position).getPrice()) +"€");
        productQuatityView.setText(String.valueOf(getItem(position).getQuantity()));

        return convertView;
    }
}
