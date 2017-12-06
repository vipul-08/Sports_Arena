package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

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

public class RegisterTask extends AsyncTask<String,Void,String>{

    AlertDialog alertDialog;

    Context context;

    RegisterTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Registration Details");
        Register.avlRegister.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        String reg_url = "http://sports-arena.stackstaging.com/app/register.php";
        String fname = strings[0];
        String lname = strings[1];
        String email = strings[2];
        String uname = strings[3];
        String pwd = strings[4];


        try {
            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            String data = URLEncoder.encode("fname_input","UTF-8") + "=" + URLEncoder.encode(fname,"UTF-8") + "&" + URLEncoder.encode("lname_input","UTF-8") + "=" + URLEncoder.encode(lname,"UTF-8") + "&" + URLEncoder.encode("email_input","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&" +URLEncoder.encode("uname_input","UTF-8") + "=" + URLEncoder.encode(uname,"UTF-8") + "&" +URLEncoder.encode("pwd_input","UTF-8") + "=" + URLEncoder.encode(pwd,"UTF-8");
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
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
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

        Register.avlRegister.setVisibility(View.GONE);
        if(s.equals("1")) {
            context.startActivity(new Intent(context,MainActivity.class));
        }
        else {
            alertDialog.setMessage(s);
            alertDialog.show();
        }
    }


}
