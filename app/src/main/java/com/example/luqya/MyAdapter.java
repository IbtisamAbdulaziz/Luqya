package com.example.luqya;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_events, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.eventImage);
        holder.Title.setText(dataList.get(position).getTitle());
        holder.Location.setText(dataList.get(position).getLocation());
        holder.Date.setText(dataList.get(position).getDate());
        holder.register.setText(dataList.get(position).getRegister());
        holder.Details.setText(dataList.get(position).getDetails());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details_event_class.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Language", dataList.get(holder.getAdapterPosition()).getDataLang());
                context.startActivity(intent);
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
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView eventImage, LocationImage, DateImage;
        TextView Title;
        TextView Location;
        TextView Date;
        Button register;
        Button Details;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage=itemView.findViewById(R.id.pic);
            LocationImage=itemView.findViewById(R.id.imageView4);
            DateImage=itemView.findViewById(R.id.imageView5);
            Title=itemView.findViewById(R.id.TitleText);
            Location=itemView.findViewById(R.id.StarText);
            Date=itemView.findViewById(R.id.Date);
            register=itemView.findViewById(R.id.Register);
            Details=itemView.findViewById(R.id.Details);
            cardView=itemView.findViewById(R.id.CardViewID);

        }
    }

}