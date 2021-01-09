package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bargest.Adaptars.NewRequestAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.Context.MODE_PRIVATE;


public class EditRequestFragment extends Fragment implements ProductsListener {


    public EditRequestFragment() {
        // Required empty public constructor
    }

    RecyclerView listProductsEditRequest;
    private NewRequestAdaptar adapters;
    private ArrayList<Products> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_request, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        listProductsEditRequest = view.findViewById(R.id.listProductsEditRequest);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        ImageView addRequest = view.findViewById(R.id.BtnToolbarAdd);
        TextView toolbarTitle = view.findViewById(R.id.TVTollbarTitle);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FBTNsave);

        toolbarTitle.setText("Pedido:"+getArguments().getInt("request_id"));

        listProductsEditRequest.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonBarGest.getInstance(getContext()).getRequestInfo(getContext(),getArguments().getInt("request_id"));
        SingletonBarGest.getInstance(getContext()).setProductListener(this);

        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        addRequest.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonBarGest.getInstance(getContext()).editRequest(getContext(),getArguments().getInt("request_id"),products);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(listProductsEditRequest);

        return view;
    }


    Products deletedResqust;

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            deletedResqust = products.get(position);
            Snackbar.make(listProductsEditRequest,deletedResqust.getName(), Snackbar.LENGTH_LONG).setAction("Confirmar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    products.remove(position);
                    adapters.notifyDataSetChanged(); }
            }).show();
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
        listProductsEditRequest.setAdapter(adapters);
        this.products=products;
    }

    @Override
    public void onRefreshArrayProducts(ArrayList<Products> products) {

    }




}