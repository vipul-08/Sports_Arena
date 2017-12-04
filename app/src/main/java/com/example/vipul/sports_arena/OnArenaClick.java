package com.example.vipul.sports_arena;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

public class OnArenaClick extends AppCompatActivity {


    Button viewFeedBack,submitFeedback,bookArena,goBack;

    TextView arenaName,arenaTime,arenaLocation;
    ImageView arenaImage;

    ArenaReviewAdapter adapter;
    List<ArenaReview> list;

    RecyclerView reviews;

    String aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_arena_click);

        viewFeedBack = findViewById(R.id.clickForReviews);
        submitFeedback = findViewById(R.id.clickForSubmitFeedback);
        bookArena = findViewById(R.id.clickForBook);
        goBack = findViewById(R.id.clickForGoBack);

        reviews = (RecyclerView) findViewById(R.id.arenaReviewList);
        reviews.setHasFixedSize(false);
        reviews.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new ArenaReviewAdapter(this,list);
        arenaName = (TextView) findViewById(R.id.nameOfArena);
        arenaImage = (ImageView) findViewById(R.id.imageOfArena);
        arenaTime = (TextView) findViewById(R.id.timingOfArena);
        arenaLocation = (TextView) findViewById(R.id.locationOfArena);
        reviews.setAdapter(adapter);
//        list.add(new ArenaReview("Vipul","Nice","5"));
//        adapter.notifyDataSetChanged();
//
//        list.add(new ArenaReview("Vipul","Nice","5"));
//        adapter.notifyDataSetChanged();
//        list.add(new ArenaReview("Vipul","Nice","5"));
//        adapter.notifyDataSetChanged();
//        list.add(new ArenaReview("Vipul","Nice","5"));
//        adapter.notifyDataSetChanged();



        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arenaName.setVisibility(View.VISIBLE);
                arenaImage.setVisibility(View.VISIBLE);
                arenaTime.setVisibility(View.VISIBLE);
                arenaLocation.setVisibility(View.VISIBLE);
                submitFeedback.setVisibility(View.VISIBLE);
                bookArena.setVisibility(View.VISIBLE);

                goBack.setVisibility(View.GONE);
                reviews.setVisibility(View.GONE);


                viewFeedBack.setVisibility(View.VISIBLE);
            }
        });

        viewFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arenaName.setVisibility(View.GONE);
                arenaImage.setVisibility(View.GONE);
                arenaTime.setVisibility(View.GONE);
                arenaLocation.setVisibility(View.GONE);
                submitFeedback.setVisibility(View.GONE);
                bookArena.setVisibility(View.GONE);

                reviews.setVisibility(View.VISIBLE);
                goBack.setVisibility(View.VISIBLE);


                viewFeedBack.setVisibility(View.GONE);

            }
        });




        //Bundle extras = getIntent().getExtras();
        aid = getIntent().getExtras().getString("aid");

        Log.d("PASSED VALUE","pValue"+aid);

        new AsyncTask<String, Void, String>() {



            @Override
            protected String doInBackground(String... strings) {

                String postData = strings[0];
                try
                {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody postWebData = new FormBody.Builder().add("aid", postData).build();

                    Request request = new Request.Builder().url("http://sports-arena.stackstaging.com/app/onclick.php").post(postWebData).build();

                    Response response = client.newCall(request).execute();
                    String result = response.body().string();

                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                try{

                    JSONArray jsonArray1 = new JSONObject(s).getJSONArray("arenaDetail");
                    int counter = 0;
                    while (counter < jsonArray1.length()) {

                        JSONObject jo = jsonArray1.getJSONObject(counter);
                        arenaName.setText(jo.optString("name"));
                        arenaTime.setText("Timings: "+jo.optString("timings"));
                        arenaLocation.setText("Address: "+jo.optString("location"));
                        Glide.with(OnArenaClick.this).load("http://sports-arena.stackstaging.com/images/" + aid + ".jpg").into(arenaImage);
                        counter++;
                    }

                    JSONArray jsonArray2 = new JSONObject(s).getJSONArray("arenaFeedback");
                    int counter2 = 0;
                    while (counter2 < jsonArray2.length()) {
                        JSONObject jo2 = jsonArray2.getJSONObject(counter2);
                        Log.d("LISTOFFEEDBACK",jo2.optString("client")+"\n"+jo2.optString("review")+"\n"+jo2.optString("star"));
                        list.add(new ArenaReview(jo2.optString("client"),jo2.optString("review"),jo2.optString("star")));
                        adapter.notifyDataSetChanged();
                        //reviews.setAdapter(adapter);
                        counter2++;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(aid);
    }
}
