package com.example.luqya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitiativeFounderSignUp extends AppCompatActivity {

    private EditText initiativeNameEditText, initiativeFounderNameEditText, initiativeEmailEditText, initiativeDescriptionEditText,
    passwordEditText, passwordConfirmationEditText, phoneEditText, locationEditText;
    private Button cancelBtn, signUpBtn;
    private ProgressBar progressBar;
    private static final String TAG= "InitiativeFounderSignUp";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiative_founder_signup);

        getSupportActionBar().setTitle("Sign Up");

        initiativeNameEditText = findViewById(R.id.edittext_initiative_name);
        initiativeFounderNameEditText = findViewById(R.id.edittext_initiative_founder);
        initiativeEmailEditText = findViewById(R.id.edittext_email_login);
        passwordEditText = findViewById(R.id.edittext_password_login);
        passwordConfirmationEditText = findViewById(R.id.edittext_password_confirmation);
        phoneEditText = findViewById(R.id.editText_phone);
        locationEditText = findViewById(R.id.editText_location);
        initiativeDescriptionEditText = findViewById(R.id.eventOverview);

        cancelBtn = findViewById(R.id.button_cansel);
        signUpBtn = findViewById(R.id.button_sign_up);
        progressBar = findViewById(R.id.progressBar);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitiativeFounderSignUp.this, LogIn.class);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textInitiativeName = initiativeNameEditText.getText().toString();
                String textInitiativeFounderName = initiativeFounderNameEditText.getText().toString();
                String textInitiativeEmail = initiativeEmailEditText.getText().toString();
                String textInitiativeDescription = initiativeDescriptionEditText.getText().toString();

                String textPassowrd = passwordEditText.getText().toString();
                String textPasswordConfirmation = passwordConfirmationEditText.getText().toString();

                String textInitiativePhone = phoneEditText.getText().toString();
                String textInitiativeLocation = locationEditText.getText().toString();

                String mobileRegex = "[+][0-9]{12}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textInitiativePhone);

                if (TextUtils.isEmpty(textInitiativeName)){
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter initiative name", Toast.LENGTH_LONG).show();
                    initiativeNameEditText.setError("Initiative Name is required");
                    initiativeNameEditText.requestFocus();

                } else if (TextUtils.isEmpty(textInitiativeFounderName)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter initiative founder name", Toast.LENGTH_LONG).show();
                    initiativeFounderNameEditText.setError("Initiative Founder Name is required");
                    initiativeFounderNameEditText.requestFocus();

                } else if (TextUtils.isEmpty(textInitiativeDescription)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter initiative description", Toast.LENGTH_LONG).show();
                    initiativeDescriptionEditText.setError("Initiative Overview is required");
                    initiativeDescriptionEditText.requestFocus();

            } else if (TextUtils.isEmpty(textInitiativeEmail)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter initiative email", Toast.LENGTH_LONG).show();
                    initiativeEmailEditText.setError("Initiative Email address is required");
                    initiativeEmailEditText.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(textInitiativeEmail).matches()) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                    initiativeEmailEditText.setError("Valid email is required");
                    initiativeEmailEditText.requestFocus();


                } else if (TextUtils.isEmpty(textInitiativePhone)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter your phone number", Toast.LENGTH_LONG).show();
                    phoneEditText.setError("Phone number is required");
                    phoneEditText.requestFocus();

                } else if (textInitiativePhone.length()!= 13 ) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                    phoneEditText.setError("Phone number should be 13 digits (+966)");
                    phoneEditText.requestFocus();

                } else if (!mobileMatcher.find()) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                    phoneEditText.setError("Phone number should start with (+966)");
                    phoneEditText.requestFocus();

                } else if (TextUtils.isEmpty(textInitiativeLocation)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter initiative location", Toast.LENGTH_LONG).show();
                    locationEditText.setError("Initiative location is required");
                    locationEditText.requestFocus();


                } else if (TextUtils.isEmpty(textPassowrd)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter a password", Toast.LENGTH_LONG).show();
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();

                } else if (textPassowrd.length() < 8) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
                    passwordEditText.setError("Password too weak");
                    passwordEditText.requestFocus();

                } else if (TextUtils.isEmpty(textPasswordConfirmation)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    passwordConfirmationEditText.setError("Password confirmation is required");
                    passwordConfirmationEditText.requestFocus();

                } else if (!textPassowrd.equals(textPasswordConfirmation)) {
                    Toast.makeText(InitiativeFounderSignUp.this, "Please enter the same password", Toast.LENGTH_LONG).show();
                    passwordConfirmationEditText.setError("Password confirmation is required");
                    passwordConfirmationEditText.requestFocus();
                    passwordEditText.setText(" ");
                    passwordConfirmationEditText.setText(" ");
                } else {

                    registerInitiative(textInitiativeName, textInitiativeFounderName, textInitiativeEmail, textInitiativePhone, textInitiativeLocation, textPassowrd, textInitiativeDescription);
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void registerInitiative(String textInitiativeName, String textInitiativeFounderName, String textInitiativeEmail, String textInitiativePhone, String textInitiativeLocation, String textPassowrd, String textDescription) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        auth.createUserWithEmailAndPassword(textInitiativeEmail, textPassowrd).addOnCompleteListener(InitiativeFounderSignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textInitiativeName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textInitiativeName, textInitiativeFounderName, textInitiativePhone, textInitiativeLocation, textDescription);
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered initiatives");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                firebaseUser.sendEmailVerification();
                                Toast.makeText(InitiativeFounderSignUp.this, "User Registered Successfully. Please verify your email", Toast.LENGTH_LONG).show();
                                DocumentReference df = fStore.collection("Users").document(firebaseUser.getUid());
                                Map<String,Object> userInfo = new HashMap<>();
                                userInfo.put("InitiativeName",textInitiativeName);
                                userInfo.put("InitiativeFounderName", textInitiativeFounderName);
                                userInfo.put("InitiativeEmail", textInitiativeEmail);
                                userInfo.put("InitiativePhone", textInitiativePhone);
                                userInfo.put("InitiativeLocation", textInitiativeLocation);
                                userInfo.put("InitiativeDescription", textDescription);
                                userInfo.put("UserType","2");

                                df.set(userInfo);
                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(InitiativeFounderSignUp.this,LogIn.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(InitiativeFounderSignUp.this, "User Registered Failed. Please try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        passwordEditText.setError("Your password is too weak. Kindly use a mix of alphabets, numbers " +
                                "and special characters");
                        passwordEditText.requestFocus();
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        initiativeEmailEditText.setError("Your email is invalid or already in use. Kindly re-enter.");
                        initiativeEmailEditText.requestFocus();
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (FirebaseAuthUserCollisionException e){
                        initiativeEmailEditText.setError("User is already registered with this email. use another email.");
                        initiativeEmailEditText.requestFocus();
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(InitiativeFounderSignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

    }
}
