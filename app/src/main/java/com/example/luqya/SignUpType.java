package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SignUpType extends AppCompatActivity {

    private RelativeLayout initiativeFounderSignUp, eventsSeekerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_type);

        initiativeFounderSignUp = findViewById(R.id.initiative_founder_signup);
        eventsSeekerSignUp = findViewById(R.id.events_seeker_signup);

        initiativeFounderSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpType.this, InitiativeFounderSignUp.class);
                startActivity(i);
            }
        });

        eventsSeekerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpType.this, EventsSeekerSignUp.class);
                startActivity(i);
            }
        });
    }
}