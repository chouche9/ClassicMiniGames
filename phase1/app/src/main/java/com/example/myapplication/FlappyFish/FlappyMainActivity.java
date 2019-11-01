package com.example.myapplication.FlappyFish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class FlappyMainActivity extends AppCompatActivity {

    private FlappyGameView gameView;
    private FlappyGameStatus gameStatus;
    private Timer timer = null;
    private Handler handler = new Handler();
    private static final long TIMER_INTERVAL = 30;
    private static final int REQUEST_CODE3 = 3;
    private static final String TAG1 = "RUNNING";
    private static final String TAG2 = "STOP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new FlappyGameView(this);
        setContentView(gameView);
        gameStatus = getIntent().getParcelableExtra("gamer");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.setGameStatus(gameStatus);

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            gameView.invalidate();
                            Log.e(TAG1, "Running!!");
                        }
                    });
                }
            }, 0, TIMER_INTERVAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer = null;
        FlappyGameManager gameManager = FlappyGameManager.getInstance(this);
        gameManager.saveGame(gameStatus);
        Log.e(TAG2, "It stops!");
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE3){
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
