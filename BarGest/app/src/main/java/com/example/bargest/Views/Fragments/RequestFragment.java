package com.example.bargest.Views.Fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bargest.Adaptars.RequestsAdaptars;
import com.example.bargest.Listeners.ListRequestsListener;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.views.ListRequests;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class RequestFragment extends Fragment implements ListRequestsListener {


    public RequestFragment() {
        // Required empty public constructor
    }

    private RequestsAdaptars adapters;
    private ArrayList<ListRequests> requests;
    private  RecyclerView listRequest;
    private TextView infoRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_request, container, false);
        infoRequest = view.findViewById(R.id.TVInfoExistRequest);

        listRequest = view.findViewById(R.id.ListRequests);
        listRequest.setLayoutManager(new LinearLayoutManager(getContext()));


        SingletonBarGest.getInstance(getContext()).getAPIListRequests(getContext());
        SingletonBarGest.getInstance(getContext()).setListRequestListener(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(listRequest);

        return view;
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.RIGHT:
                    getFragmentManager().beginTransaction().replace(R.id.container,new EditRequestFragment()).addToBackStack("Requests").commit();
                    break;
                case ItemTouchHelper.LEFT:
                    Snackbar.make(listRequest,"Pedido: " +  requests.get(position).getId(), Snackbar.LENGTH_LONG).setAction("Confirmar", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SingletonBarGest.getInstance(getContext()).deleteRequest(getContext(),requests.get(position).getId());
                            requests.remove(position);
                            adapters.notifyDataSetChanged();
                        }
                    }).show();
                    adapters.notifyDataSetChanged();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.swipe_delete_color))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.swipe_edit_color))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onRefreshListTables(ArrayList<ListRequests> requests) {
        if(requests.size()!=0){
            adapters = new RequestsAdaptars(getContext(),requests);
            listRequest.setAdapter(adapters);
            this.requests=requests;
        }
        else{
            infoRequest.setVisibility(View.VISIBLE);
        }
    }
}