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

public class ShooterGameOver extends AppCompatActivity implements View.OnClickListener{
    Button next;
    TextView message;
    ShooterGameStatus shooterGameStatus;
    Button backToMain, backToMenu;
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
        backToMenu = findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(this);
        musicfinish = false;
        message = findViewById(R.id.gamemessage);
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
            startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
            musicfinish = false;
        }
        message.setText(gameOverPresenter.setText());
    }

    protected void onPause() {
        super.onPause();
        if(viewFinish){
            stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
            musicfinish = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backMain:
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
            case R.id.backToMenu:
                Intent intent1 = new Intent(getApplicationContext(), GameMain.class);
                intent1.putExtra("user", shooterGameStatus.getName());
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
        }
    }
}
