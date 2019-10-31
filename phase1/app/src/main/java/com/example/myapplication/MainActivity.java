package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button createAccount;
    Button login;
    Button stat;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAccount = findViewById(R.id.createAccount);
        login = findViewById(R.id.login);
        stat = findViewById(R.id.userInfo);
        exit = findViewById(R.id.exit);
        createAccount.setOnClickListener(this);
        stat.setOnClickListener(this);
        login.setOnClickListener(this);
        exit.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccount:
                Intent intentCreate = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intentCreate);
                break;
            case R.id.login:
                Intent intentLogin = new Intent(this, Login.class);
                startActivity(intentLogin);
                break;
            case R.id.userInfo:
                Intent intentStat = new Intent(MainActivity.this, UserInfo.class);
                startActivity(intentStat);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}

