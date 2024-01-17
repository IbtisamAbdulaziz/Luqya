package com.example.luqya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

    private Chip In_person, online;
    private Chip literary, artistic, musical, scientific;
    private Button btnApply;
    private ArrayList<String> selectedChipData;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);

        In_person = findViewById(R.id.In_person);
        online = findViewById(R.id.online);

        literary = findViewById(R.id.literary);
        artistic = findViewById(R.id.artistic);
        musical = findViewById(R.id.musical);
        scientific = findViewById(R.id.scientific);

        selectedChipData = new ArrayList<>();
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selectedChipData.add(buttonView.getText().toString());

                }
                else
                {
                    selectedChipData.remove(buttonView.getText().toString());

                }
            }
        };

        In_person.setOnCheckedChangeListener(checkedChangeListener);
        online.setOnCheckedChangeListener(checkedChangeListener);
        literary.setOnCheckedChangeListener(checkedChangeListener);
        artistic.setOnCheckedChangeListener(checkedChangeListener);
        musical.setOnCheckedChangeListener(checkedChangeListener);
        scientific.setOnCheckedChangeListener(checkedChangeListener);

        btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resulyIntent = new Intent();
                resulyIntent.putExtra("data", selectedChipData.toString());
                setResult(101,resulyIntent);
                finish();
            }
        });

    }
}
