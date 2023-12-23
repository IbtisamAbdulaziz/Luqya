package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainInterface extends AppCompatActivity {
    SharedPreferences settings;
    TextView textview_guest;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);

        getSupportActionBar().setTitle("لُقيا");

        Button loginButton = findViewById(R.id.btn_login);
        Button signUpButton = findViewById(R.id.btn_sign_up);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Administrator = new Intent(MainInterface.this, LogIn.class);
                startActivity(Administrator);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Initiative_Founder = new Intent(MainInterface.this, SignUpType.class);
                startActivity(Initiative_Founder);

            }
        });

        //check the cookies if he has login before

        settings = getSharedPreferences("ID", 0);
        Boolean check = settings.getBoolean("check", false);

        // if he/she has loged in before just move to the main page

        if (check) {
            Intent i = new Intent(MainInterface.this, LogIn.class);
            startActivity(i);
            finish();
        }

        textview_guest = findViewById(R.id.textview_guest);
        textview_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainInterface.this,Events.class);
                startActivity(i);
            }
            
        });
    }

}