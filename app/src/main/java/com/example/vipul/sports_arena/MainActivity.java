package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.effect.Effect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView reg;
    EditText username_input,password_input;
    Button login_btn;
    Toolbar toolbar;
    String username , password;

    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Sports_Arena");

        reg = (TextView) findViewById(R.id.register_btn);
        login_btn = (Button) findViewById(R.id.login_btn);

        username_input = (EditText) findViewById(R.id.username_input);
        password_input = (EditText) findViewById(R.id.password_input);

        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        login = sharedPreferences.getString("login","0");
        editor = sharedPreferences.edit();

        if("1".equals(login)) {
            startActivity(new Intent(MainActivity.this,MainPage.class));
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,MainPage.class));

                username = username_input.getText().toString();
                password = password_input.getText().toString();

                LoginTask loginTask = new LoginTask(MainActivity.this);
                loginTask.execute(username,password);

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.mipmap.football).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
