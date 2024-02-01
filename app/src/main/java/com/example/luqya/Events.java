package com.example.luqya;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

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
    //FloatingActionButton filter;
    MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        profile_Btn = findViewById(R.id.Profile_Btn);
        home_Btn = findViewById(R.id.Home_Btn);
        homeBtn_3 = findViewById(R.id.homeBtn_3);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        settings = getSharedPreferences("ID", 0);
        s_id = settings.getInt("id", 0);
        searchView =findViewById(R.id.editTextSearch);
        searchView.clearFocus();
        //filter =findViewById(R.id.Filter);


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

        /*filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Events.this,Filter.class);
                startActivityForResult(intent,101);
            }
        });*/

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

    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();// Clear focus from the SearchView
        // Optionally request focus on a different view:
        // anotherView.requestFocus();
    }

}
