package com.example.luqya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Events extends AppCompatActivity {

    private LinearLayout profile_Btn;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        profile_Btn = findViewById(R.id.Profile_Btn);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseUser == null){
                    Intent i = new Intent(Events.this, SignUpType.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(Events.this, ShowSeekerProfile.class);
                    startActivity(i);
                }
            }
        });

    }
}
