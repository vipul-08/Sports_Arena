package com.example.vipul.sports_arena;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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

    LinearLayout parent;
    Button viewFeedBack,submitFeedback,bookArena,goBack;
    TextView arenaName,arenaTime,arenaLocation;
    ImageView arenaImage;
    ArenaReviewAdapter adapter;
    List<ArenaReview> list;
    LinearLayout submitFeedbackLayot;
    RecyclerView reviews;
    ScrollView scrollView;
    String aid;
    SharedPreferences sharedPreferences;
    EditText reviewText;
    RatingBar bar;
    Button sbmtFeedback;

    LinearLayout bookingForm;
    RadioGroup kitsRequired;
    EditText numPlayers;
    DatePicker selectDate;
    TimePicker timeIn,timeOut;
    Button sbmtBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_arena_click);

        parent = findViewById(R.id.parent);

        scrollView = findViewById(R.id.scrollView);
        reviewText = findViewById(R.id.giveReview);
        bar = findViewById(R.id.ratingBar);
        sbmtFeedback = findViewById(R.id.submitFeedback);
        bookingForm = findViewById(R.id.bookForm);
        kitsRequired = findViewById(R.id.kitsreq);
        numPlayers = findViewById(R.id.numPlayers);
        selectDate = findViewById(R.id.datePick);
        timeIn = findViewById(R.id.timeIn);
        timeOut = findViewById(R.id.timeOut);
        timeIn.setIs24HourView(true);
        timeOut.setIs24HourView(true);
        sbmtBook = findViewById(R.id.submitBook);

        viewFeedBack = findViewById(R.id.clickForReviews);
        submitFeedback = findViewById(R.id.clickForSubmitFeedback);
        bookArena = findViewById(R.id.clickForBook);
        goBack = findViewById(R.id.clickForGoBack);

        reviewText = findViewById(R.id.giveReview);
        bar = findViewById(R.id.ratingBar);



        submitFeedbackLayot = findViewById(R.id.submitFeedbackLayout);

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

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arenaName.setVisibility(View.VISIBLE);
                arenaImage.setVisibility(View.VISIBLE);
                arenaTime.setVisibility(View.VISIBLE);
                arenaLocation.setVisibility(View.VISIBLE);
                submitFeedback.setVisibility(View.VISIBLE);
                bookArena.setVisibility(View.VISIBLE);
                bookingForm.setVisibility(View.GONE);
                parent.setBackgroundColor(Color.parseColor("#484848"));
                goBack.setVisibility(View.GONE);
                reviews.setVisibility(View.GONE);
                submitFeedbackLayot.setVisibility(View.GONE);
                viewFeedBack.setVisibility(View.VISIBLE);
            }
        });

        aid = getIntent().getExtras().getString("aid");

        sbmtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kit = findViewById(kitsRequired.getCheckedRadioButtonId()).getTag().toString();
                sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                String nm = sharedPreferences.getString("name","0");
                String email = sharedPreferences.getString("email","0");
                String playerNum = numPlayers.getText().toString();
                String date = String.valueOf(selectDate.getDayOfMonth())+"-"+String.valueOf(selectDate.getMonth())+"-"+String.valueOf(selectDate.getYear());
                String timing = String.valueOf(timeIn.getCurrentHour())+":"+String.valueOf(timeIn.getCurrentMinute())+"-"+String.valueOf(timeOut.getCurrentHour())+":"+String.valueOf(timeOut.getCurrentMinute());

                new AsyncTask<String,Void,Void>() {


                    @Override
                    protected Void doInBackground(String... strings) {

                        OkHttpClient client = new OkHttpClient();
                        RequestBody postData = new FormBody.Builder()
                                .add("aid",strings[0])
                                .add("kits",strings[1])
                                .add("players",strings[2])
                                .add("date",strings[3])
                                .add("time",strings[4])
                                .add("name",strings[5])
                                .add("email",strings[6])
                                .build();

                        Request request = new Request.Builder()
                                .url("http://sports-arena.stackstaging.com/app/submitBooking.php")
                                .post(postData)
                                .build();

                        try {
                            client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        startActivity(new Intent(OnArenaClick.this,MainPage.class));
                    }
                }.execute(aid,kit,playerNum,date,timing,nm,email);

                //Log.d("POSTDATA:","\n"+kit+"\n"+date+"\n"+timing+"\n"+aid+"\n"+nm+"\n"+email);
            }
        });


        viewFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.smoothScrollTo(0,0);
                arenaName.setVisibility(View.GONE);
                arenaImage.setVisibility(View.GONE);
                arenaTime.setVisibility(View.GONE);
                arenaLocation.setVisibility(View.GONE);
                submitFeedback.setVisibility(View.GONE);
                bookArena.setVisibility(View.GONE);

                reviews.setVisibility(View.VISIBLE);
                goBack.setVisibility(View.VISIBLE);

                bookingForm.setVisibility(View.GONE);
                viewFeedBack.setVisibility(View.GONE);

            }
        });

        bookArena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.smoothScrollTo(0,0);
                arenaName.setVisibility(View.GONE);
                arenaImage.setVisibility(View.GONE);
                parent.setBackgroundColor(Color.parseColor("#ffffff"));
                arenaTime.setVisibility(View.GONE);
                arenaLocation.setVisibility(View.GONE);
                submitFeedback.setVisibility(View.GONE);
                bookArena.setVisibility(View.GONE);
                bookingForm.setVisibility(View.VISIBLE);
                goBack.setVisibility(View.VISIBLE);
                viewFeedBack.setVisibility(View.GONE);

            }
        });

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.smoothScrollTo(0,0);
                arenaName.setVisibility(View.GONE);
                arenaImage.setVisibility(View.GONE);
                arenaTime.setVisibility(View.GONE);
                arenaLocation.setVisibility(View.GONE);
                submitFeedback.setVisibility(View.GONE);
                bookArena.setVisibility(View.GONE);

                submitFeedbackLayot.setVisibility(View.VISIBLE);
                goBack.setVisibility(View.VISIBLE);


                viewFeedBack.setVisibility(View.GONE);

            }
        });

        sbmtFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);

                new AsyncTask<String,Void,Void>() {

                    @Override
                    protected Void doInBackground(String... strings) {

                        OkHttpClient client = new OkHttpClient();
                        RequestBody postData = new FormBody.Builder()
                                .add("aid",strings[0])
                                .add("review",strings[2])
                                .add("rating",strings[3])
                                .add("name",strings[1])
                                .build();
                        Request request = new Request.Builder()
                                .url("http://sports-arena.stackstaging.com/app/submitFeedback.php")
                                .post(postData)
                                .build();

                        try {
                            client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {

                        startActivity(new Intent(OnArenaClick.this,MainPage.class));

                    }
                }.execute(aid,sharedPreferences.getString("name","0"),reviewText.getText().toString(),String.valueOf(bar.getRating()));
            }
        });


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
