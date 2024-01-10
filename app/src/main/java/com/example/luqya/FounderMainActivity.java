package com.example.luqya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FounderMainActivity extends AppCompatActivity {
    ImageView Initiative_Logo;
    TextView Initiative_Name;
    Button Initiative_Profile, Add_Event, My_Events, Edit_Profile, Statistics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_main);

        Initiative_Logo = findViewById(R.id.imageView);
        Initiative_Name = findViewById(R.id.textView);
        Initiative_Profile = findViewById(R.id.button);
        Add_Event = findViewById(R.id.button2);
        My_Events = findViewById(R.id.button3);
        Edit_Profile = findViewById(R.id.button4);
        Statistics = findViewById(R.id.button5);


        Add_Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FounderMainActivity.this,AddEvent.class);
                startActivity(intent);
            }
        });

        Initiative_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FounderMainActivity.this ,View_InitiativeFounderProfile.class);
                startActivity(intent1);
            }
        });

        Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FounderMainActivity.this, Edit_InitiativeFounderProfile.class);
                startActivity(intent);
            }
        });
        My_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FounderMainActivity.this, My_Events.class);
                startActivity(intent);
            }
        });
    }
}

