package com.example.luqya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RedeemPoints extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int points;
    private ListView partnersListView;
    private FirebaseAuth authProfile;
    private  FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_points);

        getSupportActionBar().setTitle("Our Partners");
        partnersListView = findViewById(R.id.partner_list_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            points = bundle.getInt("points");
        }

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        partnersListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        String itemList = (String) parent.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(RedeemPoints.this);
        builder.setMessage("You can only Collect your reward from " + itemList + ", 100 points will be redeemed.");
        builder.setTitle("Confirm Redeem Points");
        builder.setCancelable(false);
        builder.setPositiveButton("Redeem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String userID = firebaseUser.getUid();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered users");

                referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                        if(readUserDetails != null){

                            String name = readUserDetails.fullName;
                            points = readUserDetails.points;
                            readUserDetails.setPoints(points-100);
                            String seekerName = readUserDetails.fullName;

                            String userID = firebaseUser.getUid();
                            referenceProfile.child(userID).setValue(readUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(seekerName).build();
                                        firebaseUser.updateProfile(profileUpdates);
                                    } else {
                                        try {
                                            throw task.getException();
                                        } catch (Exception e){
                                            Toast.makeText(RedeemPoints.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }
                            });

                            Intent intent = new Intent(RedeemPoints.this, RewardPointsRedemption.class);
                            intent.putExtra("name", name);
                            intent.putExtra("partner", itemList);
                            Toast.makeText(RedeemPoints.this, "100 Points have redeemed successfully!", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            } else {
                            Toast.makeText(RedeemPoints.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(RedeemPoints.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}