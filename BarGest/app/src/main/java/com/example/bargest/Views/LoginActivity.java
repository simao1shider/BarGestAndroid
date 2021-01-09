package com.example.bargest.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bargest.Listeners.LoginListener;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingletonBarGest.getInstance(getApplicationContext()).setLoginListener(this);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.ETUsername);
        password = findViewById(R.id.ETPassword);
    }

    public void ValidateLogin(View view) {
        String username =this.username.getText().toString();
        String password =this.password.getText().toString();
        if(IsValidUsername(username) && IsValidPassword(password))
            SingletonBarGest.getInstance(this).loginUserAPI(username,password,this);
        else
            Toast.makeText(getApplicationContext(),"Username ou password incorretos",Toast.LENGTH_LONG).show();
    }


    private boolean IsValidUsername(String username){
        if(username.length() == 0)
            return false;
        return true;
    }

    private boolean IsValidPassword(String password){
        if(password.length() == 0)
            return false;
        return true;
    }

    @Override
    public void onRefreshToken(String token) {
        if(token!=null){
            Intent intent = new Intent(this, MainActivity.class);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            startActivity(intent);
        }
    }
}