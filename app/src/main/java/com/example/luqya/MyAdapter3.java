package com.example.luqya;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    public MyAdapter3(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_event_bar, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Title.setText(dataList.get(position).getName());
        holder.Date.setText(dataList.get(position).getDate());
        holder.Location.setText(dataList.get(position).getLocation());


        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Details_event_class.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getTime());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Location", dataList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("Duration", dataList.get(holder.getAbsoluteAdapterPosition()).getDuration());
                intent.putExtra("Overview", dataList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("Language", dataList.get(holder.getAbsoluteAdapterPosition()).getLanguage());
                intent.putExtra("category", dataList.get(holder.getAbsoluteAdapterPosition()).getCategory());
                intent.putExtra("attendingMethod", dataList.get(holder.getAbsoluteAdapterPosition()).getAttendingMethod());
                intent.putExtra("initiative",dataList.get(holder.getAdapterPosition()).getInitiative());
                intent.putExtra("initiativeId", dataList.get(holder.getAbsoluteAdapterPosition()).getInitativeId());

                context.startActivity(intent);
            }
        });

        // Add click listener for the cancel_register button
        holder.cancel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Cancellation")
                        .setMessage("Are you sure you want to cancel your registration for this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Proceed with cancellation logic

                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                FirebaseUser currentUser = auth.getCurrentUser();

                                if (currentUser == null) {
                                    // Handle the case where the user is not logged in
                                    return;
                                }

                                String eventName = dataList.get(holder.getAdapterPosition()).getName();
                                DatabaseReference dbRef = database.getReference("EventData/" + eventName + "/attendees");
                                dbRef.child(currentUser.getUid()).removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "User successfully unregistered!");

                                                // Update the UI or handle navigation as needed
                                                holder.cancel_register.setVisibility(View.GONE); // Hide the button after cancellation
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error unregistering user", e);

                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", null) // Dismiss the dialog on "No"
                        .show();
            }

        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Date, Location;
        Button Details;
        Button cancel_register;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.TitleText);
            Date = itemView.findViewById(R.id.Date);
            Location = itemView.findViewById(R.id.StarText);
            Details = itemView.findViewById(R.id.Details);
            cancel_register = itemView.findViewById(R.id.cancel_registeration);

        }
    }
}