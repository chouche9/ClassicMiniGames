package com.example.myapplication.FlappyFish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;


public class FlappyResultActivity extends AppCompatActivity {

    TextView resultText;
    Button playAgainBtn;
    Button backToMain;
    private FlappyGameStatus gameStatus;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_result);

        resultText = findViewById(R.id.result);
        playAgainBtn = findViewById(R.id.playAgainBtn);
        setFinish();
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.putExtra("closed", false);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }

    private void setFinish() {
        intent1 = getIntent();
        gameStatus = intent1.getParcelableExtra(FlappyGameView.EXTRA_MESSAGE);
        gameStatus.finishUpdate();
        int finalScore = gameStatus.getScore();
        String result = "Your Score : " + finalScore;
        resultText.setText(result);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
        gameManager.saveGame(gameStatus);
    }



}
