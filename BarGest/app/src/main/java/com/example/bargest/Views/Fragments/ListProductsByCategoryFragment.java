package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.bargest.Views.LoginActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListProductsByCategoryFragment extends Fragment implements ProductsListener{

    GridView GVProducts;
    private AddProductAdaptar productAdapter;
    ArrayList<Products> products;
    ArrayList<Products> allproducts;


    public ListProductsByCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_product, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        GVProducts = view.findViewById(R.id.GVAddProduct);
        allproducts = SingletonBarGest.getInstance(getContext()).getAllProducts(getContext());
        if(allproducts != null){
            products = SingletonBarGest.getInstance(getContext()).getProductsByCategory(getContext(), getArguments().getInt("category_id"));
            productAdapter = new AddProductAdaptar(getContext(), products);
            GVProducts.setAdapter(productAdapter);
        }
        else{
            products = SingletonBarGest.getInstance(getContext()).getProductsByCategory(getContext(), getArguments().getInt("category_id"));
        }

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