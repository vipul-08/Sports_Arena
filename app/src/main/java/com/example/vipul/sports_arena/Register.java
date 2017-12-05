package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class Register extends AppCompatActivity {

    EditText fname_input,lname_input,email_input,uname_input,pwd_input;
    Button reg_btn;

    android.support.v7.widget.Toolbar toolbar;

    String firstName,lastName,userName,eMail,passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname_input = (EditText) findViewById(R.id.reg_fname_input);
        lname_input = (EditText) findViewById(R.id.reg_lname_input);
        email_input = (EditText) findViewById(R.id.reg_email_input);
        uname_input = (EditText) findViewById(R.id.reg_username_input);
        pwd_input = (EditText) findViewById(R.id.reg_password_input);

        if(! isNetworkStatusAvialable (getApplicationContext())){

            new AlertDialog.Builder(this).setIcon(R.mipmap.football).setTitle("No Internet")
                    .setMessage("Please check your Internet Connection..")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                        }
                    }).show();

        }


        reg_btn = (Button) findViewById(R.id.reg_register_btn);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitle("Sports_Arena");




        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = fname_input.getText().toString();
                lastName = lname_input.getText().toString();
                userName = uname_input.getText().toString();
                eMail = email_input.getText().toString();
                passWord = pwd_input.getText().toString();

                //startActivity(new Intent(Register.this,MainPage.class));
                RegisterTask registerTask = new RegisterTask(Register.this);
                registerTask.execute(firstName,lastName,eMail,userName,passWord);
            }
        });

    }


    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
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
