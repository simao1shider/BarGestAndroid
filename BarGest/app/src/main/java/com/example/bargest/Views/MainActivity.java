package com.example.bargest.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bargest.R;
import com.example.bargest.Views.Fragments.HomeFragment;
import com.example.bargest.Views.Fragments.RequestFragment;
import com.example.bargest.Views.Fragments.TablesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navBar = findViewById(R.id.bottom_bar);
        navBar.setSelectedItemId(R.id.nav_home);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_table:
                        setFragment(new TablesFragment());
                        return true;
                    case R.id.nav_home:
                        setFragment(new HomeFragment());
                        return true;
                    case R.id.nav_receipt:
                        setFragment(new RequestFragment());
                        return true;
                }

                return false;
            }
        });
        setFragment(new HomeFragment());


    }




    public void setHome(View view){
        setFragment(new HomeFragment());
    }


    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

}