package com.example.luqya;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
    ArrayList<String> nameArray = new ArrayList<String>();
    ArrayList<String> pointsArray = new ArrayList<String>();

    boolean isOnTextChanged = false;
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
        holder.pointEditText.setText("0");
        holder.checkBox.setSelected(false);


        holder.pointEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOnTextChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(isOnTextChanged){
                    isOnTextChanged = false;

                    try {

                        String points = holder.pointEditText.getText().toString();


                    }catch (NumberFormatException e){
                        Toast.makeText(context, "You should enter a number (an integer) ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

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
        public String points;
        public MyViewHolder(View  v) {
            super(v);
           // nameTextView = v;
            nameTextView = v.findViewById(R.id.textView7);
            checkBox = v.findViewById(R.id.checkbox1);
            pointEditText = v.findViewById(R.id.Point);
            points = pointEditText.getText().toString();
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }


}
