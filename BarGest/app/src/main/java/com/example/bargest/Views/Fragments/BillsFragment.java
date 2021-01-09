package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bargest.Adaptars.BillsAdaptar;
import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Listeners.BillListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class BillsFragment extends Fragment implements BillListener {

    GridView billsView;
    public BillsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String table_id = getArguments().getString("table_id").trim();
        final int table_number = getArguments().getInt("table_number");

        View view = inflater.inflate(R.layout.fragment_bills, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        billsView = view.findViewById(R.id.GVBills);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        ImageView addRequest = view.findViewById(R.id.BtnToolbarAdd);
        TextView tollbarTitle= view.findViewById(R.id.TVTollbarTitle);

        tollbarTitle.setText("Mesa "+table_number);

        SingletonBarGest.getInstance(getContext()).getAPITableAccountsList(getContext(),table_id);
        SingletonBarGest.getInstance(getContext()).setBillsListener(this);

        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("table_id",Integer.parseInt(table_id));
                bundle.putInt("table_number",table_number);
                NewRequestFragment newRequestFragment = new NewRequestFragment();
                newRequestFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container,newRequestFragment).addToBackStack("Bills").commit();
            }
        });


        return view;
    }

    @Override
    public void onRefreshListTables(ArrayList<Bills> bills) {
        BillsAdaptar adaptar = new BillsAdaptar(getContext(),bills,getFragmentManager().beginTransaction());
        billsView.setAdapter(adaptar);
    }

    @Override
    public void onUpdateListTables(ArrayList<Bills> bills) {

    }
}