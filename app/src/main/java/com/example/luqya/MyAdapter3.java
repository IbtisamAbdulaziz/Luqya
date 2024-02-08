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

        DataClass data = dataList.get(position);
        if (data != null) {
            holder.Title.setText(data.getName());
            holder.Location.setText(data.getLocation());
            holder.Date.setText(data.getDate());

            holder.Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Details_event_class.class);
                    intent.putExtra("Image", data.getDataImage());
                    intent.putExtra("Date", data.getDate());
                    intent.putExtra("Time", data.getTime());
                    intent.putExtra("Title", data.getName());
                    intent.putExtra("Location", data.getLocation());
                    intent.putExtra("Duration", data.getDuration());
                    intent.putExtra("Overview", data.getOverview());
                    intent.putExtra("Language", data.getLanguage());
                    intent.putExtra("category", data.getCategory());
                    intent.putExtra("attendingMethod", data.getAttendingMethod());
                    intent.putExtra("initiative", data.getInitiative());
                    context.startActivity(intent);
                }
            });

            holder.cancel_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Confirm Cancellation")
                            .setMessage("Are you sure you want to cancel your registration for this event?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    FirebaseUser currentUser = auth.getCurrentUser();

                                    if (currentUser == null) {
                                        return;
                                    }

                                    String eventName = data.getName();
                                    DatabaseReference dbRef = database.getReference("EventData/" + eventName + "/attendees");
                                    dbRef.child(currentUser.getUid()).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "User successfully unregistered!");
                                                    holder.cancel_register.setVisibility(View.GONE);
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
                            .setNegativeButton("No", null)
                            .show();
                }

            });
        }
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
