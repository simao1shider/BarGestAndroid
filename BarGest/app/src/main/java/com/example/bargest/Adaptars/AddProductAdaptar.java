package com.example.bargest.Adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.Requests;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.Fragments.AddProductFragment;

import java.util.ArrayList;

public class AddProductAdaptar extends BaseAdapter {

    Context context;
    ArrayList<Products> products;
    public AddProductAdaptar(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products= products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view=inflater.inflate(R.layout.item_categorie,null,false);

        TextView CategorieName = view.findViewById(R.id.TVCategorieName);
        CardView CardCategorieName = view.findViewById(R.id.CVCategorie);
        CategorieName.setText(products.get(position).getName());

        CardCategorieName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonBarGest.getInstance(context).addNewRequest(products.get(position));
            }
        });

        return view;
    }
}
