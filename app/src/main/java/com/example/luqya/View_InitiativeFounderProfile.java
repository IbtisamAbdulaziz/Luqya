package com.example.luqya;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class View_InitiativeFounderProfile extends AppCompatActivity {

    private TextView textViewInitiativeName, textViewInitiativeOverview, textViewInitiativeFounder, textViewInitiativePhone,
            textViewInitiativeAddress, textViewInitiativeEmail;
    private ImageView imageViewInitiativeLogo, insagramPic;
    private ProgressBar progressBar;
    private String initiativeName, initiativeDescription, initiativeFounder, initiativePhone,
    initiativeAddress, initiativeEmail;
    private FirebaseAuth authProfile;
    RecyclerView recyclerView;

    List<DataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchInitiative;
    MyAdapter adapter;
    String initiativeId;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_initiative_profile);

        Bundle bundle = getIntent().getExtras();
        initiativeId = bundle.getString("initiativeId");


        textViewInitiativeName = findViewById(R.id.textView_initiative_name);
        textViewInitiativeOverview = findViewById(R.id.textView_Initiative_overview);
        textViewInitiativeFounder = findViewById(R.id.textView_show_founder_name);
        textViewInitiativePhone = findViewById(R.id.textView_show_initiative_phone);
        textViewInitiativeAddress = findViewById(R.id.textView_show_initiative_address);

        searchInitiative = findViewById(R.id.Search_initiative);
        searchInitiative.clearFocus();

        imageViewInitiativeLogo = findViewById(R.id.imageView_initiative_logo2);
        insagramPic = findViewById(R.id.instagram);

        insagramPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference accountRef = database.getReference("Registered initiatives");

                // Retrieve account name from Firebase
                accountRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String accountName = dataSnapshot.getValue(String.class);

                            // Use the retrieved account name in the link
                            String sAppLink = "https://www.instagram.com/" + accountName;
                            String sPackage = "com.instagram.android";

                            // Call method
                            openLink(v.getContext(), sAppLink, sPackage, sAppLink);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void openLink(Context context, String sAppLink, String sPackage, String sWebLink) {
                // Use try catch
                try {
                    // When application is installed
                    // Initialize uri
                    Uri uri = Uri.parse(sAppLink);
                    // Initialize intent
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    // Set data
                    intent.setData(uri);
                    // Set package
                    intent.setPackage(sPackage);
                    // Set flag
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // Start activity
                    context.startActivity(intent);
                } catch (ActivityNotFoundException activityNotFoundException) {
                    // Open link in browser
                    // Initialize uri
                    Uri uri = Uri.parse(sWebLink);
                    // Initialize intent
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    // Set data
                    intent.setData(uri);
                    // Set flag
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
   }
}
        });
        getSupportActionBar().setTitle("Initiative Profile");
        progressBar = findViewById(R.id.progressBarEditProfile);

        // To Display a events added by Initiative in its Profile.

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(View_InitiativeFounderProfile.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(View_InitiativeFounderProfile.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(View_InitiativeFounderProfile.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(View_InitiativeFounderProfile.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Add Event");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);

                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        imageViewInitiativeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(View_InitiativeFounderProfile.this, UploadInitiativePicture.class);
                startActivity(i);
            }
        });

        authProfile = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        showUserProfile();


        searchInitiative.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);

                return true;
            }
        });
    }

    private void searchList(String newText) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList) {
            if (dataClass.getName().toLowerCase().contains(newText.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

    private void showUserProfile() {

        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        authProfile = FirebaseAuth.getInstance();
        String userID = initiativeId;

        if(firebaseUser != null ) {
            if(firebaseUser.getUid().equals(Objects.requireNonNull(authProfile.getCurrentUser()).getUid())) {
                userID = firebaseUser.getUid();
            }
        }


        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered initiatives");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);

                if(readUserDetails != null){
                    initiativeName = readUserDetails.initiativeName;
                    initiativeDescription = readUserDetails.initiativeOverView;
                    initiativeFounder = readUserDetails.initiativeFounderName;
                    initiativePhone = readUserDetails.initiativePhone;
                    initiativeAddress = readUserDetails.initiativeLocation;
                    textViewInitiativeName.setText(initiativeName);
                    textViewInitiativeOverview.setText(initiativeDescription);
                    textViewInitiativeFounder.setText(initiativeFounder);
                    textViewInitiativePhone.setText(initiativePhone);
                    textViewInitiativeAddress.setText(initiativeAddress);

                    //Uri uri = firebaseUser.getPhotoUrl();
                    //Picasso.with(View_InitiativeFounderProfile.this).load(uri).into(imageViewInitiativeLogo);

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
    @Override
    protected void onResume() {
        super.onResume();
        searchInitiative.clearFocus();// Clear focus from the SearchView
        // Optionally request focus on a different view:
        // anotherView.requestFocus();
    }
}