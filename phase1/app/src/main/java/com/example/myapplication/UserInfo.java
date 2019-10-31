package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class UserInfo extends AppCompatActivity implements View.OnClickListener {
    Intent intent1;
    TextView stat;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        intent1 = getIntent();
        String info = UserManager.getInstance(this).toString();
        stat = findViewById(R.id.textView);
        stat.setText(info);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}

