package com.example.luqya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

public class FounderMainActivity extends AppCompatActivity {
    private ImageView Initiative_Logo;
    private TextView textViewInitiativeName, textViewInitiativeFounder;
    private Button Initiative_Profile, Add_Event, My_Events, Edit_Profile, Statistics;
    private String initiativeName, initiativeFounder;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.founder_main);
        getSupportActionBar().setTitle("Initiative Dashboard");

        Initiative_Logo = findViewById(R.id.imageView);

        textViewInitiativeName = findViewById(R.id.textView);
        textViewInitiativeFounder = findViewById(R.id.textView_founder_name_welcome);

        Initiative_Profile = findViewById(R.id.button);
        Add_Event = findViewById(R.id.button2);
        My_Events = findViewById(R.id.button3);
        Edit_Profile = findViewById(R.id.button4);
        Statistics = findViewById(R.id.button5);
        progressBar = findViewById(R.id.progressBarFounderMainActivity);


        Add_Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FounderMainActivity.this,AddEvent.class);
                startActivity(intent);
            }
        });

        Initiative_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FounderMainActivity.this ,View_InitiativeFounderProfile.class);
                startActivity(intent1);
            }
        });

        Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FounderMainActivity.this, Edit_InitiativeFounderProfile.class);
                startActivity(intent);
            }
        });
        My_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FounderMainActivity.this, My_Events.class);
                startActivity(intent);
            }
        });

        Initiative_Logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FounderMainActivity.this, UploadInitiativePicture.class);
                startActivity(i);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(FounderMainActivity.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered initiatives");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);

                if(readUserDetails != null){
                    initiativeName = readUserDetails.initiativeName;
                    initiativeFounder = readUserDetails.initiativeFounderName;

                    textViewInitiativeName.setText(initiativeName);
                    textViewInitiativeFounder.setText("Welcome back " + initiativeFounder + " !");



                    Uri uri = firebaseUser.getPhotoUrl();
                    Picasso.with(FounderMainActivity.this).load(uri).into(Initiative_Logo);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FounderMainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FounderMainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_founder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_reset_password) {

            Intent intent = new Intent(FounderMainActivity.this, Forgot_Yor_Password.class);
            startActivity(intent);

        } else if (id == R.id.menu_item_logout) {

            authProfile.signOut();
            Toast.makeText(FounderMainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FounderMainActivity.this, MainInterface.class);
            startActivity(intent);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(FounderMainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}

