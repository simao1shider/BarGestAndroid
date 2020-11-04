package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

public class TableDetailsFragment extends Fragment {


    public TableDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table_details, container, false);
        ImageView backfragment = view.findViewById(R.id.IMGBackFragment);
        final SingletonBarGest singletonBarGest = SingletonBarGest.getInstance(getContext());
        backfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}