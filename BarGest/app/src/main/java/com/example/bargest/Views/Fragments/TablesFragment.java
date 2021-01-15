package com.example.bargest.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bargest.Adaptars.TablesAdapters;
import com.example.bargest.Listeners.TableListener;
import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Tables;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class TablesFragment extends Fragment implements TableListener {

    ListView listTables;
    TablesAdapters adapters;
    EditText tableNumber;
    ArrayList<Tables> tables;

    public TablesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        listTables = view.findViewById(R.id.list_tables);
        tableNumber = view.findViewById(R.id.tableNumber);
        tableNumber.setFocusable(false);

        if(SingletonBarGest.getInstance(getContext()).getAPITableList(getContext()) != null){
            this.tables = SingletonBarGest.getInstance(getContext()).getAPITableList(getContext());
            adapters = new TablesAdapters(getContext(), R.layout.item_list_tables, this.tables);
            listTables.setAdapter(adapters);

            listTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle=new Bundle();
                    bundle.putString("table_id",String.valueOf(tables.get(position).getId()));
                    bundle.putInt("table_number",tables.get(position).getNumber());
                    BillsFragment billsFragment = new BillsFragment();
                    billsFragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.container,billsFragment).addToBackStack("Tables").commit();
                }
            });
        }

        SingletonBarGest.getInstance(getContext()).setTableListener(this);

        tableNumber.setOnEditorActionListener(editorListner);
        return view;

    }


    private TextView.OnEditorActionListener editorListner = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean find=false;
            if(actionId == EditorInfo.IME_ACTION_DONE){
                String number = v.getText().toString();
                for (Tables table: tables) {
                    if(table.getNumber() == Integer.parseInt(number)){
                        find=true;
                        Bundle bundle=new Bundle();
                        bundle.putString("table_id",String.valueOf(table.getId()));
                        bundle.putInt("table_number",table.getNumber());
                        BillsFragment billsFragment = new BillsFragment();
                        billsFragment.setArguments(bundle);

                        getFragmentManager().beginTransaction().replace(R.id.container,billsFragment).addToBackStack("Tables").commit();
                    }
                }
                if(!find){
                    Toast.makeText(getContext(),"Mesa não existe", Toast.LENGTH_LONG).show();
                }
            }
            return false;
        }
    };

    @Override
    public void onRefreshListTables(final ArrayList<Tables> tables) {
        this.tables = tables;
        if(tables.size() != 0 ){
            tableNumber.setFocusableInTouchMode(true);
        }

        if (getContext() != null){
            adapters = new TablesAdapters(getContext(), R.layout.item_list_tables, this.tables);
            listTables.setAdapter(adapters);
        }

        listTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString("table_id",String.valueOf(tables.get(position).getId()));
                bundle.putInt("table_number",tables.get(position).getNumber());
                BillsFragment billsFragment = new BillsFragment();
                billsFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.container,billsFragment).addToBackStack("Tables").commit();
            }
        });

    }

    @Override
    public void onUpdateListTables(ArrayList<Tables> tables) {

    }
}