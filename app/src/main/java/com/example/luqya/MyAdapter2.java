package com.example.luqya;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter2(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
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
                Intent intent = new Intent(context, Details_event_class.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Location", dataList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("Duration", dataList.get(holder.getAdapterPosition()).getDuration());
                intent.putExtra("Age", dataList.get(holder.getAdapterPosition()).getAge());
                intent.putExtra("Overview", dataList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("Language", dataList.get(holder.getAdapterPosition()).getLanguage());
                intent.putExtra("category", dataList.get(holder.getAdapterPosition()).getCategory());
             //   intent.putExtra("attendingMeth", dataList.get(holder.getAdapterPosition()).getAttendingMeth());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title;

        Button edit_event,Delete_Event, Attending_List;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.TitleText);
            edit_event=itemView.findViewById(R.id.edit_event);
            Delete_Event=itemView.findViewById(R.id.delete_event);
            Attending_List=itemView.findViewById(R.id.attending_List);

        }
    }
}
