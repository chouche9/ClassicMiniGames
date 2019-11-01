package com.example.myapplication.FlappyFish;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class FlappySetting extends AppCompatActivity implements View.OnClickListener{

    private Button easyBtn;
    private Button hardBtn;
    private Intent settingIntent;
    private FlappyGameStatus gameStatus;
    private final static int REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_setting);

        settingIntent = getIntent();
        gameStatus = settingIntent.getParcelableExtra("gamer");
        setUpEasyBtn();
        setUpHardBtn();
    }

    private void setUpEasyBtn() {
        easyBtn = findViewById(R.id.easyBtn);
        easyBtn.setOnClickListener(this);
    }

    private void setUpHardBtn() {
        hardBtn = findViewById(R.id.hardBtn);
        hardBtn.setOnClickListener(this);
    }

    private void startGame() {
        Intent startGame = new Intent(getApplicationContext(), FlappyMainActivity.class);
        startGame.putExtra("gamer", gameStatus);
        startActivityForResult(startGame, REQUEST_CODE);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode  == REQUEST_CODE){
            setResult(RESULT_OK, data);
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.easyBtn:
                gameStatus.setGameEasy();
                startGame();
                break;

            case R.id.hardBtn:
                gameStatus.setGameHard();
                startGame();
                break;
        }
    }
}
