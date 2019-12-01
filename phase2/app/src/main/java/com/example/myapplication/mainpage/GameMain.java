package com.example.myapplication.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.flappyfish.flappygameactivities.FlappyGameMenu;
import com.example.myapplication.hangman.HangmanMain;
import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.shootergamestart.ShooterStart;

/** Main page of all three games. */
public class GameMain extends AppCompatActivity implements View.OnClickListener {

  /** The username of a user playing the game. */
  private String username;

  /**
   * Create GameMain activity.
   *
   * @param savedInstanceState bundle of the resource in this activity
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game_main);

    Button spaceShooter = findViewById(R.id.spaceShooter);
    Button hangman = findViewById(R.id.hangman);
    Button flappyFish = findViewById(R.id.flappyFish);
    Button logout = findViewById(R.id.logout);

    Intent intent = getIntent();
    username = intent.getStringExtra("user");

    spaceShooter.setOnClickListener(this);
    hangman.setOnClickListener(this);
    flappyFish.setOnClickListener(this);
    logout.setOnClickListener(this);
  }

  /**
   * Method that gets called when player clicks a button, controlling which game they go to.
   *
   * @param view the button that gets clicked
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.spaceShooter:
        Intent shooterIntent = new Intent(this, ShooterStart.class);
        shooterIntent.putExtra("user", username);
        startActivity(shooterIntent);
        break;
      case R.id.hangman:
        Intent hangmanIntent = new Intent(this, HangmanMain.class);
        hangmanIntent.putExtra("user", username);
        startActivity(hangmanIntent);
        break;
      case R.id.flappyFish:
        Intent flappyIntent = new Intent(this, FlappyGameMenu.class);
        flappyIntent.putExtra("user", username);
        startActivity(flappyIntent);
        break;
      case R.id.logout:
        setResult(RESULT_OK);
        finish();
        break;
    }
  }
}
