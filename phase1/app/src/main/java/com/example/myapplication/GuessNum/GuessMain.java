package com.example.myapplication.GuessNum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;


public class GuessMain extends AppCompatActivity implements View.OnClickListener {
    private Button startButton;
    private Button resumeButton;
    private Button quitButton;
    private final int REQUEST_CODE1 = 1;
    private final int REQUEST_CODE2 = 2;
    private GuessGameStat p1;
    private Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_main);
        startButton = findViewById(R.id.startBotton);
        startButton.setOnClickListener(this);
        resumeButton = findViewById(R.id.resumeBtn);
        intent1 = getIntent();
        resumeButton.setOnClickListener(this);
        quitButton = findViewById(R.id.quitBtn);
        quitButton.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        GuessGameManager gameManager = GuessGameManager.getInstance(this);
        String name = intent1.getStringExtra("user");
        p1 = gameManager.getGameStatus(name);
        if (!p1.played()){
            resumeButton.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
        }
        else{
            resumeButton.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE1 || requestCode == REQUEST_CODE2){
            if (data!= null){
                boolean closed = data.getBooleanExtra("closed", false);
                if(closed){
                    finish();
                }
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBotton:
                Intent intent2 = new Intent(this, GuessSetting.class);
                p1.startUpdate();
                intent2.putExtra("gamer", p1);
                startActivityForResult(intent2, REQUEST_CODE2);
                break;

            case R.id.resumeBtn:
                Intent intent1 = new Intent(this, GuessGame.class);
                intent1.putExtra("gamer", p1);
                startActivityForResult(intent1, REQUEST_CODE1);
                break;
            case R.id.quitBtn:
                finish();
                break;
        }
    }
}

