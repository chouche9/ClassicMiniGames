package com.example.myapplication.SpaceShooter.shooterplanegame;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.BonusLevel.BonusLevelDialog;
import com.example.myapplication.SpaceShooter.ShooterBackGroundMusic;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;
import com.example.myapplication.SpaceShooter.shootergameview.ShooterGameView;

public class ShooterGame extends AppCompatActivity implements BonusLevelDialog.BonusLevelDialogListener {
    ShooterGameView gameView;
    ShooterGameStatus shooterGameStatus;
    ShooterPlaneGameLogic shooterPlaneGameLogic;
    boolean musicstop;
    private BonusLevelDialog dialog;
    boolean bonusopen;
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
        bonusopen = false;
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
        if (bonusopen){
            dialog.dismiss();
        }
        super.onPause();
        if(shooterPlaneGameLogic.shouldMusicStop()){
            stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
            musicstop = true;
        }
        shooterPlaneGameLogic.saveGameState();

    }
    public void activateBonusGame(){
        bonusopen = true;
        gameView.activityFinish = true;
        openDialog();
    }
    private void openDialog(){
        dialog = new BonusLevelDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "Bonus Level Dialog");
    }

    @Override
    public void bonusLevelResult(boolean isWon, int bonusSore) {
        if (isWon) {
            shooterPlaneGameLogic.addBonusPoint(bonusSore);
            Toast.makeText(this, "You guessed the correct number!\nPlus 100 points!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, "Try Again Next Time!", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
        onCancel();
    }

    @Override
    public void onCancel() {
        bonusopen = false;
        gameView.activityFinish = false;
        gameView.startTimer();
        gameView.invalidate();
    }
}
