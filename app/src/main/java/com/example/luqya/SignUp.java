package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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


            }
        });

    }
}