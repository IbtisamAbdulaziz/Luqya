package com.example.luqya;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Profile extends AppCompatActivity {

    private TextView textViewWelcome, textViewFullName, textViewDoB, textViewEmail, textViewPhone ;
    private ProgressBar progressBar;
    private String fullName, email, doB, phone;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        getSupportActionBar().setTitle("My Profile");

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewFullName = findViewById(R.id.textViewShowFullName);
        textViewDoB = findViewById(R.id.textViewShowDoB);
        textViewEmail = findViewById(R.id.textViewShowEmail);
        textViewPhone = findViewById(R.id.textViewShowPhone);

        progressBar = findViewById(R.id.progressBarEditProfile);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(Edit_Profile.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    fullName = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    doB = readUserDetails.doD;
                    phone = readUserDetails.phone;

                    textViewWelcome.setText("Welcome, "+ fullName + "!");
                    textViewFullName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewPhone.setText(phone);
                    textViewDoB.setText(doB);
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Edit_Profile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
