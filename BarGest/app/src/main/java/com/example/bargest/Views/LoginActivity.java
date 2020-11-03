package com.example.bargest.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bargest.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void ValidateLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //TODO:Transition for main Layout
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);


    }


    private boolean IsValidUsername(){
        return true;
    }

    private boolean IsValidPassword(){
        return true;
    }
}