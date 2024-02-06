package com.example.luqya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ShowSeekerProfile extends AppCompatActivity {

    private TextView textViewWelcome, textViewFullName, textViewDoB, textViewEmail, textViewPhone, textViewPoints ;
    private ProgressBar progressBar;
    private LinearLayout profile_Btn, Home_Btn, myEvents_Btn;
    private String fullName, email, doB, phone, pointsString;

    private int points;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_seeker_profile);

        getSupportActionBar().setTitle("My Profile");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewFullName = findViewById(R.id.textViewShowFullName);
        textViewDoB = findViewById(R.id.textViewShowDoB);
        textViewEmail = findViewById(R.id.textViewShowEmail);
        textViewPhone = findViewById(R.id.textViewShowPhone);
        textViewPoints = findViewById(R.id.textView_pts);
        imageView = findViewById(R.id.imageView);

        profile_Btn = findViewById(R.id.Profile_Btn);
        Home_Btn = findViewById(R.id.Home_Btn);
        myEvents_Btn = findViewById(R.id.myEventsBtn);
        progressBar = findViewById(R.id.progressBarEditProfile);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowSeekerProfile.this, UploadProfilePicture.class);
                startActivity(i);
            }
        });



        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(ShowSeekerProfile.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        myEvents_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseUser == null){
                    Intent i = new Intent(ShowSeekerProfile.this, LogIn.class);
                    startActivity(i);

                } else {
                    Intent intent = new Intent(ShowSeekerProfile.this, MyEvent_bar.class);
                    startActivity(intent);
                }
            }
        });

        profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseUser == null){
                    Intent i = new Intent(ShowSeekerProfile.this, LogIn.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(ShowSeekerProfile.this, ShowSeekerProfile.class);
                    startActivity(i);
                }
            }
        });

        Home_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(ShowSeekerProfile.this, Events.class);
                    startActivity(i);

            }
        });

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
                    points = readUserDetails.points;
                    pointsString = String.valueOf(points);

                    textViewWelcome.setText("Welcome, "+ fullName + "!");
                    textViewFullName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewPhone.setText(phone);
                    textViewDoB.setText(doB);

                    textViewPoints.setText(pointsString);

                    Uri uri = firebaseUser.getPhotoUrl();
                    Picasso.with(ShowSeekerProfile.this).load(uri).into(imageView);

                } else {
                    Toast.makeText(ShowSeekerProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ShowSeekerProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        /*Refresh code
        * startActivity(getIntent());
        * finish();
        * overridePendingTransition(0,0);*/

        if(id == R.id.menu_item_edit_profile){

            Intent intent = new Intent(ShowSeekerProfile.this, EditSeekerProfile.class);
            startActivity(intent);

        } else if (id == R.id.menu_item_reset_password) {

            Intent intent = new Intent(ShowSeekerProfile.this, Forgot_Yor_Password.class);
            startActivity(intent);

        } else if (id == R.id.menu_item_logout) {

            authProfile.signOut();
            Toast.makeText(ShowSeekerProfile.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ShowSeekerProfile.this, MainInterface.class);
            startActivity(intent);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(ShowSeekerProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
