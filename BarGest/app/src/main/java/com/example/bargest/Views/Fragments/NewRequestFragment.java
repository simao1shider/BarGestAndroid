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

import com.example.bargest.Adaptars.NewRequestAdaptar;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Categories;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class NewRequestFragment extends Fragment {


    public NewRequestFragment() {
        // Required empty public constructor
    }
    RecyclerView listProductsNewRequest;
    private NewRequestAdaptar adapters;
    private ArrayList<Bills> products;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_new_request, container, false);
         getFragmentManager().beginTransaction().replace(R.id.toolbarNewRequest,new BtnSearchFragment()).commit();
         getFragmentManager().beginTransaction().replace(R.id.conteinerAddProduct,new CategoriesFragment()).commit();

         listProductsNewRequest = view.findViewById(R.id.list_new_request);

        listProductsNewRequest.setLayoutManager(new LinearLayoutManager(getContext()));

        products=SingletonBarGest.getInstance(getContext()).generateFakeDetailsBills();

        adapters = new NewRequestAdaptar(getContext(),products);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(listProductsNewRequest);

        listProductsNewRequest.setAdapter(adapters);

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
            Snackbar.make(listProductsNewRequest,deletedResqust.getProductName(), Snackbar.LENGTH_LONG).setAction("Cancelar", new View.OnClickListener() {
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