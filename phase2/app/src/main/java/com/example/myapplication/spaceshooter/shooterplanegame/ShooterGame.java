package com.example.myapplication.spaceshooter.shooterplanegame;

import android.os.Bundle;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bonuslevel.BonusLevelDialog;
import com.example.myapplication.spaceshooter.ShooterBackGroundMusic;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

public class ShooterGame extends AppCompatActivity implements BonusLevelDialog.BonusLevelDialogListener, ShooterGameInterface {
    ShooterGameView gameView;
    ShooterGameStatusFacade shooterGameStatus;
    ShooterGamePresenter shooterGamePresenter;
    private BonusLevelDialog dialog;
    boolean bonusopen;
    int start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        gameView = new ShooterGameView(this);
        gameView.setShooterGameStatus(shooterGameStatus);
        ShooterPlaneGameLogic shooterPlaneGameLogic = new ShooterPlaneGameLogic(
                shooterGameStatus, this, gameView);
        shooterGamePresenter = new ShooterGamePresenter(this, shooterPlaneGameLogic);
        setContentView(gameView);
        start = 1;
        bonusopen = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        shooterGamePresenter.HandleOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shooterGamePresenter.HandleOnPause();

    }
    public void activateBonusGame(){
        shooterGamePresenter.activateBonusGame();
    }


    public void openDialog(){
        dialog = new BonusLevelDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "Bonus Level Dialog");
    }

    @Override
    public void makeBonusWinToast() {
        Toast.makeText(this,
                "You guessed the correct number!\nPlus 100 points!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeBonusLoseToast() {
        Toast.makeText(this, "Try Again Next Time!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bonusLevelResult(boolean isWon, int bonusSore) {
        shooterGamePresenter.handleBonusGameResult(isWon, bonusSore);
        onCancel();
    }

    @Override
    public void onCancel() {
        shooterGamePresenter.cancelBonus();
    }

    @Override
    public void startMusic() {
        startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }

    @Override
    public void stopMusic() {
        stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

}
