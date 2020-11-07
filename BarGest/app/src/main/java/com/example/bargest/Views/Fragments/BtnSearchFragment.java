package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.bargest.R;


public class BtnSearchFragment extends Fragment {


    public BtnSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_btn_search, container, false);

        ImageView btnSearch = view.findViewById(R.id.btnSearch);
        ImageButton back = view.findViewById(R.id.IMGBackFragmentBtnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: passar por parametro o id para onde colocar
                getFragmentManager().beginTransaction().replace(R.id.toolbarNewRequest, new SearchFragment()).commit();
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
}