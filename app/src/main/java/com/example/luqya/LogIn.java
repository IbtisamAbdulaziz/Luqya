package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;

    private FirebaseAuth authProfile;
    private TextView textview_signup;
    private static final String TAG = "LogIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        getSupportActionBar().setTitle("Login");
        textview_signup = findViewById(R.id.textview_signup);
        editTextLoginEmail = findViewById(R.id.edittext_email_login);
        editTextLoginPwd = findViewById(R.id.edittext_password_login);
        progressBar = findViewById(R.id.progressBarLogin);

        ImageView imageViewShowHidePwd = findViewById(R.id.show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        authProfile = FirebaseAuth.getInstance();

        Button btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPwd = editTextLoginPwd.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LogIn.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LogIn.this, "The email is incorrect", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Valid email is required");
                    editTextLoginEmail.requestFocus();

                } else if(TextUtils.isEmpty(textPwd)){
                    Toast.makeText(LogIn.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextLoginPwd.setError("Password is required");
                    editTextLoginPwd.requestFocus();

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail , textPwd);
                }

            }
        });


        textview_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogIn.this,SignUp.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(LogIn.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                    }
                } else {



                    try{

                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e){
                        editTextLoginEmail.setError("User does not exist or is no longer valid. Please register again.");
                        editTextLoginEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e ){
                        editTextLoginEmail.setError("Invalid credentials. Kindly, check and re-enter.");
                        editTextLoginEmail.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please Verify your email now. You can not login without email verification.");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged In!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, Events.class);
            startActivity(intent);
            finish();
        }

        else {
            Toast.makeText(this, "You can login now!", Toast.LENGTH_SHORT).show();
        }
    }
}