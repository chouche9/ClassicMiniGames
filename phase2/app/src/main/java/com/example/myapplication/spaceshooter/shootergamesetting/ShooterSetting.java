package com.example.myapplication.spaceshooter.shootergamesetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.spaceshooter.ShooterBackgroundMusic;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shooterplanegame.ShooterGame;

/** The type Shooter setting. */
public class ShooterSetting extends AppCompatActivity implements View.OnClickListener {
  /** The Plane 1. */
  ImageButton plane1,
      /** The Plane 2. */
      plane2,
      /** The Plane 3. */
      plane3;
  /** The Shooter game status. */
  ShooterGameStatusFacade shooterGameStatus;
  /** The View finish boolean. */
  boolean viewFinish;

  /**
   * Initializes this ShooterSetting activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shooter_setting);
    GameStatus gameStatus = getIntent().getParcelableExtra("gameStatus");
    shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
    plane1 = findViewById(R.id.plane1);
    plane2 = findViewById(R.id.plane2);
    plane3 = findViewById(R.id.plane3);
    plane1.setOnClickListener(this);
    plane2.setOnClickListener(this);
    plane3.setOnClickListener(this);
    viewFinish = true;
  }

  /** start playing music */
  @Override
  protected void onResume() {
    super.onResume();
    startService(new Intent(getApplicationContext(), ShooterBackgroundMusic.class));
  }

  /** decide if should stop music */
  @Override
  protected void onPause() {
    super.onPause();
    if (viewFinish) {
      stopService(new Intent(getApplicationContext(), ShooterBackgroundMusic.class));
    }
  }

  /**
   * on click event when button get clicked
   *
   * @param v the button view
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.plane1:
        shooterGameStatus.setPlane(1, getApplicationContext());
        startNext();
        break;
      case R.id.plane2:
        shooterGameStatus.setPlane(2, getApplicationContext());
        startNext();
        break;
      case R.id.plane3:
        shooterGameStatus.setPlane(3, getApplicationContext());
        startNext();
        break;
    }
  }

  /** Start the actual space shooting game. */
  void startNext() {
    Intent intent = new Intent(this, ShooterGame.class);
    intent.putExtra("gameStatus", shooterGameStatus);
    viewFinish = false;
    startActivity(intent);
    this.finish();
  }
}
