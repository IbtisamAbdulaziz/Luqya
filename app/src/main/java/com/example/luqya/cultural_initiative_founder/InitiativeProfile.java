package com.example.luqya;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InitiativeProfile extends AppCompatActivity {

    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiative_profile);

        searchView = findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // يمكنك تنفيذ البحث أثناء كتابة النص هنا، إذا رغبت
                return false;
            }
        });
    }

    private void performSearch(String query) {
        // هنا يمكنك تنفيذ البحث الفعلي والتعامل مع النتائج
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
    }
}