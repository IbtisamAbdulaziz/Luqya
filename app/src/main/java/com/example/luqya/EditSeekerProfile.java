package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditSeekerProfile extends AppCompatActivity {

    private EditText editTextUpdateName, editTextUpdateDoB, editTextUpdatePhone;
    private String textFullName, textDoB, textPhone;
    private int points;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_seeker_profile);

        getSupportActionBar().setTitle("Update Profile Details");

        progressBar =findViewById(R.id.progressBarUpdateProfile);
        editTextUpdateName = findViewById(R.id.editText_update_profile_name);
        editTextUpdateDoB = findViewById(R.id.editText_update_profile_doB);
        editTextUpdatePhone = findViewById(R.id.editText_update_profile_phone);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        showProfile(firebaseUser);

        Button buttonUploadProfilePic = findViewById(R.id.button_upload_profile_pic);
        buttonUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditSeekerProfile.this, UploadProfilePicture.class);
                startActivity(intent);
                finish();
            }
        });


        editTextUpdateDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textSADoB[] = textDoB.split("/");
                int day = Integer.parseInt(textSADoB[0]);
                int month = Integer.parseInt(textSADoB[1]);
                int year = Integer.parseInt(textSADoB[2]);

                DatePickerDialog picker;

                picker = new DatePickerDialog(EditSeekerProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        editTextUpdateDoB.setText(dayOfMonth+ "/" + (month+1) + "/" + year);
                    }
                } , year, month, day);
                picker.show();
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
        mobileMatcher = mobilePattern.matcher(textPhone);

        if (TextUtils.isEmpty(textFullName)){
            Toast.makeText(EditSeekerProfile.this, "Please enter your full name", Toast.LENGTH_LONG).show();
            editTextUpdateName.setError("Full Name is required");
            editTextUpdateName.requestFocus();

        } else if (TextUtils.isEmpty(textPhone)) {
            Toast.makeText(EditSeekerProfile.this, "Please enter your phone number", Toast.LENGTH_LONG).show();
            editTextUpdatePhone.setError("Phone number is required");
            editTextUpdatePhone.requestFocus();

        } else if (textPhone.length()!= 13 ) {
            Toast.makeText(EditSeekerProfile.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            editTextUpdatePhone.setError("Phone number should be 13 digits (+966)");
            editTextUpdatePhone.requestFocus();

        } else if (!mobileMatcher.find()) {
            Toast.makeText(EditSeekerProfile.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
            editTextUpdatePhone.setError("Phone number should be 13 digits Starting with (+966)");
            editTextUpdatePhone.requestFocus();

        } else {

            textFullName = editTextUpdateName.getText().toString();
            textDoB = editTextUpdateDoB.getText().toString();
            textPhone = editTextUpdatePhone.getText().toString();
            String userId = firebaseUser.getUid();

            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFullName, textDoB, textPhone, points,userId);
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");

            String userID = firebaseUser.getUid();
            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdates);
                        Toast.makeText(EditSeekerProfile.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditSeekerProfile.this, ShowSeekerProfile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(EditSeekerProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");
        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    textFullName = firebaseUser.getDisplayName();
                    textDoB = readUserDetails.doD;
                    textPhone = readUserDetails.phone;
                    points = readUserDetails.points;

                    editTextUpdateName.setText(textFullName);
                    editTextUpdateDoB.setText(textDoB);
                    editTextUpdatePhone.setText(textPhone);


                } else {
                    Toast.makeText(EditSeekerProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(EditSeekerProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        /*Refresh code
         * startActivity(getIntent());
         * finish();
         * overridePendingTransition(0,0);*/

        if(id == R.id.menu_item_edit_profile){

            Intent intent = new Intent(EditSeekerProfile.this, EditSeekerProfile.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.menu_item_reset_password) {

            Intent intent = new Intent(EditSeekerProfile.this, Forgot_Yor_Password.class);
            startActivity(intent);

        } else if (id == R.id.menu_item_logout) {

            authProfile.signOut();
            Toast.makeText(EditSeekerProfile.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditSeekerProfile.this, MainInterface.class);
            startActivity(intent);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(EditSeekerProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}