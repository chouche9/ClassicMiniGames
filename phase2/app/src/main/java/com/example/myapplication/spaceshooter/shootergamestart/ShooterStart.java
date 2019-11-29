package com.example.myapplication.spaceshooter.shootergamestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterBackGroundMusic;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;
import com.example.myapplication.spaceshooter.ShooterSetting;
import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOver;

public class ShooterStart extends AppCompatActivity implements View.OnClickListener{
    Button start, exit, resume;
    boolean finish = true;
    boolean musicstop;
    ShooterStartLogic shooterStartLogic;
    String user;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_start);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
        resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
        user = getIntent().getStringExtra("user");
        i = 0;
    }
    @Override
    protected void onResume() {
        i++;
        finish = true;
        super.onResume();
        shooterStartLogic = new ShooterStartLogic(this, user);
        startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
        if(shooterStartLogic.ResumeBtnAppear()){
            resume.setVisibility(View.VISIBLE);
        }
        else {
            resume.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                Intent intent = new Intent(this, ShooterSetting.class);
                shooterStartLogic.eraseGameStat();
                intent.putExtra("gameStatus", shooterStartLogic.getShooterGameStatus());
                finish = false;
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.resume:
                if (shooterStartLogic.checkAtLevel1Finish()){
                    Intent intent1 = new Intent(this, ShooterGameOver.class);
                    intent1.putExtra("gameStatus", shooterStartLogic.getShooterGameStatus());
                    finish = false;
                    startActivity(intent1);
                }
                else {
                    Intent intent1 = new Intent(this, ShooterGame.class);
                    intent1.putExtra("gameStatus", shooterStartLogic.getShooterGameStatus());
                    finish = false;
                    startActivity(intent1);
                }
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(finish){
            stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
        }
    }
}
