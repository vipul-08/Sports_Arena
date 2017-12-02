package com.example.vipul.sports_arena;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.effect.Effect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView reg;
    EditText username_input,password_input;
    Button login_btn;


    String username , password;

    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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








//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.putString("login","1");
//                editor.apply();
//                startActivity(new Intent(MainActivity.this,MainPage.class));
//            }
//        });



    }
}
