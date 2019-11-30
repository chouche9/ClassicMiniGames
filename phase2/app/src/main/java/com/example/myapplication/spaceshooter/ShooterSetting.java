package com.example.myapplication.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;

public class ShooterSetting extends AppCompatActivity implements View.OnClickListener{
    ImageButton plane1, plane2, plane3;
    ShooterGameStatusFacade shooterGameStatus;
    boolean viewFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_setting);
        GameStatus gameStatus = getIntent().getParcelableExtra("gameStatus");
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        plane1 = findViewById(R.id.plane1);
        plane2 = findViewById(R.id.plane2);
        plane3 = findViewById(R.id.plane3);
        plane1.setOnClickListener(this);
        plane2.setOnClickListener(this);
        plane3.setOnClickListener(this);
        viewFinish = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (viewFinish){
        stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));}
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plane1:
                shooterGameStatus.setPlane(1, getApplicationContext());
                startNext();
                break;
            case R.id.plane2:
                shooterGameStatus.setPlane(2, getApplicationContext());
                startNext();
                break;
            case R.id.plane3:
                shooterGameStatus.setPlane(3, getApplicationContext());
                startNext();
                break;
        }
    }
    void startNext(){
        Intent intent = new Intent(this, ShooterGame.class);
        intent.putExtra("gameStatus", shooterGameStatus);
        viewFinish = false;
        startActivity(intent);
        this.finish();
    }




}
