package com.example.myapplication.GuessNum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;


public class GuessMain extends AppCompatActivity implements View.OnClickListener {
    /**
     * start button
     */
    private Button startButton;
    /**
     * resumeButton
     */
    private Button resumeButton;
    /**
     * the request_code to start activity GuessSetting
     */
    private final int REQUEST_CODE1 = 1;
    /**
     * the activity code to start activity GuessGame
     */
    private final int REQUEST_CODE2 = 2;
    /**
     * the play's GuessGameStat
     */
    private GuessGameStat p1;
    /**
     * the intent that get passed in
     */
    private Intent intent1;
    /**
     * create GuessMain activity
     * @param savedInstanceState bundle of the resource in this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_main);
        startButton = findViewById(R.id.startBotton);
        startButton.setOnClickListener(this);
        resumeButton = findViewById(R.id.resumeBtn);
        intent1 = getIntent();
        resumeButton.setOnClickListener(this);
        Button quitButton = findViewById(R.id.quitBtn);
        quitButton.setOnClickListener(this);
    }
    /**
     * check GuessGameStatus to determine whether Resume Button should appear or not
     */
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

    /**
     * decide whether GuessMain activity should close or not but the data passed in
     * @param requestCode: the request code of the started activity
     * @param resultCode: whether the result get returned is okay or not
     * @param data: intent that store the closed info
     */
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
    /**
     *On Click method for different button get clicked and goes to their particular activity;
     * @param view the button's view
     */
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

