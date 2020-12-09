package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bargest.Adaptars.AddProductAdaptar;
import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;


public class AddProductFragment extends Fragment implements ProductsListener {

    GridView GVAddProduct;
    private AddProductAdaptar adaptarCategories;

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int category_id = getArguments().getInt("category_id");
        String category_name = getArguments().getString("category_name");
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        GVAddProduct = view.findViewById(R.id.GVAddProduct);
        ImageButton backCategories = view.findViewById(R.id.BtnBackToCategories);
        TextView title = view.findViewById(R.id.TVTitleCategoryName);

        title.setText(category_name);
        SingletonBarGest.getInstance(getContext()).getProductsByCategory(getContext(),category_id);
        SingletonBarGest.getInstance(getContext()).setProductListener(this);


        backCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.conteinerAddProduct,new CategoriesFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public void onRefreshListProducts(ArrayList<Products> products) {
        adaptarCategories = new AddProductAdaptar(getContext(),products);
        GVAddProduct.setAdapter(adaptarCategories);
    }
}