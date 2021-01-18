package com.example.bargest.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.Fragments.HomeFragment;
import com.example.bargest.Views.Fragments.RequestFragment;
import com.example.bargest.Views.Fragments.TablesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            startActivity(intent);
            finish();
        }

        BottomNavigationView navBar = findViewById(R.id.bottom_bar);

        setFragment(new HomeFragment(),null);
        navBar.setSelectedItemId(R.id.nav_home);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_table:
                        setFragment(new TablesFragment(),"Tables");
                        return true;
                    case R.id.nav_home:
                        setFragment(new HomeFragment(),"Home");
                        return true;
                    case R.id.nav_receipt:
                        setFragment(new RequestFragment(),"Receipt");
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            startActivity(intent);
            finish();
        }
    }

    private void setFragment(Fragment fragment, @Nullable String backStack){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }


}