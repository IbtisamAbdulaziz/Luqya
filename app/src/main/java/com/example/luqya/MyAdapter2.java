package com.example.luqya;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    public MyAdapter2(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_event, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Title.setText(dataList.get(position).getName());


        holder.edit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseRef = firebaseDatabase.getReference("Add Event");
                String eventName = databaseRef.child(dataList.get(holder.getAbsoluteAdapterPosition()).getName()).toString();*/

                Intent intent = new Intent(context, EditEvent.class);
                intent.putExtra("eventName", dataList.get(holder.getAbsoluteAdapterPosition()).getName());
                //intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());'

                intent.putExtra("eventDate", dataList.get(holder.getAbsoluteAdapterPosition()).getDate());
                intent.putExtra("eventTitle", dataList.get(holder.getAbsoluteAdapterPosition()).getName());
                intent.putExtra("eventLocation", dataList.get(holder.getAbsoluteAdapterPosition()).getLocation());
                intent.putExtra("eventDuration", dataList.get(holder.getAbsoluteAdapterPosition()).getDuration());
                intent.putExtra("eventTime", dataList.get(holder.getAbsoluteAdapterPosition()).getTime());
                intent.putExtra("eventOverview", dataList.get(holder.getAbsoluteAdapterPosition()).getOverview());
                intent.putExtra("eventLanguage", dataList.get(holder.getAbsoluteAdapterPosition()).getLanguage());
                intent.putExtra("eventCategory", dataList.get(holder.getAbsoluteAdapterPosition()).getCategory());
                intent.putExtra("eventAttendingMethod", dataList.get(holder.getAbsoluteAdapterPosition()).getAttendingMeth());

                context.startActivity(intent);
            }
        });

        holder.Delete_Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure? You want do delete this event?");
                builder.setTitle("Confirm Delete");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Add Event");
                        databaseRef.child(databaseRef.getKey()).removeValue();
                        Toast.makeText(context, "Event has been deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title;

        Button edit_event, Delete_Event,  Attending_List;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.TitleText);
            edit_event=itemView.findViewById(R.id.edit_event);
            Delete_Event=itemView.findViewById(R.id.delete_event);
            Attending_List=itemView.findViewById(R.id.attending_List);

        }
    }
}
