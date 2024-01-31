package com.example.luqya;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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

        /*
        List<DataClass> dataList;
        RecyclerView recyclerView;
                                   */


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

       // recyclerView = findViewById(R.id.myeventView2);

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
    }
}




