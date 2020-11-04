package com.example.bargest.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bargest.Adaptars.RequestsAdaptars;
import com.example.bargest.Models.Requests;
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

        listRequest.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Requests>  requests = SingletonBarGest.getInstance(getContext()).genereteFakeRequestList();
        Log.d("Request",String.valueOf(requests));
        final RequestsAdaptars adapters = new RequestsAdaptars(getContext(),requests);



        listRequest.setAdapter(adapters);
        return view;
    }
}