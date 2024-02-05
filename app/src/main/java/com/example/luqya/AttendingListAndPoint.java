package com.example.luqya;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.UserWriteRecord;

import java.util.ArrayList;
import java.util.Objects;

public class AttendingListAndPoint extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> arrayList = new ArrayList<>();
    MyAdapter4 adapter;
    Button saveButton;
    ProgressBar progressBarAttendingList;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attending_list_and_point);

        // Get the event name from the intent
        eventName = getIntent().getStringExtra("eventName");

        recyclerView = findViewById(R.id.list_seeker);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter4(this, arrayList);
        recyclerView.setAdapter(adapter);
        saveButton = findViewById(R.id.save_attendees_button);
        progressBarAttendingList = findViewById(R.id.progressBarAttendingList);
        progressBarAttendingList.setVisibility(View.GONE);



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Get the current user
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not logged in
            Log.d(TAG, "User is not logged in");
            return;
        }

        // Get a reference to the "EventData" node in your Firebase Realtime Database
        DatabaseReference eventDataRef = database.getReference("EventData").child(eventName).child("attendees");
        DatabaseReference usersRef = database.getReference("Registered users");

        // Attach a listener to read the data at our posts reference
        eventDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot attendeeSnapshot: dataSnapshot.getChildren()) {
                    // Get the user ID of the attendee
                    String userId = attendeeSnapshot.getKey();

                    // Get the user's name from the "Registered users" node
                    usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get the user's name
                            String userName = dataSnapshot.child("fullName").getValue(String.class);
                            int points = dataSnapshot.child("points").getValue(Integer.class);


                            // Create a new Model object for the attendee
                            Model attendee = new Model(userId, userName, false, points);

                            // Update the data in the adapter
                            arrayList.add(attendee);

                            // Notify the adapter that the data has changed
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBarAttendingList.setVisibility(View.VISIBLE);

                for (int i =0; i < arrayList.size(); i++){

                    Model attendee = arrayList.get(i);
                    int points = attendee.getPoints();


                    usersRef.child(attendee.getUserId()).child("points").setValue(points);

                }

                progressBarAttendingList.setVisibility(View.GONE);
                Toast.makeText(AttendingListAndPoint.this, "Saved!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
