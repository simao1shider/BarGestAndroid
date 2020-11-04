package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bargest.Adaptars.RequestsAdaptars;
import com.example.bargest.Adaptars.TablesAdapters;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;


public class RequestFragment extends Fragment {


    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_request, container, false);
        RecyclerView listRequest = view.findViewById(R.id.ListRequests);

        ArrayList<Requests>  requests = SingletonBarGest.getInstance(getContext()).genereteFakeRequestList();

        final RequestsAdaptars adapters = new RequestsAdaptars(getContext(),requests);

        listRequest.setAdapter(adapters);
        return view;
    }
}