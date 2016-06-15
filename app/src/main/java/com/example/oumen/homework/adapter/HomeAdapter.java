package com.example.oumen.homework.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oumen.homework.R;

import com.example.oumen.homework.bean.Home;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder> {
    private ArrayList<Home> homes;
    private Context cxt;
    private LayoutInflater inflater;

    public HomeAdapter(ArrayList<Home> homes, Context cxt) {
        this.homes = homes;
        this.cxt = cxt;
        inflater = LayoutInflater.from(cxt);
    }

    @Override
    public HomeAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, null);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.Viewholder holder, int position) {
        Home home = homes.get(position);
        holder.tv_name.setText(home.getUser().getNickname());
        String userAvator=home.getUser().getAvatar();
        String photo=home.getPhotos().get(0).getUrl();
        holder.user_avator.setImageURI(Uri.parse(userAvator));
        holder.photo.setImageURI(Uri.parse(photo));
        if(position%2==0){
            holder.photo.setAspectRatio(0.75f);
        }else{
            holder.photo.setAspectRatio(0.5f);
        }


    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        SimpleDraweeView user_avator;
        SimpleDraweeView photo;
        TextView tv_name;

        public Viewholder(View itemView) {
            super(itemView);
            user_avator = (SimpleDraweeView) itemView.findViewById(R.id.avator);
            photo = (SimpleDraweeView) itemView.findViewById(R.id.photo);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
