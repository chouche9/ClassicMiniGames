package com.example.myapplication.spaceshooter.shootergameover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.mainpage.GameMain;
import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterBackgroundMusic;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;

/** The type Shooter game over. */
public class ShooterGameOver extends AppCompatActivity
    implements View.OnClickListener, ShooterGameOverView {
  /** The Next button. */
  Button next;
  /** The Message displayed on game over activity. */
  TextView message;
  /** The Shooter game status. */
  ShooterGameStatusFacade shooterGameStatus;
  /** The Shooter game over presenter that interact shooterGameOverLogic with ShooterGameOver. */
  ShooterGameOverPresenter shooterGameOverPresenter;
  /** The Back to main Page Button */
  Button backToMain,
      /** The Back to 3 Game menu button */
      backToMenu;

  /**
   * initialize the ShooterGameOver activity
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shooter_game_over);
    shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
    assert shooterGameStatus != null;
    ShooterGameOverLogic shooterGameOverLogic = new ShooterGameOverLogic(shooterGameStatus);
    shooterGameOverPresenter = new ShooterGameOverPresenter(shooterGameOverLogic, this);
    next = findViewById(R.id.nextStep);
    next.setOnClickListener(this);
    backToMain = findViewById(R.id.backMain);
    backToMain.setOnClickListener(this);
    backToMenu = findViewById(R.id.backToMenu);
    backToMenu.setOnClickListener(this);
    message = findViewById(R.id.gamemessage);
  }

  /** onResume call when activity is resumed and update level button music and message displayed */
  @Override
  protected void onResume() {
    super.onResume();
    super.onResume();
    shooterGameOverPresenter.checkNextLevelAppear();
    shooterGameOverPresenter.checkMusicStart();
    shooterGameOverPresenter.setUpGameMessage();
  }

  /** pause the music and decide whether the music should stop */
  protected void onPause() {
    super.onPause();
    shooterGameOverPresenter.checkMusicStop();
  }

  /**
   * click logic when different button get clicked
   *
   * @param v the button view get clicked
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.backMain:
        shooterGameOverPresenter.checkMusicStop();
        finish();
        break;
      case R.id.nextStep:
        shooterGameOverPresenter.handleNextLevel();
        finish();
        break;
      case R.id.backToMenu:
        shooterGameOverPresenter.handleBackToMain();
        finish();
    }
  }

  /** set the next level button appear */
  @Override
  public void nextLevelAppear() {
    next.setVisibility(View.VISIBLE);
  }

  /** set the next level button disappear */
  @Override
  public void nextLevelGone() {
    next.setVisibility(View.GONE);
  }

  /** start background music */
  @Override
  public void startMusic() {
    startService(new Intent(getApplicationContext(), ShooterBackgroundMusic.class));
  }

  /** stop background music */
  @Override
  public void stopMusic() {
    stopService(new Intent(getApplicationContext(), ShooterBackgroundMusic.class));
  }

  /**
   * set gametext message
   *
   * @param message message that want to display
   */
  @Override
  public void setGameText(String message) {
    this.message.setText(message);
  }

  /** activate start the next level intent */
  @Override
  public void startNewLevel() {
    Intent newLevelIntent = new Intent(this, ShooterGame.class);
    newLevelIntent.putExtra("gameStatus", shooterGameStatus);
    startActivity(newLevelIntent);
  }

  /** activate go back to game menu intent */
  @Override
  public void backToMenu() {
    Intent backToMenuIntent = new Intent(getApplicationContext(), GameMain.class);
    backToMenuIntent.putExtra("user", shooterGameStatus.getName());
    backToMenuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(backToMenuIntent);
  }
}
