package com.example.myapplication.GuessNum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * the activity class that is the result page of guessing number game.
 */
public class GuessCongradulation extends AppCompatActivity implements View.OnClickListener {
    /**
     * TextView that display congrat String
     */
    private TextView congratInfo;
    /**
     * button that go back to the GameMain activity
     */
    private Button backToMain;
    /**
     * button that go back to the GuessMain activity
     */
    private Button backToFront;
    /**
     * intent that GuessCongradulation activity get
     */
    private Intent intent1;
    /**
     * the GuessGameStat of the user that's playing right now.
     */
    private GuessGameStat gamer;
    /**
     * create GuessCongradulation activity
     * @param savedInstanceState bundle of the resource in this activity
     */
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

    /**
     * change to textcolor passed by GameStat and set text for congratInfo
     */
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

    /**
     * get the color number corresponding to the String color
     * @param color: the color passed in
     * @return the integer value of color
     */
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

    /**
     * override onPause method in order to save GuessGameStat.
     */
    protected void onPause() {
        super.onPause();
        GuessGameManager gameManager = GuessGameManager.getInstance(this);
        gameManager.saveGame(gamer);
    }

    /**
     *On Click method for different button get clicked and goes to their particular activity;
     * @param view the button's view
     */
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
