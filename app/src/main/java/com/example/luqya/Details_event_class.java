package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Details_event_class extends AppCompatActivity {




        TextView Title, Location, Duration,Overview,Gender,Age,Language;
        Button Register;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details_event_class);

            Title = findViewById(R.id.TitleText);
            Location = findViewById(R.id.StarText);
            Duration = findViewById(R.id.Duration);
            Overview = findViewById(R.id.Overview);
            Gender = findViewById(R.id.Gender);
            Age = findViewById(R.id.Age);
            Language = findViewById(R.id.Language);

            Register = findViewById(R.id.Register);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                Title.setText(bundle.getString("Title"));
                Location.setText(bundle.getString("Location"));
                Duration.setText(bundle.getString("Duration"));
                Overview.setText(bundle.getString("Overview"));
                Location.setText(bundle.getString("Location"));
                Gender.setText(bundle.getString("Gender"));
                Age.setText(bundle.getString("Age"));
                Language.setText(bundle.getString("Language"));

            }
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


