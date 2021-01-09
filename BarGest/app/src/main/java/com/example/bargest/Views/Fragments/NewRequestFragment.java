package com.example.bargest.Views.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bargest.Adaptars.NewRequestAdaptar;
import com.example.bargest.Listeners.NewRequestListner;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.Context.MODE_PRIVATE;


public class NewRequestFragment extends Fragment implements NewRequestListner {


    public NewRequestFragment() {
        // Required empty public constructor
    }
    RecyclerView listProductsNewRequest;
    private NewRequestAdaptar adapters;
    public ArrayList<Products> products;
    Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_request, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        dialog = new Dialog(getContext());

        Bundle bundle = new Bundle();
        BtnSearchFragment btnSearchFragment = new BtnSearchFragment();
        if(getArguments().containsKey("table_id"))
        {
            bundle.putInt("table_id",getArguments().getInt("table_id"));
            bundle.putInt("table_number",getArguments().getInt("table_number"));
            btnSearchFragment.setArguments(bundle);
        }
        if(getArguments().containsKey("account_id"))
        {
            bundle.putInt("account_id",getArguments().getInt("account_id"));
            bundle.putString("account_name",getArguments().getString("account_name"));
            btnSearchFragment.setArguments(bundle);
        }

         getFragmentManager().beginTransaction().replace(R.id.toolbarNewRequest,btnSearchFragment).commit();
         getFragmentManager().beginTransaction().replace(R.id.conteinerAddProduct,new CategoriesFragment()).commit();

         listProductsNewRequest = view.findViewById(R.id.list_new_request);

         SingletonBarGest.getInstance(getContext()).startNewRequest();
        SingletonBarGest.getInstance(getContext()).setNewrequestsListener(this);

        listProductsNewRequest.setLayoutManager(new LinearLayoutManager(getContext()));


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(listProductsNewRequest);


        Button create = view.findViewById(R.id.BTNCraeteRequest);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments().containsKey("account_id"))
                {
                    SingletonBarGest.getInstance(getContext()).createRequestAccount(getContext(),getArguments().getInt("account_id"),products, getFragmentManager());
                }
                if(getArguments().containsKey("table_id")){
                    openDialog();
                }

            }
        });


        return view;
    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            products.remove(position);
            adapters.notifyDataSetChanged();
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.swipe_delete_color))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public void onRefreshListProducts(ArrayList<Products> products) {
        adapters = new NewRequestAdaptar(getContext(),products);
        listProductsNewRequest.setAdapter(adapters);
        this.products=products;
    }



    private void openDialog() {
        dialog.setContentView(R.layout.dialog_account);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        Button createRequest = dialog.findViewById(R.id.BTNAddAccount);

        createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = dialog.findViewById(R.id.EdTAccountName);
                String accountName = editText.getText().toString();
                SingletonBarGest.getInstance(getContext()).createRequestTable(getContext(),getArguments().getInt("table_id"),accountName,products, getFragmentManager());
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}