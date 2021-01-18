package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bargest.Adaptars.AddProductAdaptar;
import com.example.bargest.Adaptars.ListProductsByCategoryAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Categories;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListProductsByCategoryFragment extends Fragment implements ProductsListener{

    GridView GVProducts;
    private ListProductsByCategoryAdaptar productAdapter;
    ArrayList<Products> products;
    ArrayList<Products> allproducts;
    TextView TVTitleCategoryName;
    ImageButton BtnBackToCategories;


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
        TVTitleCategoryName = view.findViewById(R.id.TVTitleCategoryName);
        BtnBackToCategories = view.findViewById(R.id.BtnBackToCategories);

        TVTitleCategoryName.setText(getArguments().getString("category_name"));

        BtnBackToCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new ListCategoriesFragment()).commit();
            }
        });

        //allproducts = SingletonBarGest.getInstance(getContext()).getAllProducts(getContext());
        products = SingletonBarGest.getInstance(getContext()).getProductsByCategory(getContext(), getArguments().getInt("category_id"));
        if(products != null){
            productAdapter = new ListProductsByCategoryAdaptar(products);
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
        if(products.size() != 0){
            productAdapter = new ListProductsByCategoryAdaptar(products);
            GVProducts.setAdapter(productAdapter);
        }
    }

    @Override
    public void onRefreshArrayProducts(ArrayList<Products> products) {

    }
}