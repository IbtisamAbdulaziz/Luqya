package com.example.luqya;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.MyViewHolder> {

    private List<Model> attendees;

    public MyAdapter4(List<Model> attendees) {
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
        // Set other data points if needed (e.g., initial checkbox state, point value)
    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public CheckBox checkBox;
        public EditText pointEditText; // Assuming you want to access the EditText
        public MyViewHolder(View  v) {
            super(v);
           // nameTextView = v;
            nameTextView = v.findViewById(R.id.textView7);
            checkBox = v.findViewById(R.id.checkbox1);
            pointEditText = v.findViewById(R.id.Point);
        }
    }
}
