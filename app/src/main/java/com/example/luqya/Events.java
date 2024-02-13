package com.example.luqya;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class Events extends AppCompatActivity {

    private LinearLayout profile_Btn, home_Btn,homeBtn_3;

    private FirebaseAuth authProfile;

    List<DataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;

    public static int s_id;
    public SharedPreferences settings;
    SearchView searchView;
    MyAdapter adapter;
    private CheckBox InPerson, Online, Literary, Artistic, Musical,Scientific;

    private ImageButton notification;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        profile_Btn = findViewById(R.id.Profile_Btn);
        home_Btn = findViewById(R.id.Home_Btn);
        homeBtn_3 = findViewById(R.id.myEventsBtn);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        settings = getSharedPreferences("ID", 0);
        s_id = settings.getInt("id", 0);
        searchView = findViewById(R.id.editTextSearch);
        searchView.clearFocus();

        notification = findViewById(R.id.notification);

        InPerson = findViewById(R.id.In_person);
        Online = findViewById(R.id.online);
        Literary = findViewById(R.id.literary);
        Artistic = findViewById(R.id.artistic);
        Musical = findViewById(R.id.musical);
        Scientific = findViewById(R.id.scientific);


        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Events.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Events.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        AlertDialog.Builder builder = new AlertDialog.Builder(Events.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(Events.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Add Event");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);

                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("NewEvent", MODE_PRIVATE);
                boolean isNewEventAdded = preferences.getBoolean("isNewEventAdded", false);

                if (isNewEventAdded) {
                    String newEventName = preferences.getString("newEventName", "");
                    String newEvenDate = preferences.getString("newEvenDate", "");
                    // ... (retrieve other details as needed)

                    // Create AlertDialog with custom layout
                    AlertDialog.Builder builder = new AlertDialog.Builder(Events.this);
                    View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
                    // ... (populate dialog view with event details)

                    TextView eventNameText = dialogView.findViewById(R.id.TitleText);
                    TextView dateText = dialogView.findViewById(R.id.Date);
                    // ... (set other text views)

                    eventNameText.setText(newEventName);
                    dateText.setText(newEvenDate);
                    // ... (set other values)

                    builder.setView(dialogView);
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            preferences.edit().clear().commit(); // Clear the new event flag
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(Events.this, "There is no added event.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        // Set onClickListener for the InPerson CheckBox
        InPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

        // Set onClickListener for the Online CheckBox
        Online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

        // Add onClickListener for Literary CheckBox
        Literary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

        // Add onClickListener for Artistic CheckBox
        Artistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

        // Add onClickListener for Musical CheckBox
        Musical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

        // Add onClickListener for Scientific CheckBox
        Scientific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });

        profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseUser == null) {
                    Intent i = new Intent(Events.this, LogIn.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(Events.this, ShowSeekerProfile.class);
                    startActivity(i);
                }
            }
        });

        home_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Events.this, Events.class);
                startActivity(i);
            }
        });

        homeBtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Events.this, MyEvent_bar.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_seeker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_edit_profile){
            Intent go_edit_profile = new Intent(getApplicationContext(), EditSeekerProfile.class);
            go_edit_profile.putExtra("std_id",""+s_id);
            startActivity(go_edit_profile);
        }else if (id == R.id.action_log_out){
            getSharedPreferences("ID",0).edit().clear().commit();
            Intent go_login = new Intent(getApplicationContext(),LogIn.class);
            startActivity(go_login);
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchList(String text){
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
            if (dataClass.getName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

    // Method to filter the data based on the selected CheckBoxes
    private void filterData() {
        ArrayList<DataClass> filteredList = new ArrayList<>();
        for (DataClass data : dataList) {
            // Check if the event matches the selected criteria
            if (isEventMatchCriteria(data)) {
                filteredList.add(data);
            }
        }
        // Update the RecyclerView with the filtered list
        adapter.searchDataList(filteredList);
    }

    // Method to check if an event matches the selected criteria
    private boolean isEventMatchCriteria(DataClass data) {
        // Implement your filtering logic here based on the selected CheckBoxes
        // For example, check if the event type matches the selected criteria
        if (InPerson.isChecked() && data.getAttendingMethod().equals("In Person")) {
            return true;
        }
        if (Online.isChecked() && data.getAttendingMethod().equals("Online")) {
            return true;
        }
        if (Literary.isChecked() && data.getCategory().equals("Literature")) {
            return true;

        }
        if (Artistic.isChecked() && data.getCategory().equals("Artistic")) {
            return true;
        }
        if (Musical.isChecked() && data.getCategory().equals("Music")) {
            return true;
        }
        if (Scientific.isChecked() && data.getCategory().equals("Scientific")) {
            return true;
        }
        // Return true if no chips are selected to include the event by default
        return !InPerson.isChecked() && !Online.isChecked() && !Literary.isChecked() &&
                !Artistic.isChecked() && !Musical.isChecked() && !Scientific.isChecked();
    }
    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();// Clear focus from the SearchView
        // Optionally request focus on a different view:
        // anotherView.requestFocus();
    }
}
