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
        gender = findViewById(R.id.eventGender);
        Duration = findViewById(R.id.eventDuration);
        Language = findViewById(R.id.eventLanguage);
        age = findViewById(R.id.eventEge);
        location = findViewById(R.id.eventLocation);

        submit = findViewById(R.id.submitEvent_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEventName = name.getText().toString();
                String textOverview = overview.getText().toString();
                String textDate = date.getText().toString();
                String textGender = gender.getText().toString();
                String textDuration = Duration.getText().toString();
                String textLanguage = Language.getText().toString();
                String textAge = age.getText().toString();
                String textLocation = location.getText().toString();


                if (TextUtils.isEmpty(textEventName)) {
                    Toast.makeText(AddEvent.this, "Please enter event name", Toast.LENGTH_LONG).show();
                    name.setError("Initiative Name is required");
                    name.requestFocus();

                } else if (TextUtils.isEmpty(textOverview)) {
                    Toast.makeText(AddEvent.this, "Please enter initiative founder name", Toast.LENGTH_LONG).show();
                    overview.setError("Initiative Founder Name is required");
                    overview.requestFocus();

                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(AddEvent.this, "Please enter initiative email", Toast.LENGTH_LONG).show();
                    date.setError("Initiative Email address is required");
                    date.requestFocus();

                } else if (TextUtils.isEmpty(textGender)) {
                    Toast.makeText(AddEvent.this, "Please enter your phone number", Toast.LENGTH_LONG).show();
                    gender.setError("Phone number is required");
                    gender.requestFocus();


                } else if (TextUtils.isEmpty(textDuration)) {
                    Toast.makeText(AddEvent.this, "Please enter initiative location", Toast.LENGTH_LONG).show();
                    Duration.setError("Initiative location is required");
                    Duration.requestFocus();


                } else if (TextUtils.isEmpty(textLanguage)) {
                    Toast.makeText(AddEvent.this, "Please enter a password", Toast.LENGTH_LONG).show();
                    Language.setError("Password is required");
                    Language.requestFocus();

                } else if (TextUtils.isEmpty(textAge)) {
                    Toast.makeText(AddEvent.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    age.setError("Password confirmation is required");
                    age.requestFocus();

                } else if (TextUtils.isEmpty(textLocation)) {
                    Toast.makeText(AddEvent.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    location.setError("Password confirmation is required");
                    location.requestFocus();
                } else {

                    DataClass dataClass = new DataClass(textEventName, textOverview, textDate, textGender, textDuration, textLanguage, textAge,textLocation);
                }

            }

        });



  /*  private void AddEvents(String textEventName, String textOverview, String textDate, String textGender, String textDuration, String textLanguage, String textAge, String textLocation) {


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    }*/
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
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
        String textEventName = name.getText().toString();
        String textOverview = overview.getText().toString();
        String textDate = date.getText().toString();
        String textGender = gender.getText().toString();
        String textDuration = Duration.getText().toString();
        String textLanguage = Language.getText().toString();
        String textAge = age.getText().toString();
        String textLocation = location.getText().toString();

        DataClass dataClass = new DataClass(textEventName, textOverview, textDate, textGender, textDuration, textLanguage, textAge,textLocation);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(currentDate)

                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddEvent.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEvent.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
           });
}
}