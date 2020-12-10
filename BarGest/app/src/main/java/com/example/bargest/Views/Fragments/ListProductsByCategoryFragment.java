package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.bargest.Adaptars.AddProductAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

public class ListProductsByCategoryFragment extends Fragment implements ProductsListener{

    GridView GVProducts;
    private AddProductAdaptar productAdapter;

    public ListProductsByCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_product, container, false);
        GVProducts = view.findViewById(R.id.GVAddProduct);

        SingletonBarGest.getInstance(getContext()).getProductsByCategory(getContext(), getArguments().getInt("category_id"));
        SingletonBarGest.getInstance(getContext()).setProductListener(this);

        return view;
    }

    @Override
    public void onRefreshListProducts(ArrayList<Products> products) {
        productAdapter = new AddProductAdaptar(getContext(), products);
        GVProducts.setAdapter(productAdapter);
    }

    @Override
    public void onRefreshArrayProducts(ArrayList<Products> products) {

    }
}