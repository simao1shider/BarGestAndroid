package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.bargest.Adaptars.CategoriesAdaptar;
import com.example.bargest.Listeners.CategoriesListener;
import com.example.bargest.Models.Categories;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListCategoriesFragment extends Fragment implements CategoriesListener {

    GridView GVCategories;
    private CategoriesAdaptar adaptarCategories;

    public ListCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        GVCategories = view.findViewById(R.id.containerCategory);
        SingletonBarGest.getInstance(getContext()).getAllCategories(getContext());
        SingletonBarGest.getInstance(getContext()).setCategoriesListener(this);

        return view;
    }

    @Override
    public void onRefreshCategories(ArrayList<Categories> categories) {
        adaptarCategories = new CategoriesAdaptar(getContext(), categories, getFragmentManager().beginTransaction(), R.id.container);
        GVCategories.setAdapter(adaptarCategories);
    }
}