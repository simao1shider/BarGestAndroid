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
import android.widget.TextView;

import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Adaptars.DivideAdaptar;
import com.example.bargest.Adaptars.NewRequestAdaptar;
import com.example.bargest.Listeners.DivideBillListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DivideBillFragment extends Fragment implements DivideBillListener {

    RecyclerView listOriginBill;
    RecyclerView listNewBill;
    TextView TVTotal;
    private DivideAdaptar adaptersOrigin;
    private DivideAdaptar adaptersNew;
    private ArrayList<Bills> Originproducts;
    private ArrayList<Bills> NewProducts;

    public DivideBillFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_divide_bill, container, false);


        listOriginBill = view.findViewById(R.id.listOriginBill);
        listNewBill = view.findViewById(R.id.listNewnBill);
        TVTotal = view.findViewById(R.id.TVtotalNewPrice);
        view.findViewById(R.id.BtnToolbarAdd).setVisibility(View.GONE);
        TVTotal.setText("0 €");
        listOriginBill.setLayoutManager(new LinearLayoutManager(getContext()));
        listNewBill.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonBarGest.getInstance(getContext()).getAccountProducts(getContext(), getArguments().getInt("account_id"));
        SingletonBarGest.getInstance(getContext()).setDivideBillListener(this);
        NewProducts = new ArrayList<>();

        adaptersOrigin = new DivideAdaptar(getContext(), Originproducts);
        adaptersNew = new DivideAdaptar(getContext(), NewProducts);


        ItemTouchHelper itemTouchHelperOriginBill = new ItemTouchHelper(callbackOrigin);
        ItemTouchHelper itemTouchHelperNewBill = new ItemTouchHelper(callbackNew);
        itemTouchHelperOriginBill.attachToRecyclerView(listOriginBill);
        itemTouchHelperNewBill.attachToRecyclerView(listNewBill);
        view.findViewById(R.id.BtnConfirmationNewBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack("Bills",0);
            }
        });
        listOriginBill.setAdapter(adaptersOrigin);
        listNewBill.setAdapter(adaptersNew);

        return view;
    }

    Bills deletedOriginBills;
    Bills deletedNewBills;
    float Total;
    ItemTouchHelper.SimpleCallback callbackOrigin = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            deletedOriginBills = Originproducts.get(position);
            Originproducts.remove(position);
            NewProducts.add(deletedOriginBills);
            adaptersOrigin.notifyDataSetChanged();
            adaptersNew.notifyDataSetChanged();
           // Total += deletedOriginBills.getPrice()*deletedOriginBills.getQuantidade();
            TVTotal.setText(String.valueOf(0)+"????");
            //TODO:Dialog for Quantity

        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.swip_move_color))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_arrow_circle_down_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback callbackNew = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            deletedNewBills = Originproducts.get(position);
            NewProducts.remove(position);
            Originproducts.add(deletedNewBills);
            adaptersOrigin.notifyDataSetChanged();
            adaptersNew.notifyDataSetChanged();
            //Total -= deletedNewBills.getPrice()*deletedNewBills.getQuantidade();
            TVTotal.setText(String.valueOf(0)+" ????€");
            //TODO:Dialog for Quantity

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.swip_move_color))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_arrow_circle_up_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onRefreshListProducts(ArrayList<Bills> bill) {
        adaptersOrigin = new DivideAdaptar(getContext(), Originproducts);
        listOriginBill.setAdapter(adaptersOrigin);
    }
}