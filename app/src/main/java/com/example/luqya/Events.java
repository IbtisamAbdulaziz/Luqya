package com.example.luqya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Events extends AppCompatActivity {

    private LinearLayout profile_Btn;
    private FirebaseAuth authProfile;

    public static int s_id;
    public SharedPreferences settings;

    EditText editTextSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        profile_Btn = findViewById(R.id.Profile_Btn);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        settings = getSharedPreferences("ID", 0);
        s_id = settings.getInt("id",0);
        editTextSearch = findViewById(R.id.editTextSearch);

        profile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseUser == null){
                    Intent i = new Intent(Events.this, SignUpType.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(Events.this, ShowSeekerProfile.class);
                    startActivity(i);
                }
            }
        });

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                || event.getAction() == KeyEvent.KEYCODE_ENTER){

                    GoToSearchView();
                }
                return false;
            }
        });
    }

    private void GoToSearchView() {

        //Query here
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
        }else if (id == R.id.action_founder_log_out){
            getSharedPreferences("ID",0).edit().clear().commit();
            Intent go_login = new Intent(getApplicationContext(),LogIn.class);
            startActivity(go_login);
        }
        return super.onOptionsItemSelected(item);
    }


}
