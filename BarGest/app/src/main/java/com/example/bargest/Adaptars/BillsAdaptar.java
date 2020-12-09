package com.example.bargest.Adaptars;

import android.content.Context;
import android.os.Bundle;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view=inflater.inflate(R.layout.item_categorie,null,false);

        TextView CategorieName = view.findViewById(R.id.TVCategorieName);
        CardView CardCategorieName = view.findViewById(R.id.CVCategorie);

        CategorieName.setText(bills.get(position).getProductName());
        CardCategorieName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("account_id",bills.get(position).getId());
                bundle.putString("account_name",bills.get(position).getProductName());
                BillsDetailsFragment billsDetailsFragment = new BillsDetailsFragment();
                billsDetailsFragment.setArguments(bundle);
                transaction.replace(R.id.container,billsDetailsFragment).addToBackStack("Bills").commit();
            }
        });
        return view;
    }
}
