package com.example.myapplication.SpaceShooter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class ShooterStart extends AppCompatActivity implements View.OnClickListener{
    Button start, exit;
    boolean finish = true;
    ShooterGameStatus shooterGameStatus;
    boolean musicstop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_start);
        shooterGameStatus = new ShooterGameStatus("user");
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        finish = true;
        super.onResume();
        startService(new Intent(getApplicationContext(), BackGroundMusic.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                Intent intent = new Intent(this, ShooterSetting.class);
                intent.putExtra("gameStatus", shooterGameStatus);
                finish = false;
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(finish){
            stopService(new Intent(getApplicationContext(), BackGroundMusic.class));
        }
    }
}
