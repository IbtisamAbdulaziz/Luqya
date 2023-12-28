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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;

public class Forgot_Yor_Password extends AppCompatActivity {

    private EditText editTextEmail;
    private Button btnResetPass;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private final  static String TAG = "Forgot_Yor_Password";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_your_password);
        getSupportActionBar().setTitle("Forgot Password");

        editTextEmail = findViewById(R.id.edit_text_email_resetPass);
        btnResetPass = findViewById(R.id.recover_btn);
        progressBar = findViewById(R.id.progressBarResetPass);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Forgot_Yor_Password.this, "Please enter your registered email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Forgot_Yor_Password.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Valid email is required");
                    editTextEmail.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
            }
        });

    }

    private void resetPassword(String email) {
        authProfile = FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot_Yor_Password.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Forgot_Yor_Password.this, MainInterface.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    try{
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthInvalidUserException e){
                        editTextEmail.setError("User does not exists or is no longer valid. Please register again.");
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Forgot_Yor_Password.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
