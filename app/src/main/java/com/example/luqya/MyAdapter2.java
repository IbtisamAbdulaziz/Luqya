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
        holder.Date.setText(dataList.get(position).getDate());


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
                intent.putExtra("Gender", dataList.get(holder.getAdapterPosition()).getGender());
                intent.putExtra("Overview", dataList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("Language", dataList.get(holder.getAdapterPosition()).getLanguage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView eventImage, LocationImage, DateImage;
        TextView Title,Overview,Language,Age,Duration;
        TextView Location;
        TextView Gender;
        TextView Date;
        Button edit_event;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.pic);
            LocationImage = itemView.findViewById(R.id.imageView4);
            DateImage = itemView.findViewById(R.id.imageView5);
            Title = itemView.findViewById(R.id.TitleText);
            Location = itemView.findViewById(R.id.StarText);
            Date = itemView.findViewById(R.id.Date);
            Gender = itemView.findViewById(R.id.Date);
            Overview = itemView.findViewById(R.id.Date);
            Language= itemView.findViewById(R.id.Date);
            Age =  itemView.findViewById(R.id.Date);
            Duration = itemView.findViewById(R.id.Date);
        }
    }
}
