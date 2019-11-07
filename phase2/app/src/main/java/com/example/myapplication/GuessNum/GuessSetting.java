package com.example.myapplication.GuessNum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

/** the activity class that setting the font color of Guessing Game. */
public class GuessSetting extends AppCompatActivity implements View.OnClickListener {

  /** the play's GuessGameStat */
  private GuessGameStat p1;

  /** request_code pass in to start activity */
  final int REQUEST_CODE = 5;

  /**
   * create GuessSetting activity
   *
   * @param savedInstanceState bundle of the resource in this activity
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guess_setting);
    Button yellowButton = findViewById(R.id.yellowButton);
    Button greenButton = findViewById(R.id.greenButton);
    Button redButton = findViewById(R.id.redButton);
    yellowButton.setOnClickListener(this);
    greenButton.setOnClickListener(this);
    redButton.setOnClickListener(this);
    Intent intent1 = getIntent();
    p1 = intent1.getParcelableExtra("gamer");
  }
  /**
   * On Click method for different button get clicked and set each's particular number;
   *
   * @param view the button's view
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.yellowButton:
        p1.setColor("yellow");
        Toast.makeText(GuessSetting.this, "yellow", Toast.LENGTH_SHORT).show();
        startGame();
        break;
      case R.id.redButton:
        p1.setColor("red");
        Toast.makeText(GuessSetting.this, "red", Toast.LENGTH_SHORT).show();
        startGame();
        break;
      case R.id.greenButton:
        p1.setColor("dark green");
        Toast.makeText(GuessSetting.this, "dark green", Toast.LENGTH_SHORT).show();
        startGame();
        break;
    }
  }

  /** start the GuessGame activity and pass the play's GuessGameStat into the new activity */
  private void startGame() {
    Intent intent1 = new Intent(GuessSetting.this, GuessGame.class);
    intent1.putExtra("gamer", p1);
    startActivityForResult(intent1, REQUEST_CODE);
  }

  /**
   * Dispatch incoming result to the correct fragment. close this activity as well when intent get
   * passed back
   *
   * @param requestCode the request code of the started activity
   * @param resultCode whether the result get returned is okay or not
   * @param data intent that store the closed info
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE) {
      setResult(RESULT_OK, data);
      finish();
    }
  }
}
