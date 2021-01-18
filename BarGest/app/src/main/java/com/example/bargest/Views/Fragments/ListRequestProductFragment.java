package com.example.bargest.Views.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bargest.Adaptars.ListRequestProductAdapter;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Listeners.ProductsToBePaidListener;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.Fragments.dummy.DummyContent;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ListRequestProductFragment extends Fragment implements ProductsToBePaidListener {

    RecyclerView recyclerView;
    ArrayList<ProductsToBePaid> products;
    public ListRequestProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_request_product, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_requestproduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        products = SingletonBarGest.getInstance(getContext()).getRequestInfo(getContext(),getArguments().getInt("request_id"));
        if(products != null){
            recyclerView.setAdapter(new ListRequestProductAdapter(products));
        }

        SingletonBarGest.getInstance(getContext()).setProductsToBePaidListener(this);
        return view;
    }

    @Override
    public void onRefreshListProducts(ArrayList<ProductsToBePaid> products) {
        recyclerView.setAdapter(new ListRequestProductAdapter(products));
    }

    @Override
    public void onRefreshArrayProducts(ArrayList<ProductsToBePaid> products) {

    }
}