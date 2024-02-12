package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewInitiatives extends AppCompatActivity {

    private ListView initiativesListView;

    DatabaseReference reference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_initiatives);
        getSupportActionBar().setTitle("Registered initiatives");

        initiativesListView = (ListView) findViewById(R.id.initiativesList);
        progressBar = findViewById(R.id.progressBarViewInitiatives);

        reference = FirebaseDatabase.getInstance().getReference("Registered initiatives");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        initiativesListView.setAdapter(arrayAdapter);

        progressBar.setVisibility(View.VISIBLE);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(ReadWriteUserDetails.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        initializeListView();

    }

    private void initializeListView() {

    }
}