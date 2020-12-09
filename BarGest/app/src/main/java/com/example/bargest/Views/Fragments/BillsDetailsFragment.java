package com.example.bargest.Views.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

public class BillsDetailsFragment extends Fragment implements ProductsListener {

    Dialog dialog;
    ListView listbillDetailsView;
    TextView totalView;
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
        listbillDetailsView = view.findViewById(R.id.ListBillDetails);
        totalView = view.findViewById(R.id.TVTotalBills);
        TextView toolbarTitle = view.findViewById(R.id.TVTollbarTitle);

        toolbarTitle.setText(getArguments().getString("account_name"));

        dialog = new Dialog(getContext());
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
                bundle.putInt("account_id",getArguments().getInt("account_id"));
                bundle.putString("account_name",getArguments().getString("account_name"));
                NewRequestFragment newRequestFragment = new NewRequestFragment();
                newRequestFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container,newRequestFragment).addToBackStack("BillsDetails").commit();
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

        SingletonBarGest.getInstance(getContext()).getAccountProducts(getContext(),getArguments().getInt("account_id"));
        SingletonBarGest.getInstance(getContext()).setProductListener(this);

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

    @Override
    public void onRefreshListProducts(ArrayList<Products> products) {
        final BillsDetailsAdaptar adapters = new BillsDetailsAdaptar(getContext(),R.layout.item_list_bill_details,products);
        listbillDetailsView.setAdapter(adapters);
        float totalBills= 0;
        for (Products product: products
             ) {
            totalBills+=product.getQuantity()*product.getPrice();
        }
        totalView.setText(String.valueOf(totalBills) + " €");
    }
}