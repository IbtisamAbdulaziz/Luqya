package com.example.luqya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdministratorMainActivity extends AppCompatActivity {

    private TextView  AddEvent,viewInitiativesTextView, viewSeekersTextView, viewEventsTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrator);

        AddEvent = findViewById(R.id.Add_Event);
        viewInitiativesTextView = findViewById(R.id.view_initiatives_button);
        viewSeekersTextView = findViewById(R.id.view_seekers_button);
        viewEventsTextView = findViewById(R.id.view_events_button);

        AddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorMainActivity.this, AddEvent.class);
                startActivity(intent);
            }
        });
        viewInitiativesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdministratorMainActivity.this, ViewInitiatives.class);
                startActivity(intent);

            }
        });

        viewSeekersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdministratorMainActivity.this, ViewSeekers.class);
                startActivity(intent);

            }
        });

        viewEventsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdministratorMainActivity.this, ViewEvents.class);
                startActivity(intent);

            }
        });



    }
}
