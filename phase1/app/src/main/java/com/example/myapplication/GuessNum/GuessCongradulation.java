package com.example.myapplication.GuessNum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class GuessCongradulation extends AppCompatActivity implements View.OnClickListener {
    private TextView congratInfo;
    private Button backToMain;
    private Button backToFront;
    private Intent intent1;
    private GuessGameStat gamer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_congradulation);
        backToMain = findViewById(R.id.backButton);
        backToFront = findViewById(R.id.backFrontBtn);
        congratInfo = findViewById(R.id.congratInfo);
        backToFront.setOnClickListener(this);
        backToMain.setOnClickListener(this);
        setFinish();
    }
    private void setFinish() {
        intent1 = getIntent();
        gamer = intent1.getParcelableExtra("gamer");
        int color = getTextColor(gamer.getColor());
        int numtries = gamer.getCurrentTries();
        congratInfo.setTextColor(color);
        backToMain.setBackgroundColor(color);
        backToFront.setBackgroundColor(color);
        gamer.finishUpdate(numtries);
        String congrat;
        congrat = "You Finish game in:\n" + numtries + " tries\n" + "Your best tries is: \n" + gamer.getBestTries();
        congratInfo.setText(congrat);
    }


    private int getTextColor(String color) {
        int colorNum;
        if (color.equals("yellow")) {
            colorNum = getResources().getColor(R.color.yellow);
        } else if (color.equals("red")) {
            colorNum = getResources().getColor(R.color.red);
        } else {
            colorNum = getResources().getColor(R.color.green);
        }
        return colorNum;
    }

    protected void onPause() {
        super.onPause();
        GuessGameManager gameManager = GuessGameManager.getInstance(this);
        gameManager.saveGame(gamer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backFrontBtn:
                intent1.putExtra("closed", false);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.backButton:
                intent1.putExtra("closed", true);
                setResult(RESULT_OK, intent1);
                finish();
                break;
        }
    }
}
