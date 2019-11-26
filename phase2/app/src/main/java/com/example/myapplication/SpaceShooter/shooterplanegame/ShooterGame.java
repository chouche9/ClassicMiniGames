package com.example.myapplication.SpaceShooter.shooterplanegame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.SpaceShooter.ShooterBackGroundMusic;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;
import com.example.myapplication.SpaceShooter.shootergameview.ShooterGameView;

public class ShooterGame extends Activity {
    ShooterGameView gameView;
    ShooterGameStatus shooterGameStatus;
    ShooterPlaneGameLogic shooterPlaneGameLogic;
    boolean musicstop;
    int start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        shooterPlaneGameLogic = new ShooterPlaneGameLogic(shooterGameStatus, this);
        gameView = new ShooterGameView(this);
        gameView.setShooterGameStatus(shooterGameStatus);
        setContentView(gameView);
        start = 1;
        musicstop = false;
    }

    @Override
    protected void onResume() {
        gameView.activityFinish = false;
        gameView.startTimer();
        if(start<1){
        gameView.invalidate();}
        if(musicstop){
            startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
        }
        start--;
        super.onResume();
    }

    @Override
    protected void onPause() {
        gameView.activityFinish = true;
        super.onPause();
        if(shooterPlaneGameLogic.shouldMusicStop()){
            stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
            musicstop = true;
        }
        shooterPlaneGameLogic.saveGameState();

    }
}
