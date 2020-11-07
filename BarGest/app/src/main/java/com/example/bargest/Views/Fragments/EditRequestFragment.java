package com.example.bargest.Views.Fragments;

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
import android.widget.ImageView;

import com.example.bargest.Adaptars.NewRequestAdaptar;
import com.example.bargest.Models.Bills;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class EditRequestFragment extends Fragment {


    public EditRequestFragment() {
        // Required empty public constructor
    }

    RecyclerView listProductsEditRequest;
    private NewRequestAdaptar adapters;
    private ArrayList<Bills> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_request, container, false);
        listProductsEditRequest = view.findViewById(R.id.listProductsEditRequest);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        ImageView addRequest = view.findViewById(R.id.BtnToolbarAdd);

        listProductsEditRequest.setLayoutManager(new LinearLayoutManager(getContext()));

        products = SingletonBarGest.getInstance(getContext()).generateFakeDetailsBills();


        adapters = new NewRequestAdaptar(getContext(),products);


        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new NewRequestFragment()).addToBackStack("EditRequest").commit();
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(listProductsEditRequest);

        listProductsEditRequest.setAdapter(adapters);


        return view;
    }


    Bills deletedResqust;

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            deletedResqust = products.get(position);
            products.remove(position);
            adapters.notifyDataSetChanged();
            Snackbar.make(listProductsEditRequest,deletedResqust.getProductName(), Snackbar.LENGTH_LONG).setAction("Cancelar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    products.add(position,deletedResqust);
                    adapters.notifyDataSetChanged(); }
            }).show();

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


}