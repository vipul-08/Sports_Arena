package com.example.vipul.sports_arena;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by vipul on 2/12/17.
 */

public class ArenaAdapter extends RecyclerView.Adapter<ArenaAdapter.ArenaViewHolder>{

    private Context mCtx;
    private List<Arena> arenaList;

    public ArenaAdapter(Context mCtx, List<Arena> arenaList) {
        this.mCtx = mCtx;
        this.arenaList = arenaList;
    }

    @Override
    public ArenaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout,null);
        view.setClickable(true);
        return new ArenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArenaViewHolder holder, int position) {

        final Arena arena = arenaList.get(position);
        holder.arenaName.setText(arena.getName());
        holder.arenaTiming.setText(arena.getTiming());
        holder.arenaLocation.setText(arena.getLocation());
        holder.arenaRating.setText(arena.getAvgRating());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCtx,OnArenaClick.class);
                intent.putExtra("aid",String.valueOf(arena.getId()));
                mCtx.startActivity(intent);

                //Toast.makeText(mCtx,"You Clicked:"+arena.getId(),Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(mCtx).load("http://sports-arena.stackstaging.com/images/"+arena.getId()+".jpg").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arenaList.size();
    }

    public class ArenaViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        RelativeLayout relativeLayout;
        TextView arenaName,arenaTiming,arenaLocation,arenaRating;

        public ArenaViewHolder(View itemView) {

            super(itemView);
            arenaRating = itemView.findViewById(R.id.avgRating);
            relativeLayout = itemView.findViewById(R.id.recyclerRelative);
            imageView=itemView.findViewById(R.id.arenaImageIcon);
            arenaName=itemView.findViewById(R.id.arenaName);
            arenaLocation=itemView.findViewById(R.id.location);
            arenaTiming=itemView.findViewById(R.id.timing);
        }
    }
}
