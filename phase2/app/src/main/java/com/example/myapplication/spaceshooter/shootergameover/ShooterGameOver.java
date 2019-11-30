package com.example.myapplication.spaceshooter.shootergameover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.mainpage.GameMain;
import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterBackGroundMusic;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;
import com.example.myapplication.spaceshooter.ShooterGameStatus;

public class ShooterGameOver extends AppCompatActivity implements View.OnClickListener, ShooterGameOverView{
    Button next;
    TextView message;
    ShooterGameStatus shooterGameStatus;
    ShooterGameOverPresenter shooterGameOverPresenter;
    Button backToMain, backToMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_game_over);
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        assert shooterGameStatus != null;
        ShooterGameOverLogic shooterGameOverLogic = new ShooterGameOverLogic(shooterGameStatus);
        shooterGameOverPresenter = new ShooterGameOverPresenter(shooterGameOverLogic, this);
        next = findViewById(R.id.nextStep);
        next.setOnClickListener(this);
        backToMain = findViewById(R.id.backMain);
        backToMain.setOnClickListener(this);
        backToMenu = findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(this);
        message = findViewById(R.id.gamemessage);

    }
    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        shooterGameOverPresenter.checkNextLevelAppear();
        shooterGameOverPresenter.checkMusicStart();
        shooterGameOverPresenter.setUpGameMessage();
    }

    protected void onPause() {
        super.onPause();
        shooterGameOverPresenter.checkMusicStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backMain:
                shooterGameOverPresenter.checkMusicStop();
                finish();
                break;
            case R.id.nextStep:
                shooterGameOverPresenter.handleNextLevel();
                finish();
                break;
            case R.id.backToMenu:
                shooterGameOverPresenter.handleBackToMain();
                finish();
        }
    }

    @Override
    public void nextLevelAppear() {
        next.setVisibility(View.VISIBLE);
    }

    @Override
    public void nextLevelGone() {
        next.setVisibility(View.GONE);
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
    public void setGameText(String message) {
        this.message.setText(message);
    }

    @Override
    public void startNewLevel() {
        Intent newLevelIntent = new Intent(this, ShooterGame.class);
        newLevelIntent.putExtra("gameStatus", shooterGameStatus);
        startActivity(newLevelIntent);
    }

    @Override
    public void backToMenu() {
        Intent backToMenuIntent = new Intent(getApplicationContext(), GameMain.class);
        backToMenuIntent.putExtra("user", shooterGameStatus.getName());
        backToMenuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(backToMenuIntent);
    }

}
