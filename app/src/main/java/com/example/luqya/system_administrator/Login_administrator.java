package com.example.luqya.system_administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luqya.LogIn;
import com.example.luqya.R;

public class Login_administrator extends AppCompatActivity {

    EditText editText_email,editText_pass;
    public String username_or_email,password;
    TextView textView_foget_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_administrator);

        editText_email = (EditText)findViewById(R.id.edittext_email_login);
        editText_pass = (EditText)findViewById(R.id.edittext_password_login);
        textView_foget_pass = (TextView)findViewById(R.id.textview_forget_pass);


        textView_foget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_or_email = editText_email.getText().toString();

                if (username_or_email.isEmpty()){
                    Toast.makeText(Login_administrator.this, "Enter your username or email!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    public void LogInAdministrator(final View view){

        username_or_email = editText_email.getText().toString();
        password = editText_pass.getText().toString();

        if (username_or_email.isEmpty() | password.isEmpty()){
            Toast.makeText(Login_administrator.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}