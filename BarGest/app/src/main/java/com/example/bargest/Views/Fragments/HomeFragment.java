package com.example.bargest.Views.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;
import com.example.bargest.Views.MainActivity;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    TextView textView6;
    ImageView BtnLogout;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        ImageButton categoriesbutton = view.findViewById(R.id.categoriesButton);
        BtnLogout = view.findViewById(R.id.BTNlogout);

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().remove("token");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        categoriesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new ListCategoriesFragment()).commit();
            }
        });

        textView6 = view.findViewById(R.id.textView6);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        // textView is the TextView view that should display it
        textView6.setText(currentDateTimeString);
        return view;
    }
}