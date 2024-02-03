package com.example.luqya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Details_event_class extends AppCompatActivity {

    private TextView titleTextView, categoryTextView, initiativeTextView, locationTextView, dateTextView,
            timeTextView, languageTextView, durationTextView, atthendingMethodTextView, overviewTextView;
    Button register_button;
    ImageView imageView;
    private String title, category, initiative, location, date, time, language, duration, attendingMethod, overview;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event_class);
        getSupportActionBar().setTitle("Event Details");


        titleTextView = findViewById(R.id.event_title);
        categoryTextView = findViewById(R.id.event_category);
        initiativeTextView = findViewById(R.id.event_initiative_name);
        locationTextView = findViewById(R.id.event_location);
        dateTextView = findViewById(R.id.event_date);
        timeTextView = findViewById(R.id.event_time);
        languageTextView = findViewById(R.id.event_language);
        durationTextView = findViewById(R.id.event_duration);
        overviewTextView = findViewById(R.id.event_overview);
        atthendingMethodTextView = findViewById(R.id.event_attending_method);

        imageView = findViewById(R.id.event_image);
        register_button = findViewById(R.id.Register);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            title = bundle.getString("Title");
            category = getIntent().getStringExtra("category");
            location = bundle.getString("Location");
            date = bundle.getString("Date");
            duration = bundle.getString("Duration");
            overview = bundle.getString("Overview");
            time = bundle.getString("Time");
            language = getIntent().getStringExtra("Language");
            attendingMethod = getIntent().getStringExtra("attendingMethod");
            initiative = bundle.getString("initiative");
            Glide.with(this).load(bundle.getString("Image")).into(imageView);

            titleTextView.setText(title);
            categoryTextView.setText(category);
            locationTextView.setText(location);
            dateTextView.setText(date);
            durationTextView.setText(duration);
            overviewTextView.setText(overview);
            timeTextView.setText(time);
            languageTextView.setText(language);
            atthendingMethodTextView.setText(attendingMethod);

            initiativeTextView.setText(initiative);

            initiativeTextView.setText(initiative + " ");
        }
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    // If user is not logged in, navigate to the login activity
                    Intent intent = new Intent(Details_event_class.this, LogIn.class);
                    startActivity(intent);
                } else {
                    // If user is logged in, proceed with event registration
                    register_button(currentUser);
                }
            }
        });
    }

    private void register_button(FirebaseUser currentUser) {
        String eventName = title; // Get the event name from the activity


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("EventData").child(eventName).child("attendees");

        // Add the current user to the attendees of the current event
        dbRef.child(currentUser.getUid()).setValue(true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Details_event_class.this, "You've successfully registered to the event!", Toast.LENGTH_LONG).show();
                        Log.d("Registration", "User successfully registered!");
                        // Navigate to the MyEventsActivity or any other desired activity
                        Intent intent = new Intent(Details_event_class.this, MyEvent_bar.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Registration", "Error registering user", e);

                    }
                });
    }
}




