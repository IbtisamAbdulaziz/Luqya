package com.example.luqya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RedeemPoints extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int points;
    private ListView partnersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_points);

        getSupportActionBar().setTitle("Our Partners");
        partnersListView = findViewById(R.id.partner_list_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            points = Integer.parseInt(bundle.getString("points"));
        }

        partnersListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        String itemList = (String) parent.getItemAtPosition(position);
        Toast.makeText(this, "List of Item is : " + itemList, Toast.LENGTH_SHORT).show();
    }
}