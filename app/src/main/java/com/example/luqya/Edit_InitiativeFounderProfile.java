package com.example.luqya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edit_InitiativeFounderProfile extends AppCompatActivity {

    private EditText editTextUpdateInitiativeName, editTextUpdateInitiativeFounderName, editTextUpdateInitiativeDescription,
            editTextUpdateInitiativePhone, editTextUpdateInitiativeLocation;
    private String textInitiativeName, textInitiativeFounderName, textInitiativeDescription, textInitiativePhone, textInitiativeLocation;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_initiative_founder_profile);

        getSupportActionBar().setTitle("Update Initiative Details");
        progressBar = findViewById(R.id.progressBarEditInitiativeProfile);
        editTextUpdateInitiativeName = findViewById(R.id.editText_update_initiative_name);
        editTextUpdateInitiativeFounderName = findViewById(R.id.editText_update_initiative_founder_name);
        editTextUpdateInitiativeDescription = findViewById(R.id.editText_update_initiative_description);
        editTextUpdateInitiativePhone = findViewById(R.id.editText_update_initiative_phone);
        editTextUpdateInitiativeLocation = findViewById(R.id.editText_update_initiative_location);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        showProfile(firebaseUser);

        Button buttonUploadProfilePic = findViewById(R.id.button_upload_profile_pic);
        buttonUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_InitiativeFounderProfile.this, UploadProfilePicture.class);
                startActivity(intent);
                finish();
            }
        });


        Button buttonUpdateProfile = findViewById(R.id.button_update_profile);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
            }
        });

    }
    private void updateProfile(FirebaseUser firebaseUser) {

        String mobileRegex = "[+][0-9]{12}";
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textInitiativePhone);

        if (TextUtils.isEmpty(textInitiativeName)){
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter initiative name", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativeName.setError("Initiative Name is required");
            editTextUpdateInitiativeName.requestFocus();

        } else if (TextUtils.isEmpty(textInitiativeFounderName)){
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter initiative founder name", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativeFounderName.setError("Initiative Founder Name is required");
            editTextUpdateInitiativeFounderName.requestFocus();

        } else if (TextUtils.isEmpty(textInitiativeDescription)) {
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter initiative description", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativeDescription.setError("Initiative Description is required");
            editTextUpdateInitiativeDescription.requestFocus();

        } else if (TextUtils.isEmpty(textInitiativePhone)) {
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter initiative phone number", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativePhone.setError("Phone number is required");
            editTextUpdateInitiativePhone.requestFocus();

        } else if (textInitiativePhone.length()!= 13 ) {
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativePhone.setError("Phone number should be 13 digits (+966)");
            editTextUpdateInitiativePhone.requestFocus();

        } else if (!mobileMatcher.find()) {
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativePhone.setError("Phone number should be 13 digits Starting with (+966)");
            editTextUpdateInitiativePhone.requestFocus();

        } else if (TextUtils.isEmpty(textInitiativeLocation)) {
            Toast.makeText(Edit_InitiativeFounderProfile.this, "Please enter initiative location", Toast.LENGTH_LONG).show();
            editTextUpdateInitiativeLocation.setError("Initiative Location is required");
            editTextUpdateInitiativeLocation.requestFocus();

        } else {

            textInitiativeName = editTextUpdateInitiativeName.getText().toString();
            textInitiativeFounderName = editTextUpdateInitiativeFounderName.getText().toString();
            textInitiativeDescription = editTextUpdateInitiativeDescription.getText().toString();
            textInitiativeLocation = editTextUpdateInitiativeLocation.getText().toString();
            textInitiativePhone = editTextUpdateInitiativePhone.getText().toString();

            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textInitiativeName, textInitiativeFounderName, textInitiativePhone, textInitiativeLocation, textInitiativeDescription );
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered initiatives");

            String userID = firebaseUser.getUid();
            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(textInitiativeName).build();
                        firebaseUser.updateProfile(profileUpdates);

                        Toast.makeText(Edit_InitiativeFounderProfile.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Edit_InitiativeFounderProfile.this, View_InitiativeFounderProfile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(Edit_InitiativeFounderProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    progressBar.setVisibility(View.GONE);

                }
            });

            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void showProfile(FirebaseUser firebaseUser) {

        String userIDofRegistered = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered initiatives");
        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){

                    editTextUpdateInitiativeName.setText(textInitiativeName);
                    editTextUpdateInitiativeFounderName.setText(textInitiativeFounderName);
                    editTextUpdateInitiativeDescription.setText(textInitiativeDescription);
                    editTextUpdateInitiativePhone.setText(textInitiativePhone);
                    editTextUpdateInitiativeLocation.setText(textInitiativeLocation);


                } else {
                    Toast.makeText(Edit_InitiativeFounderProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Edit_InitiativeFounderProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

}
