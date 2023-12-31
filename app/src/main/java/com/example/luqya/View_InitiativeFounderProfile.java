package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class View_InitiativeFounderProfile extends AppCompatActivity {

    private TextView textViewInitiativeName;
    private ImageView imageViewInitiativeLogo;
    private ProgressBar progressBar;
    private String initiativeName, initiativeDescription;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_initiative_profile);

        textViewInitiativeName = findViewById(R.id.textView_initiative_name);
        imageViewInitiativeLogo = findViewById(R.id.imageView_initiative_logo);

        getSupportActionBar().setTitle("Initiative Profile");
        progressBar = findViewById(R.id.progressBarEditProfile);

        imageViewInitiativeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(View_InitiativeFounderProfile.this, UploadProfilePicture.class);
                startActivity(i);
            }
        });

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(View_InitiativeFounderProfile.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
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

                    textViewInitiativeName.setText(initiativeName);

                    Uri uri = firebaseUser.getPhotoUrl();
                    Picasso.with(View_InitiativeFounderProfile.this).load(uri).into(imageViewInitiativeLogo);

                } else {
                    Toast.makeText(View_InitiativeFounderProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(View_InitiativeFounderProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

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

            Intent intent = new Intent(View_InitiativeFounderProfile.this, Forgot_Yor_Password.class);
            startActivity(intent);

        } else if (id == R.id.menu_item_logout) {

            authProfile.signOut();
            Toast.makeText(View_InitiativeFounderProfile.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(View_InitiativeFounderProfile.this, MainInterface.class);
            startActivity(intent);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(View_InitiativeFounderProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}