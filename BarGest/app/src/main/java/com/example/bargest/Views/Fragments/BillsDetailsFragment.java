package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bargest.Adaptars.BillsDetailsAdaptar;
import com.example.bargest.Adaptars.TablesAdapters;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

public class BillsDetailsFragment extends Fragment {


    public BillsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_details, container, false);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        ListView listbillDetailsView = view.findViewById(R.id.ListBillDetails);
        TextView totalView = view.findViewById(R.id.TVTotalBills);

        final SingletonBarGest singletonBarGest = SingletonBarGest.getInstance(getContext());
        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        ArrayList<Bills> bills = SingletonBarGest.getInstance(getContext()).generateFakeDetailsBills();
        float totalBills= singletonBarGest.getTotalBills();

        totalView.setText(String.valueOf(totalBills) + " €");



        final BillsDetailsAdaptar adapters = new BillsDetailsAdaptar(getContext(),R.layout.item_list_bill_details,bills);
        listbillDetailsView.setAdapter(adapters);



        return view;
    }
}