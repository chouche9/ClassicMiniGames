package com.example.myapplication.SpaceShooter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.example.myapplication.R;
import com.example.myapplication.SpaceShooter.shootergameview.ShooterGameView;

public class ShooterGame extends Activity {
    ShooterGameView gameView;
    ShooterGameStatus shooterGameStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        gameView = new ShooterGameView(this);
        gameView.setShooterGameStatus(shooterGameStatus);
        setContentView(gameView);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(gameView.viewFinish){
            stopService(new Intent(getApplicationContext(), BackGroundMusic.class));
        }
    }
}
