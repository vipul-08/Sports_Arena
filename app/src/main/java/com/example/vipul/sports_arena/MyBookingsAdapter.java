package com.example.vipul.sports_arena;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by vipul on 5/12/17.
 */

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.MyBookingsHolder>{


    private Context mContext;
    private List<Bookings> bookingsList;

    public MyBookingsAdapter(Context mContext, List<Bookings> bookingsList) {
        this.mContext = mContext;
        this.bookingsList = bookingsList;
    }

    @Override
    public MyBookingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.booking_card,null);
        view.setClickable(false);
        return new MyBookingsHolder(view);
    }

    @Override
    public void onBindViewHolder(MyBookingsHolder holder, int position) {
        final Bookings bookings = bookingsList.get(position);

        Glide.with(mContext).load("http://sports-arena.stackstaging.com/images/"+bookings.getArenaId()+".jpg").into(holder.arenaBookedImage);
        holder.arenaBookedName.setText(bookings.getArenaName());
        holder.arenaBookedDate.setText("Date: "+bookings.getDateBooking());
        holder.arenaBookedTiming.setText("Timings: "+bookings.getTimeBooking());
        holder.arenaBookedNumPlayers.setText("No. of players: "+bookings.getNumPlayers());
        holder.arenaBookedKitStatus.setText("Kits Requested: "+bookings.getKitStatus());
    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    class MyBookingsHolder extends RecyclerView.ViewHolder {

        ImageView arenaBookedImage;
        TextView arenaBookedName,arenaBookedTiming,arenaBookedDate,arenaBookedNumPlayers,arenaBookedKitStatus;
        public MyBookingsHolder(View itemView) {
            super(itemView);
            arenaBookedImage = itemView.findViewById(R.id.arenaBookedImage);
            arenaBookedName = itemView.findViewById(R.id.arenaBookedName);
            arenaBookedDate = itemView.findViewById(R.id.arenaBookedDate);
            arenaBookedTiming = itemView.findViewById(R.id.arenaBookedTiming);
            arenaBookedNumPlayers = itemView.findViewById(R.id.arenaBookedNumPlayers);
            arenaBookedKitStatus = itemView.findViewById(R.id.arenaBookedKitStatus);
        }
    }

}
