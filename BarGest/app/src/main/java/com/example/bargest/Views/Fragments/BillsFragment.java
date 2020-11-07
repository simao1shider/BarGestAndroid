package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.bargest.Adaptars.BillsAdaptar;
import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Models.Bills;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;


public class BillsFragment extends Fragment {


    public BillsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills, container, false);
        GridView billsView = view.findViewById(R.id.GVBills);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        ImageView addRequest = view.findViewById(R.id.BtnToolbarAdd);
        ArrayList<Bills> bills = SingletonBarGest.getInstance(getContext()).generateFakeDetailsBills();
        BillsAdaptar adaptar = new BillsAdaptar(getContext(),bills,getFragmentManager().beginTransaction());
        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new NewRequestFragment()).addToBackStack("Bills").commit();
            }
        });

        billsView.setAdapter(adaptar);
        return view;
    }
}