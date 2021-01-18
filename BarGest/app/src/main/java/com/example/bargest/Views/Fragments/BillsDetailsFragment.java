package com.example.bargest.Views.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bargest.Adaptars.BillsDetailsAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Listeners.ProductsToBePaidListener;
import com.example.bargest.Models.Products;
import com.example.bargest.Models.ProductsToBePaid;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BillsDetailsFragment extends Fragment implements ProductsToBePaidListener {

    Dialog dialog;
    ListView listbillDetailsView;
    TextView totalView;
    ArrayList<ProductsToBePaid> products;
    ArrayList<ProductsToBePaid> productsaux;
    DecimalFormat twoDForm;
    public BillsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_details, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
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
                Bundle bundle = new Bundle();
                bundle.putInt("account_id",getArguments().getInt("account_id"));
                bundle.putString("account_name",getArguments().getString("account_name"));
                DivideBillFragment divideBillFragment = new DivideBillFragment();
                divideBillFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, divideBillFragment).addToBackStack("BillsDetails").commit();
            }
        });
        BtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getArguments().getInt("account_id"));
            }
        });

        products = SingletonBarGest.getInstance(getContext()).getAccountProducts(getContext(),getArguments().getInt("account_id"));
        if(products != null){
            float totalBills= 0;
            productsaux = new ArrayList<>();
            //Fazer o total com base ,
            for (ProductsToBePaid product: products) {
                totalBills+=product.getQuantity()*product.getPrice();
                if(containArray(productsaux, product.getName())){
                    for(ProductsToBePaid productaux : productsaux){
                        if(product.getName().equals(productaux.getName())){
                            productaux.setQuantity(productaux.getQuantity() + product.getQuantity());
                        }
                    }
                }
                else{
                    productsaux.add(product);
                }
            }
            BigDecimal bigDecimal = new BigDecimal(Float.toString(totalBills));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            totalView.setText(bigDecimal.floatValue() + " €");
            final BillsDetailsAdaptar adapters = new BillsDetailsAdaptar(getContext(),R.layout.item_list_bill_details,productsaux);
            listbillDetailsView.setAdapter(adapters);
        }
        SingletonBarGest.getInstance(getContext()).setProductsToBePaidListener(this);

        return view;
    }

    private void openDialog(final int account_id) {
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
                EditText nif = dialog.findViewById(R.id.quantity);
                SingletonBarGest.getInstance(getContext()).pay(getContext(), account_id, Integer.parseInt(nif.getText().toString()), getFragmentManager());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onRefreshListProducts(ArrayList<ProductsToBePaid> products) {
        final BillsDetailsAdaptar adapters = new BillsDetailsAdaptar(getContext(),R.layout.item_list_bill_details,products);
        listbillDetailsView.setAdapter(adapters);
        float totalBills= 0;
        for (ProductsToBePaid product: products) {
            totalBills+=product.getQuantity()*product.getPrice();
        }
        BigDecimal bigDecimal = new BigDecimal(Float.toString(totalBills));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        totalView.setText(bigDecimal.floatValue() + " €");
    }


    private void total(){

    }

    @Override
    public void onRefreshArrayProducts(ArrayList<ProductsToBePaid> products) {

    }

    public boolean containArray(ArrayList<ProductsToBePaid> productsaux, String name){
        for(ProductsToBePaid product : productsaux){
            if(product.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}