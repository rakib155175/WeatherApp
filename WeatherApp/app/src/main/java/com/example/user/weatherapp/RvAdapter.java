package com.example.user.weatherapp;

import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private String dateString,state,high,low,iconID;
    private int time;
    private String BASE_URL_ICON="https://openweathermap.org/img/w/";
    List<com.example.user.weatherapp.ForecastWeather.List> list;

    public RvAdapter(List<com.example.user.weatherapp.ForecastWeather.List> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_single_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     state=list.get(position).getWeather().get(0).getDescription();
     holder.stateTV1.setText(state);
     if(MainActivity.format==1){
         high=String.format("%.0f",list.get(position).getMain().getTempMax());
         low=String.format("%.0f",list.get(position).getMain().getTempMin());
         holder.hlTv1.setText(String.format(high+"/"+low)+"\u2103");
     }
     if(MainActivity.format==2){
         high=String.format("%.0f",((list.get(position).getMain().getTempMax())*9/5)+32);
         low=String.format("%.0f",((list.get(position).getMain().getTempMin())*9/5)+32);
            holder.hlTv1.setText(String.format(high+"/"+low)+"\u2109");
        }


        holder.dateTv1.setText(list.get(position).getDtTxt());

        iconID=list.get(position).getWeather().get(0).getIcon();
        String icon=String.format(BASE_URL_ICON)+iconID+".png";
        Picasso.get().load(icon).into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView1;
        TextView stateTV1,dateTv1,hlTv1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1=itemView.findViewById(R.id.imageview1);
            stateTV1=itemView.findViewById(R.id.stateTV1);
            dateTv1=itemView.findViewById(R.id.dateTV1);
            hlTv1=itemView.findViewById(R.id.hlTV1);
        }
    }
}
