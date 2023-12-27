package com.example.luqya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Events extends AppCompatActivity {

    LinearLayout profile_Btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        profile_Btn = findViewById(R.id.Profile_Btn);
        profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Events.this, Edit_Profile.class);
                startActivity(i);
            }
        });

    }
}
