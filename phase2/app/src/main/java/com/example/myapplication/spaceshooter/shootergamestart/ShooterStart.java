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

public class ShooterStart extends AppCompatActivity implements View.OnClickListener, ShooterStartView{
    Button start, exit, resume;
    boolean finish = true;
    ShooterStartPresenter shooterStartPresenter;
    String user;
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        ShooterStartLogic shooterStartLogic = new ShooterStartLogic(this, user);
        shooterStartPresenter = new ShooterStartPresenter(shooterStartLogic, this);
        shooterStartPresenter.startMusic();
        shooterStartPresenter.checkResumeAppear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                shooterStartPresenter.startNewGame();
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.resume:
                shooterStartPresenter.resumeGame();
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        shooterStartPresenter.pauseMusic();
    }
    public void startMusic(){
        startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }
    public void stopMusic(){
        stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }


    @Override
    public void resumeAppear() {
        resume.setVisibility(View.VISIBLE);
    }


    @Override
    public void resumeGone() {
        resume.setVisibility(View.GONE);
    }

    @Override
    public void startFinishPage() {
        Intent startFinishPage = new Intent(this, ShooterGameOver.class);
        startFinishPage.putExtra("gameStatus", shooterStartPresenter.getGameStatus());
        startActivity(startFinishPage);
    }

    @Override
    public void startGamePage() {
        Intent startGameIntent = new Intent(this, ShooterGame.class);
        startGameIntent.putExtra("gameStatus", shooterStartPresenter.getGameStatus());
        startActivity(startGameIntent);
    }

    @Override
    public void startSettingPage() {
        Intent startSettingIntent = new Intent(this, ShooterSetting.class);
        startSettingIntent.putExtra("gameStatus", shooterStartPresenter.getGameStatus());
        startActivity(startSettingIntent);
    }

}
