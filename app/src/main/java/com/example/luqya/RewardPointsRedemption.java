package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RewardPointsRedemption extends AppCompatActivity {

    TextView holderNameTextView, partnerNameTextView, issueDateTextView;
    String partnerName, seekerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_points_redemption);

        getSupportActionBar().setTitle("Reward Coupon");


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            partnerName = bundle.getString("partner");
            seekerName = bundle.getString("name");
        }

        holderNameTextView = findViewById(R.id.holder_name_textView);
        partnerNameTextView = findViewById(R.id.partner_name_textView);
        issueDateTextView = findViewById(R.id.issue_date_textView);

        holderNameTextView.setText(seekerName);
        partnerNameTextView.setText(partnerName);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE dd/MM/yyyy hh:mm");
        String date = formatter.format(today);

        issueDateTextView.setText(date);

    }
}