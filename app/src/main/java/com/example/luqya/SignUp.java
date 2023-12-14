package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText editTextFullName, editTextDateOfBirth, editTextEmail,
            editTextPhone, editTextUsername, editTextPassword, editTextPassword2;

    private RadioGroup userTypeRadioGroup;
    private RadioButton userType;

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

                }

            }
        });

    }
}