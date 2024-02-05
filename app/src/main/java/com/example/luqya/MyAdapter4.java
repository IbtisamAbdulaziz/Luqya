package com.example.luqya;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.MyViewHolder> {

    private List<Model> attendees;
    private Context context;


    public MyAdapter4(Context context, List<Model> attendees) {
        this.context = context;
        this.attendees = attendees;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkbox_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model attendee = attendees.get(position);
        holder.nameTextView.setText(attendee.getName());
        holder.checkBox.setChecked(attendee.isSelected());

    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public CheckBox checkBox;

        public MyViewHolder(View  v) {
            super(v);
            nameTextView = v.findViewById(R.id.textView7);
            checkBox = v.findViewById(R.id.checkbox1);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isChecked = ((CheckBox) view).isChecked();
                    if (isChecked){
                        attendees.get(getAdapterPosition()).setSelected(true);
                        int points = attendees.get(getAdapterPosition()).getPoints();
                        attendees.get(getAdapterPosition()).setPoints(points+10);
                    } else {
                        attendees.get(getAdapterPosition()).setSelected(false);
                    }
                    notifyDataSetChanged();
                    for (int i = 0; i<attendees.size(); i++){
                        Log.d("TAG", attendees.toString());
                    }
                }
            });

        }

    }


}
