package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luqya.system_administrator.Login_administrator;

public class MainInterface extends AppCompatActivity {
    SharedPreferences settings;
    TextView textview_guest;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);


        Button System_Administrator = findViewById(R.id.System_Administrator);
        Button Cultural_Initiative_Founder = findViewById(R.id.Cultural_Initiative_Founder);
        Button Cultural_Event_Seeker = findViewById(R.id.Cultural_Event_Seeker);

        System_Administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Administrator = new Intent(MainInterface.this, Login_administrator.class);
                startActivity(Administrator);
            }
        });

        Cultural_Initiative_Founder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Initiative_Founder = new Intent(MainInterface.this,LogIn.class);
                startActivity(Initiative_Founder);
            }
        });
        Cultural_Event_Seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Event_Seeker =new Intent(MainInterface.this,LogIn.class);
                startActivity(Event_Seeker);
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

                Intent Signup = new Intent(MainInterface.this,Events.class);
                startActivity(Signup);
            }
        });
    }

}