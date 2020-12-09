package com.example.bargest.Adaptars;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.example.bargest.Models.Categories;
import com.example.bargest.R;
import com.example.bargest.Views.Fragments.AddProductFragment;
import com.example.bargest.Views.Fragments.BillsDetailsFragment;

import java.util.ArrayList;

public class CategoriesAdaptar extends BaseAdapter {
    Context context;
    ArrayList<Categories> categories;
    FragmentTransaction transaction;
    public CategoriesAdaptar(Context context, ArrayList<Categories> categories, FragmentTransaction transaction) {
        this.context = context;
        this.categories= categories;
        this.transaction=transaction;
    }

    @Override
    public int getCount() {
        return categories.size();
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
        CategorieName.setText(categories.get(position).getName());

        CardCategorieName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("category_id",categories.get(position).getId());
                bundle.putString("category_name",String.valueOf(categories.get(position).getName()));
                AddProductFragment addProductFragment = new AddProductFragment();
                addProductFragment.setArguments(bundle);
               transaction.replace(R.id.conteinerAddProduct,addProductFragment).commit();
            }
        });

        return view;
    }
}
