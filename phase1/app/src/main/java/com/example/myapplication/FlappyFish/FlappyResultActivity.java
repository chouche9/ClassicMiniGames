package com.example.myapplication.FlappyFish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.GameMain;
import com.example.myapplication.R;


public class FlappyResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultText;
    private Button playAgainBtn;
    private Button backToMainBtn;
    private FlappyGameStatus gameStatus;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_result);

        setResultText();
        setPlayAgainBtn();
        setBackToMainBtn();
        setFinish();
    }

    private void setResultText() {
        resultText = findViewById(R.id.result);
        resultText.setOnClickListener(this);
    }

    private void setPlayAgainBtn() {
        playAgainBtn = findViewById(R.id.playAgainBtn);
        playAgainBtn.setOnClickListener(this);
    }

    private void setBackToMainBtn() {
        backToMainBtn = findViewById(R.id.backToMainBtn);
        backToMainBtn.setOnClickListener(this);
    }

    private void setFinish() {
        intent1 = getIntent();
        gameStatus = intent1.getParcelableExtra(FlappyGameView.EXTRA_MESSAGE);
        int finalScore = gameStatus.getScore();
        String result = "Your Score : " + finalScore;
        resultText.setText(result);
        gameStatus.finishUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
        gameManager.saveGame(gameStatus);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playAgainBtn:
                Intent playAgainIntent = new Intent(this, FlappyGameMenu.class);
                playAgainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(playAgainIntent);
                finish();
                break;
            case R.id.backToMainBtn:
                Intent backToMainIntent = new Intent(this, GameMain.class);
                backToMainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backToMainIntent);
                finish();
                break;
        }
    }
}
