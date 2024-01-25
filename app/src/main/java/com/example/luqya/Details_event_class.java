package com.example.luqya;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Details_event_class extends AppCompatActivity {


    TextView title, category, initiative, location, date, time, language, duration, atthendingMethod, overview;
    Button register_button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event_class);

        title = findViewById(R.id.event_title);
        category = findViewById(R.id.event_category);
        initiative = findViewById(R.id.event_initiative_name);
        location = findViewById(R.id.event_location);
        date = findViewById(R.id.event_date);
        time = findViewById(R.id.event_time);
        language = findViewById(R.id.event_language);
        duration = findViewById(R.id.event_duration);
        overview = findViewById(R.id.event_overview);
        atthendingMethod = findViewById(R.id.event_attending_method);

        imageView = findViewById(R.id.event_image);
        register_button = findViewById(R.id.event_register_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString("Title"));
            category.setText(bundle.getString("category"));
            location.setText(bundle.getString("Location"));
            date.setText(bundle.getString("Date"));
            duration.setText(bundle.getString("Duration"));
            overview.setText(bundle.getString("Overview"));
            time.setText(bundle.getString("Age"));
            language.setText(bundle.getString("Language"));
            atthendingMethod.setText(bundle.getString("attendingMethod"));
            Glide.with(this).load(bundle.getString("Image")).into(imageView);
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


