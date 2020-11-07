package com.example.bargest.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.bargest.Adaptars.TablesAdapters;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

public class TablesFragment extends Fragment {


    public TablesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tables, container, false);

        ListView listTables = view.findViewById(R.id.list_tables);
        SearchView searchTables = view.findViewById(R.id.searchTables);

        ArrayList<Tables> tables = SingletonBarGest.getInstance(getContext()).genereteFakeTableList();

        final TablesAdapters adapters = new TablesAdapters(getContext(),R.layout.item_list_tables,tables);
        listTables.setAdapter(adapters);

        listTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.container,new BillsFragment()).addToBackStack("Tables").commit();
            }
        });

        searchTables.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapters.getFilter().filter(newText);
                return false;
            }
        });

        return view;

    }
}