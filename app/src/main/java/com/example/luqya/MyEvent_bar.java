package com.example.luqya;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

public class MyEvent_bar extends AppCompatActivity {

    private static final String TAG = "MyEvent_bar";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_bar);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Get the current user
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not logged in
            Log.d(TAG, "User is not logged in");
            return;
        }

        Log.d(TAG, "User UID: " + currentUser.getUid());

        // Get a reference to the "EventData" node in your Firebase Realtime Database
        DatabaseReference eventDataRef = database.getReference("EventData");
        DatabaseReference addEventRef = database.getReference("Add Event");

        // Initialize the RecyclerView and the Adapter
        RecyclerView recyclerView = findViewById(R.id.myeventView);
        List<DataClass> dataList = new ArrayList<>();
        MyAdapter3 myAdapter3 = new MyAdapter3(this, dataList);
        recyclerView.setAdapter(myAdapter3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach a listener to read the data at our posts reference
        eventDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataList.clear();

                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    // Check if the current user is an attendee of the event
                    DataSnapshot attendeesSnapshot = eventSnapshot.child("attendees");
                    if (attendeesSnapshot.hasChild(currentUser.getUid())) {
                        // The current user is an attendee of the event
                        Log.d(TAG, "User is an attendee of the event: " + eventSnapshot.getKey());

                        // Get the event name
                        String eventName = eventSnapshot.getKey();

                        // Get the event data from the "Add Event" node
                        addEventRef.child(eventName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get the event data
                                DataClass event = dataSnapshot.getValue(DataClass.class);

                                // Update the data in the adapter
                                dataList.add(event);

                                // Notify the adapter that the data has changed
                                myAdapter3.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
