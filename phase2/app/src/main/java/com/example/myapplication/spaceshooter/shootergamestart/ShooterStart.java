package com.example.myapplication.spaceshooter.shootergamestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterBackGroundMusic;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;
import com.example.myapplication.spaceshooter.shootergamesetting.ShooterSetting;
import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOver;

/**
 * The type Shooter start.
 */
public class ShooterStart extends AppCompatActivity implements View.OnClickListener, ShooterStartView {
    /**
     * The Start button.
     */
    Button start, /**
     * The Exit button.
     */
    exit, /**
     * The Resume button that start the last time played game.
     */
    resume;
    /**
     * The Shooter start presenter .
     */
    ShooterStartPresenter shooterStartPresenter;
    /**
     * The User that is playing the game.
     */
    String user;

    /**
     * create ShooterStart activity
     *
     * @param savedInstanceState bundle of the resource in this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooter_start);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
        resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
        user = getIntent().getStringExtra("user");
    }

    /**
     * resume the gameStart activity
     * make music start and check if resume button should appear.
     */
    @Override
    protected void onResume() {
        super.onResume();
        ShooterStartLogic shooterStartLogic = new ShooterStartLogic(this, user);
        shooterStartPresenter = new ShooterStartPresenter(shooterStartLogic, this);
        shooterStartPresenter.startMusic();
        shooterStartPresenter.checkResumeAppear();
    }

    /**
     * onclick method called when view button get clicked
     *
     * @param view the view that get clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                shooterStartPresenter.startNewGame();
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.resume:
                shooterStartPresenter.resumeGame();
                break;
        }

    }

    /**
     * pause game and determine if the music should stop in presenter
     */
    @Override
    protected void onPause() {
        super.onPause();
        shooterStartPresenter.pauseMusic();
    }

    /**
     * start the music
     */
    public void startMusic() {
        startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }

    /**
     * pause the music
     */
    public void stopMusic() {
        stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }

    /**
     * make resumeButton appear
     */
    @Override
    public void resumeAppear() {
        resume.setVisibility(View.VISIBLE);
    }

    /**
     * make resumeButton disappear
     */
    @Override
    public void resumeGone() {
        resume.setVisibility(View.GONE);
    }

    /**
     * start gameOver intent
     */
    @Override
    public void startFinishPage() {
        Intent startFinishPage = new Intent(this, ShooterGameOver.class);
        startFinishPage.putExtra("gameStatus", shooterStartPresenter.getGameStatus());
        startActivity(startFinishPage);
    }

    /**
     * start shooter game view intent
     */

    @Override
    public void startGamePage() {
        Intent startGameIntent = new Intent(this, ShooterGame.class);
        startGameIntent.putExtra("gameStatus", shooterStartPresenter.getGameStatus());
        startActivity(startGameIntent);
    }

    /**
     * start shooter setting activity
     */
    @Override
    public void startSettingPage() {
        Intent startSettingIntent = new Intent(this, ShooterSetting.class);
        startSettingIntent.putExtra("gameStatus", shooterStartPresenter.getGameStatus());
        startActivity(startSettingIntent);
    }

}
