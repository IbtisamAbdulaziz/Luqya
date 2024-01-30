package com.example.luqya;

import android.os.Bundle;
import android.view.View;
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

    @Override
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
        register_button = findViewById(R.id.event_register_button);


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
        }


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

          /*  deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            reference.child(key).removeValue();
                            Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                            .putExtra("Title", detailTitle.getText().toString())
                            .putExtra("Description", detailDesc.getText().toString())
                            .putExtra("Language", detailLang.getText().toString())
                            .putExtra("Image", imageUrl)
                            .putExtra("Key", key);
                    startActivity(intent);
                }
   });*/

    }
}


