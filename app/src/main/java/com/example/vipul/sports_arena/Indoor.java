package com.example.vipul.sports_arena;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by vipul on 2/12/17.
 */

public class Indoor extends Fragment {

    RecyclerView indoorRecyclerView;
    ArenaAdapter arenaAdapter;
    List<Arena> arenas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.indoor_main,container,false);


        arenas = new ArrayList<>();
        indoorRecyclerView = (RecyclerView) rootView.findViewById(R.id.indoorRecyclerView);

        indoorRecyclerView.setHasFixedSize(true);
        indoorRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        arenaAdapter = new ArenaAdapter(rootView.getContext(), arenas);

        new FetchData(rootView.getContext()).execute("indoor");
        Log.d("FIN","FINISHED");

        return rootView;
    }


    public class FetchData extends AsyncTask<String,Void,String> {

        Context context;

        FetchData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String fetch_url = "http://sports-arena.stackstaging.com/app/fetch.php";
            String type = params[0];
            Log.d("D","param"+type);

            try {

                URL url = new URL(fetch_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data = URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode(type,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();


                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String response = "";
                String line="";
                while ((line = bufferedReader.readLine())!=null) {
                    response += line;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();

                Log.d("TAG","json:"+response);

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("D","Vipul"+s);

            try {
                JSONArray jsonArray = new JSONObject(s).getJSONArray("serverRes");
                int counterr=0;
                Log.d("LEN","LENGTHS"+jsonArray.length());
                while(counterr<jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(counterr);
                    int aid = Integer.valueOf(jo.optString("aid"));
                    String aname = jo.optString("name");
                    String aloc = jo.optString("location");
                    String atim = jo.optString("timings");

                    String avgRating = jo.optString("avgRating");

                    Log.d("Arena",aid+"\n"+aname+"\n"+aloc+"\n"+atim);

                    arenas.add(new Arena(aid, aname, aloc, atim,avgRating));
                    counterr++;
                    arenaAdapter.notifyDataSetChanged();
                    if(counterr == jsonArray.length()-1) {
                        indoorRecyclerView.setAdapter(arenaAdapter);

                    }

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
