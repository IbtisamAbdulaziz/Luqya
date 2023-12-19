package com.example.luqya;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Model> arrayList;

    public MyAdapter(AttendingListAndPoint context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.checkbox_row, parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = arrayList.get(position);
        holder.textView.setText(model.getName());
        holder.checkbox.setChecked(model.isSelected());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkbox;
        private ConstraintLayout rowitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.textView7);
            this.rowitem = itemView.findViewById(R.id.rowitem);
            this.checkbox = itemView.findViewById(R.id.checkbox1);

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();
                    if (isChecked) {
                        arrayList.get(getAdapterPosition()).setSelected(true);
                    } else {
                        arrayList.get(getAdapterPosition()).setSelected(false);
                    }
                    notifyDataSetChanged();
                    for (int i = 0; i < arrayList.size(); i++) {
                        Log.d("TAG", arrayList.toString());
                    }
                }
            });
            rowitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, String.valueOf(arrayList.get(getAdapterPosition())),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
