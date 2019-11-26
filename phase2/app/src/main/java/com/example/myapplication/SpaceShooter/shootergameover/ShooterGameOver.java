package com.example.myapplication.SpaceShooter.shootergameover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.SpaceShooter.BackGroundMusic;
import com.example.myapplication.SpaceShooter.ShooterGame;
import com.example.myapplication.SpaceShooter.ShooterGameStatus;

public class ShooterGameOver extends AppCompatActivity implements View.OnClickListener{
    Button next;
    TextView message;
    ShooterGameStatus shooterGameStatus;
    Button backToMain;
    boolean viewFinish;
    ShooterGameOverLogic gameOverPresenter;
    boolean musicfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_game_over);
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        assert shooterGameStatus != null;
        gameOverPresenter = new ShooterGameOverLogic(shooterGameStatus);
        next = findViewById(R.id.nextStep);
        next.setText("next level");
        next.setOnClickListener(this);
        backToMain = findViewById(R.id.backMain);
        backToMain.setOnClickListener(this);
        musicfinish = false;
        message = findViewById(R.id.gamemassage);
    }
    @Override
    protected void onResume() {
        viewFinish = true;
        super.onResume();
        if (gameOverPresenter.checkNextLevelAppear()){
            next.setVisibility(View.VISIBLE);
        }
        else {
            next.setVisibility(View.GONE);
        }
        if (musicfinish){
            startService(new Intent(getApplicationContext(), BackGroundMusic.class));
            musicfinish = false;
        }
        message.setText(gameOverPresenter.setText());
    }

    protected void onPause() {
        super.onPause();
        if(viewFinish){
            stopService(new Intent(getApplicationContext(), BackGroundMusic.class));
            musicfinish = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backMain:
                gameOverPresenter.eraseGameState();
                finish();
                break;
            case R.id.nextStep:
                gameOverPresenter.levelUpGamestate();
                Intent intent = new Intent(this, ShooterGame.class);
                intent.putExtra("gameStatus", shooterGameStatus);
                startActivity(intent);
                viewFinish = false;
                finish();
                break;
        }
    }
}
