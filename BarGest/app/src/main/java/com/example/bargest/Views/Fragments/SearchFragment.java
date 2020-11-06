package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bargest.R;


public class SearchFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        ImageView btnCancelSearch = view.findViewById(R.id.btnCancelSearch);

        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: passar por parametro o id para onde colocar
                getFragmentManager().beginTransaction().replace(R.id.toolbarNewRequest, new BtnSearchFragment()).commit();
            }
        });

        return view;
    }
}