package com.example.luqya;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public void clearData() {
        this.dataList.clear();
    }

    public void addData(DataClass data) {
        this.dataList.add(data);
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        String eventName = dataList.get(holder.getAdapterPosition()).getName();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("Add Event").child(eventName);

        //Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.eventImage);
        //Uri uri = dbRef.getPhotoUrl();
        //Picasso.with(context).load(uri).into(holder.eventImage);
        holder.Title.setText(dataList.get(position).getName());
        holder.Location.setText(dataList.get(position).getLocation());
        holder.Date.setText(dataList.get(position).getDate());


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

                context.startActivity(intent);
            }
        });
        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                // Get the current user
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent(context, LogIn.class);
                    context.startActivity(intent);
                    // Handle the case where the user is not logged in
                    return;
                } else {

                    // Use the eventId of the current event
                    String eventName = dataList.get(holder.getAdapterPosition()).getName();  // replace this with your method to get the current eventId

                    // Add the current user to the attendees of the current event
                    DatabaseReference dbRef = database.getReference("EventData/" + eventName + "/attendees");
                    dbRef.child(currentUser.getUid()).setValue(true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "You've successfully registered to the event!", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "User successfully registered!");
                                    Log.d(TAG, eventName);

                                    // Navigate to the MyEventsActivity
                                    Intent intent = new Intent(context, MyEvent_bar.class);
                                    context.startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error registering user", e);
                                }
                            });
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return dataList.size();

    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView eventImage, LocationImage, DateImage;
        TextView Title;
        TextView Location;
        TextView Date;
        Button register;
        Button Details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.pic);
            LocationImage = itemView.findViewById(R.id.imageView4);
            DateImage = itemView.findViewById(R.id.imageView5);
            Title = itemView.findViewById(R.id.TitleText);
            Location = itemView.findViewById(R.id.StarText);
            Date = itemView.findViewById(R.id.Date);
            register = itemView.findViewById(R.id.Register);
            Details = itemView.findViewById(R.id.Details);

        }
    }
}
