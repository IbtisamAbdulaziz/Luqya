package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText editTextFullName, editTextDateOfBirth, editTextEmail,
            editTextPhone, editTextUsername, editTextPassword, editTextPassword2;

    private RadioGroup userTypeRadioGroup;
    private RadioButton userType;
    private ProgressBar progressBar;
    private static final String TAG= "SignUp";
    private  DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Signing Up");


        progressBar = findViewById(R.id.progressBar);
        editTextFullName = findViewById(R.id.edittext_name);
        editTextDateOfBirth = findViewById(R.id.edittext_age);
        editTextEmail = findViewById(R.id.edittext_email);
        editTextPhone  = findViewById(R.id.editText_phone);
        editTextUsername = findViewById(R.id.edittext_username);
        editTextPassword  = findViewById(R.id.editText_password);
        editTextPassword2  = findViewById(R.id.editText_passowrd2);

        userTypeRadioGroup = findViewById(R.id.radio_group);
        userTypeRadioGroup.clearCheck();

        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        editTextDateOfBirth.setText(dayOfMonth+ "/" + (month+1) + "/" + year);
                    }
                } , year, month, day);
                picker.show();
            }
        });

        Button saveBtn = findViewById(R.id.button_save);
        Button cancelBtn = findViewById(R.id.button_cansel);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedUserTypeId = userTypeRadioGroup.getCheckedRadioButtonId();
                userType = findViewById(selectedUserTypeId);

                String textFullName = editTextFullName.getText().toString();
                String textDoB = editTextDateOfBirth.getText().toString();
                String textEmail = editTextEmail.getText().toString();
                String textPhone = editTextPhone.getText().toString();
                String textUserName = editTextUsername.getText().toString();
                String textPassowrd = editTextPassword.getText().toString();
                String textPassword2 = editTextPassword2.getText().toString();
                String textUserType;

                String mobileRegex = "[+][0-9]{12}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textPhone);

                if (TextUtils.isEmpty(textFullName)){
                    Toast.makeText(SignUp.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextFullName.setError("Full Name is required");
                    editTextFullName.requestFocus();

                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignUp.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignUp.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                    editTextEmail.setError("Valid email is required");
                    editTextEmail.requestFocus();

                } else if (userTypeRadioGroup.getCheckedRadioButtonId() == -1 ) {
                    Toast.makeText(SignUp.this, "Please select a user type", Toast.LENGTH_LONG).show();
                    userType.setError("User type is required");
                    userType.requestFocus();

                } else if (TextUtils.isEmpty(textUserName)) {
                    Toast.makeText(SignUp.this, "Please enter a username", Toast.LENGTH_LONG).show();
                    editTextUsername.setError("Username is required");
                    editTextUsername.requestFocus();

                } else if (TextUtils.isEmpty(textPhone)) {
                    Toast.makeText(SignUp.this, "Please enter your phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number is required");
                    editTextPhone.requestFocus();

                } else if (textPhone.length()!= 13 ) {
                    Toast.makeText(SignUp.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number should be 13 digits (+966)");
                    editTextPhone.requestFocus();

                } else if (!mobileMatcher.find()) {
                    Toast.makeText(SignUp.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number should be 13 digits Starting with (+966)");
                    editTextPhone.requestFocus();


            } else if (TextUtils.isEmpty(textPassowrd)) {
                    Toast.makeText(SignUp.this, "Please enter a password", Toast.LENGTH_LONG).show();
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();

                } else if (textPassowrd.length() < 8) {
                    Toast.makeText(SignUp.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
                    editTextPassword.setError("Password too weak");
                    editTextPassword.requestFocus();

                } else if (TextUtils.isEmpty(textPassword2)) {
                    Toast.makeText(SignUp.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextPassword2.setError("Password confirmation is required");
                    editTextPassword2.requestFocus();

                } else if (!textPassowrd.equals(textPassword2)) {
                    Toast.makeText(SignUp.this, "Please enter the same password", Toast.LENGTH_LONG).show();
                    editTextPassword2.setError("Password confirmation is required");
                    editTextPassword2.requestFocus();
                    editTextPassword.clearComposingText();
                    editTextPassword2.clearComposingText();
                } else {

                    textUserType = userType.getText().toString();
                    registerUser(textFullName, textDoB, textEmail, textPhone, textUserName, textPassowrd, textUserType);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });

    }



    private void registerUser(String textFullName, String textDoB, String textEmail, String textPhone, String textUserName, String textPassowrd, String textUserType) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassowrd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDoB, textUserName, textUserType, textPhone);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                firebaseUser.sendEmailVerification();
                                Toast.makeText(SignUp.this, "User Registered Successfully. Please verify your email", Toast.LENGTH_LONG).show();
                                DocumentReference df = fStore.collection("Users").document(firebaseUser.getUid());
                                Map<String,Object> userInfo = new HashMap<>();
                                userInfo.put("FullName",textFullName);
                                userInfo.put("UserEmail", textEmail);
                                userInfo.put("PhoneNumber", textPhone);

                                userInfo.put("isUser","1");

                                df.set(userInfo);

                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(SignUp.this,LogIn.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(SignUp.this, "User Registered Failed. Please try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        editTextPassword.setError("Your password is too weak. Kindly use a mix of alphabets, numbers " +
                                "and special characters");
                        editTextPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTextEmail.setError("Your email is invalid or already in use. Kindly re-enter.");
                        editTextEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e){
                        editTextEmail.setError("User is already registered with this email. use another email.");
                        editTextEmail.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}