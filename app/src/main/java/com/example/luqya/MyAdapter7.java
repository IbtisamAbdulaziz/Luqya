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

public class MyAdapter7 extends RecyclerView.Adapter<MyAdapter7.MyViewHolder> {

    private Context context;
    private List<ReadWriteUserDetails> dataList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    public MyAdapter7(Context context, List<ReadWriteUserDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seekers_names_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.seekerName.setText(dataList.get(position).getFullName());


        holder.delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure? You want do delete this user? This cannot be undone.");
                builder.setTitle("Confirm Delete");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Registered users").child(dataList.get(holder.getAbsoluteAdapterPosition()).getFullName());
                        databaseRef.removeValue();
                        Toast.makeText(context, "user has been deleted successfully.", Toast.LENGTH_SHORT).show();
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

        TextView seekerName ;

        Button delete_user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            seekerName = itemView.findViewById(R.id.seeker_name_textView);
            delete_user =itemView.findViewById(R.id.delete_seeker);
        }
    }
}
