package com.example.bargest.Views.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

    Dialog dialog;

    public BillsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_details, container, false);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        ImageView addRequest = view.findViewById(R.id.BtnToolbarAdd);
        ImageButton divideFragment = view.findViewById(R.id.BtnDivideBill);
        ImageButton BtnPay = view.findViewById(R.id.BtnPay);
        ListView listbillDetailsView = view.findViewById(R.id.ListBillDetails);
        TextView totalView = view.findViewById(R.id.TVTotalBills);
        dialog = new Dialog(getContext());
        final SingletonBarGest singletonBarGest = SingletonBarGest.getInstance(getContext());
        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new NewRequestFragment()).addToBackStack("BillsDetails").commit();
            }
        });
        divideFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new DivideBillFragment()).addToBackStack("BillsDetails").commit();
            }
        });
        BtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        ArrayList<Bills> bills = SingletonBarGest.getInstance(getContext()).generateFakeDetailsBills();
        float totalBills= 0;

        totalView.setText(String.valueOf(totalBills) + " €");



        final BillsDetailsAdaptar adapters = new BillsDetailsAdaptar(getContext(),R.layout.item_list_bill_details,bills);
        listbillDetailsView.setAdapter(adapters);



        return view;
    }

    private void openDialog() {
        dialog.setContentView(R.layout.dialog_pay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.BtnClosePayDialog);
        Button addNif = dialog.findViewById(R.id.BtnAddNif);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addNif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}