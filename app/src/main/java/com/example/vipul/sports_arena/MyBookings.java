package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyBookings extends AppCompatActivity {

    RecyclerView bookingsView;
    MyBookingsAdapter adapter;
    List<Bookings> bookingsList;
    SharedPreferences sharedPreferences;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bookingsView = findViewById(R.id.listBookings);
        bookingsList = new ArrayList<>();
        adapter = new MyBookingsAdapter(MyBookings.this,bookingsList);
        bookingsView.setAdapter(adapter);

        bookingsView.setHasFixedSize(true);
        bookingsView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);


        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {

                Log.d("Parameter", "param:" + strings[0]);
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody postData = new FormBody.Builder().add("email", strings[0]).build();
                    Request request = new Request.Builder().url("http://sports-arena.stackstaging.com/app/myBookings.php").post(postData).build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.d("result", "result:" + result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                Log.d("Response_SERVER", "Response" + s);
                try {
                    JSONArray jsonArray = new JSONObject(s).getJSONArray("serverResponse");
                    int counter = 0;
                    while (counter < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(counter);
                        bookingsList.add(new Bookings(jsonObject.optString("arenaId"), jsonObject.optString("arenaName"), jsonObject.optString("dateBooking"), jsonObject.optString("timeBooking"), jsonObject.optString("numPlayers"), jsonObject.optString("kitStatus")));
                        adapter.notifyDataSetChanged();
                        counter++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute(sharedPreferences.getString("email", "0"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        finish();

    }
}