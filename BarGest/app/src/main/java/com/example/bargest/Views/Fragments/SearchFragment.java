package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class SearchFragment extends Fragment implements ProductsListener {

    AutoCompleteTextView autoCompleteTextView;
    ArrayList<Products> productsList;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        ImageView btnCancelSearch = view.findViewById(R.id.btnCancelSearch);
        ImageButton back = view.findViewById(R.id.IMGBackFragmentSearch);
        autoCompleteTextView=view.findViewById(R.id.AUTOCOMPLETEproducts);
        SingletonBarGest.getInstance(getContext()).getAllProducts(getContext());
        SingletonBarGest.getInstance(getContext()).setProductListener(this);

        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                if(getArguments().containsKey("table_id")){
                    bundle.putInt("table_id",getArguments().getInt("table_id"));
                    bundle.putInt("table_number",getArguments().getInt("table_number"));
                }
                if(getArguments().containsKey("account_id")){
                    bundle.putInt("account_id",getArguments().getInt("account_id"));
                    bundle.putString("account_name",getArguments().getString("account_name"));
                }
                BtnSearchFragment btnSearchFragment = new BtnSearchFragment();
                btnSearchFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.toolbarNewRequest, btnSearchFragment).commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onRefreshListProducts(ArrayList<Products> products) {
        this.productsList=products;
    }

    @Override
    public void onRefreshArrayProducts(final ArrayList<Products> products) {
        final ArrayList<String> arrayList = new ArrayList<>();
        for (Products product: products
             ) {
            arrayList.add(product.getName());
        }
        final ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Products product1=null;
                for (Products product:products
                     ) {
                    if(product.getName()==adapter.getItem(position)){
                         product1=product;
                         break;
                    }
                }
                SingletonBarGest.getInstance(getContext()).addNewRequest(product1);
            }
        });
    }
}