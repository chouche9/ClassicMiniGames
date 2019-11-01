package com.example.myapplication.FlappyFish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class FlappyGameMenu extends AppCompatActivity implements View.OnClickListener {

    private Button newGameBtn;
    private Button resumeGameBtn;
    private Button quitGameBtn;
    private FlappyGameStatus gameStatus;
    private final int REQUEST_CODE1 = 1;
    private final int REQUEST_CODE2 = 2;
    private Intent menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_game_menu);

        menuIntent = getIntent();
        setNewGameBtn();
        setResumeGameBtn();
        setQuitBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
        String name = menuIntent.getStringExtra("user");
        gameStatus = gameManager.getGameStatus(name);
        if (!gameStatus.getPlayed()) {
            newGameBtn.setVisibility(View.VISIBLE);
            resumeGameBtn.setVisibility(View.GONE);
        } else {
            newGameBtn.setVisibility(View.VISIBLE);
            resumeGameBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE1 || requestCode == REQUEST_CODE2) {
            if (data != null) {
                boolean closed = data.getBooleanExtra("closed", false);
                if (closed) {
                    finish();
                }
            }
        }
    }

    private void setNewGameBtn() {
        newGameBtn = findViewById(R.id.newGameBtn);
        newGameBtn.setOnClickListener(this);
    }

    private void setResumeGameBtn() {
        resumeGameBtn = findViewById(R.id.resumeGameBtn);
        resumeGameBtn.setOnClickListener(this);
    }

    private void setQuitBtn() {
        quitGameBtn = findViewById(R.id.quitGameBtn);
        quitGameBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newGameBtn:
                Intent startSettingIntent = new Intent(this, FlappySetting.class);
                gameStatus.startUpdate();
                startSettingIntent.putExtra("gamer", gameStatus);
                startActivityForResult(startSettingIntent, REQUEST_CODE2);
                break;

            case R.id.resumeGameBtn:
                Intent startGameIntent = new Intent(this, FlappyMainActivity.class);
                startGameIntent.putExtra("gamer", gameStatus);
                startActivityForResult(startGameIntent, REQUEST_CODE1);
                break;

            case R.id.quitGameBtn:
                finish();
        }
    }
}
