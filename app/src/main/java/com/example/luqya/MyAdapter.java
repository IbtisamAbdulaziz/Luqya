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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.eventImage);
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
                intent.putExtra("attendingMethod", dataList.get(holder.getAbsoluteAdapterPosition()).getAttendingMeth());
                intent.putExtra("initiative",dataList.get(holder.getAdapterPosition()).getInitiative());

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
