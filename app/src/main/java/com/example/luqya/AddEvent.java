package com.example.luqya;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AddEvent extends AppCompatActivity {

    ImageView imageView;

   // FloatingActionButton floatingActionButton;
    EditText name, overview, date, gender, Duration, Language, age, location;
    Button submit;

    String imageURL;
    Uri uri;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);

        getSupportActionBar().setTitle("Add Event");

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.eventName);
        overview = findViewById(R.id.eventOverview);
        date = findViewById(R.id.eventDate);
        gender = findViewById(R.id.editTextGender);
        Duration = findViewById(R.id.eventDuration);
        Language = findViewById(R.id.eventLanguage);
        age = findViewById(R.id.editEventAge);
        location = findViewById(R.id.eventLocation);

        submit = findViewById(R.id.submitEvent_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEventName = name.getText().toString().trim();

                String textOverview = overview.getText().toString().trim();
                String textDate = date.getText().toString().trim();
                String textGender = gender.getText().toString().trim();
                String textDuration = Duration.getText().toString().trim();
                String textLanguage = Language.getText().toString().trim();
                String textAge = age.getText().toString().trim();
                String textLocation = location.getText().toString().trim();


                if (TextUtils.isEmpty(textEventName)) {
                    Toast.makeText(AddEvent.this, "Please enter event name", Toast.LENGTH_LONG).show();
                    name.setError("Event Name is required");
                    name.requestFocus();

                } else if (TextUtils.isEmpty(textOverview)) {
                    Toast.makeText(AddEvent.this, "Please enter event overview", Toast.LENGTH_LONG).show();
                    overview.setError("Event Overview is required");
                    overview.requestFocus();

                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddEvent.this, "Please enter event date", Toast.LENGTH_LONG).show();
                    date.setError("Event Date is required");
                    date.requestFocus();

                } else if (TextUtils.isEmpty(textGender)) {
                    Toast.makeText(AddEvent.this, "Please enter the gender", Toast.LENGTH_LONG).show();
                    gender.setError("Gender is required");
                    gender.requestFocus();


                } else if (TextUtils.isEmpty(textDuration)) {
                    Toast.makeText(AddEvent.this, "Please enter event duration", Toast.LENGTH_LONG).show();
                    Duration.setError("Event Duration is required");
                    Duration.requestFocus();


                } else if (TextUtils.isEmpty(textLanguage)) {
                    Toast.makeText(AddEvent.this, "Please enter The language", Toast.LENGTH_LONG).show();
                    Language.setError("Language is required");
                    Language.requestFocus();

                } else if (TextUtils.isEmpty(textAge)) {
                    Toast.makeText(AddEvent.this, "Please enter the age", Toast.LENGTH_LONG).show();
                    age.setError("Age is required");
                    age.requestFocus();

                } else if (TextUtils.isEmpty(textLocation)) {
                    Toast.makeText(AddEvent.this, "Please enter the location", Toast.LENGTH_LONG).show();
                    location.setError("location is required");
                    location.requestFocus();
                } else {

                    uploadData();

                }

            }

        });



        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            imageView.setImageURI(uri);
                        } else {
                            Toast.makeText(AddEvent.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

       /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveData();
            }
        });*/
    }

    public void saveData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Event Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){

        String EventName = name.getText().toString().trim();

        String Overview = overview.getText().toString().trim();

        String Date = date.getText().toString().trim();

        String Gender = gender.getText().toString().trim();

        String duration = Duration.getText().toString().trim();

        String language = Language.getText().toString().trim();

        String Age = age.getText().toString().trim();

        String Location = location.getText().toString().trim();


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Add Event");

        DataClass data = new DataClass(EventName, Overview, Date, Gender, duration, language, Age,Location, imageURL);

        databaseRef.child(EventName).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddEvent.this, "Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddEvent.this, FounderMainActivity.class);
                            startActivity(intent);

       /* FirebaseDatabase.getInstance().getReference("Add Event").child(EventName).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddEvent.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();*/
                        }
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();

               /* .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEvent.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();*/
                    }
           });
}
}