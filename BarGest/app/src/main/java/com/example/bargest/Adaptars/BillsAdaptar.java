package com.example.bargest.Adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.R;
import com.example.bargest.Views.Fragments.AddProductFragment;
import com.example.bargest.Views.Fragments.BillsDetailsFragment;

import java.util.ArrayList;

public class BillsAdaptar extends BaseAdapter {

    Context context;
    ArrayList<Bills> bills;
    FragmentTransaction transaction;


    public BillsAdaptar(Context context, ArrayList<Bills> bills, FragmentTransaction transaction) {
        this.context = context;
        this.bills= bills;
        this.transaction=transaction;
    }

    @Override
    public int getCount() {
        return bills.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view=inflater.inflate(R.layout.item_categorie,null,false);

        TextView CategorieName = view.findViewById(R.id.TVCategorieName);
        CardView CardCategorieName = view.findViewById(R.id.CVCategorie);

        CategorieName.setText(bills.get(position).getProductName());
        CardCategorieName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.container,new BillsDetailsFragment()).addToBackStack("Bills").commit();
            }
        });
        return view;
    }
}
