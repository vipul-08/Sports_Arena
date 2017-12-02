package com.example.vipul.sports_arena;

import android.content.Context;
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
import android.widget.TextView;

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
        return new ArenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArenaViewHolder holder, int position) {

        Arena arena = arenaList.get(position);
        holder.arenaName.setText(arena.getName());
        holder.arenaTiming.setText(arena.getTiming());
        holder.arenaLocation.setText(arena.getLocation());

        new DownloadImageTask(holder.imageView).execute("http://sports-arena.stackstaging.com/images/"+arena.getId()+".jpg");


    }

    @Override
    public int getItemCount() {
        return arenaList.size();
    }

//    public static Bitmap getBitmapFromURL(String src) {
//
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream is = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(is);
//            return myBitmap;
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public class ArenaViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView arenaName,arenaTiming,arenaLocation;

        public ArenaViewHolder(View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.arenaImageIcon);
            arenaName=itemView.findViewById(R.id.arenaName);
            arenaLocation=itemView.findViewById(R.id.location);
            arenaTiming=itemView.findViewById(R.id.timing);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {

            this.bmImage = bmImage;

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
