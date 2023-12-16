package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity {

    private EditText editTextFullName, editTextDateOfBirth, editTextEmail,
            editTextPhone, editTextUsername, editTextPassword, editTextPassword2;

    private RadioGroup userTypeRadioGroup;
    private RadioButton userType;
    private static final String TAG= "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Signing Up");

        editTextFullName = findViewById(R.id.edittext_name);
        editTextDateOfBirth = findViewById(R.id.edittext_age);
        editTextEmail = findViewById(R.id.edittext_email);
        editTextPhone  = findViewById(R.id.editText_phone);
        editTextUsername = findViewById(R.id.edittext_username);
        editTextPassword  = findViewById(R.id.editText_password);
        editTextPassword2  = findViewById(R.id.editText_passowrd2);

        userTypeRadioGroup = findViewById(R.id.radio_group);
        userTypeRadioGroup.clearCheck();

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
                    editTextPhone.setError("Username is required");
                    editTextPhone.requestFocus();

                } else if (TextUtils.isEmpty(textPhone)) {
                    Toast.makeText(SignUp.this, "Please enter your phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number is required");
                    editTextPhone.requestFocus();

                } else if (textPhone.length()!= 13 ) {
                    Toast.makeText(SignUp.this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number should be 13 digits (+966)");
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
                }
            }
        });

    }

    private void registerUser(String textFullName, String textDoB, String textEmail, String textPhone, String textUserName, String textPassowrd, String textUserType) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassowrd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "User Registered successfully", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    firebaseUser.sendEmailVerification();

                    Intent intent = new Intent(SignUp.this,LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();
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