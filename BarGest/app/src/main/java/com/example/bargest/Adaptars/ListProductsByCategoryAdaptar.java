package com.example.bargest.Adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

public class ListProductsByCategoryAdaptar extends BaseAdapter {

    ArrayList<Products> products;
    public ListProductsByCategoryAdaptar(ArrayList<Products> products) {
        this.products = products;
    }


    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view=inflater.inflate(R.layout.item_products,null,false);

        TextView ProductName = view.findViewById(R.id.TVProductName);
        TextView ProductPrice = view.findViewById(R.id.TVProductPrice);
        ProductName.setText(products.get(position).getName());
        ProductPrice.setText(products.get(position).getPrice() + "€");

        return view;
    }
}
