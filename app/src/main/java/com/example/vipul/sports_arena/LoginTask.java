package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

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

/**
 * Created by vipul on 1/12/17.
 */

public class LoginTask extends AsyncTask<String,Void,String> {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog;
    Context context;


    String username,password;

    LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Details");
        MainActivity.avlLogin.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {

        String login_url = "http://sports-arena.stackstaging.com/app/login.php";
        username = strings[0];
        password = strings[1];


        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            String data = URLEncoder.encode("username_input","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&" + URLEncoder.encode("password_input","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            InputStream is = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine())!=null) {
                response+= line;
            }
            bufferedReader.close();
            is.close();
            httpURLConnection.disconnect();
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

        MainActivity.avlLogin.setVisibility(View.GONE);
        JSONObject json;
        try {
            json = new JSONObject(s);
            if(json.optString("val").equals("1")) {

                sharedPreferences = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("login","1");
                editor.putString("name",json.optString("name"));
                editor.putString("email",json.optString("email"));
                editor.putString("username",username);
                editor.putString("password",password);
                editor.apply();
                context.startActivity(new Intent(context,MainPage.class));
            }
            else {
                alertDialog.setMessage("Invalid Credentials");
                alertDialog.show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
