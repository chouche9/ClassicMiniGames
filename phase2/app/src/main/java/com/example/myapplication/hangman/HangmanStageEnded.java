package com.example.myapplication.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.backgroundmusic.BackgroundMusic;
import com.example.myapplication.bonuslevel.BonusLevelDialog;
import com.example.myapplication.mainpage.GameMain;

/** Activity that get activated when a stage ends in hangman. */
public class HangmanStageEnded extends AppCompatActivity
    implements BonusLevelDialog.BonusLevelDialogListener, View.OnClickListener {

  /** A HangmanGameStatInteractor that stores the hangman game status. */
  private HangmanGameStatus hangmanGameStat;

  /** A String to store the original gender */
  private String originalGender;

  /** Button that user clicks to play again. */
  private Button playAgain;

  /** Button that user clicks to go back to the main menu of the Hangman game. */
  private Button mainMenu;

  /** Button that the user clicks to go back to the home page of the app. */
  private Button backToHome;

  /** Button that the user clicks to go to the next stage */
  private Button nextStage;

  /** Button that the user clicks to play the bonus level game */
  private Button bonusLevel;

  /** TextView to show the First Message */
  private TextView txtFirstMessage;

  /** TextView that shows the Value message */
  private TextView txtValueMessage;

  /** Dialog that saves the BonusLevelDialog instance */
  private BonusLevelDialog dialog;

  /** Button that the user clicks to play background music. */
  private Button btnPlayMusic;

  /** Button that the user clicks to stop background music. */
  private Button btnStopMusic;

  /**
   * Initializes this HangmanMain activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hangman_stage_ended);

    playAgain = findViewById(R.id.btnPlayAgain);
    nextStage = findViewById(R.id.btnNextStage);
    mainMenu = findViewById(R.id.btnMainMenu);
    backToHome = findViewById(R.id.btnBackToHome);
    bonusLevel = findViewById(R.id.btnBonusLevel);
    btnPlayMusic = findViewById(R.id.btnPlayMusic);
    btnStopMusic = findViewById(R.id.btnStopMusic);

    txtFirstMessage = findViewById(R.id.txtFirstMessage);
    txtValueMessage = findViewById(R.id.txtValueMessage);

    Intent receivedIntent = getIntent();
    hangmanGameStat = receivedIntent.getParcelableExtra(HangmanMain.getGamestatusMsg());
    originalGender = hangmanGameStat.getGender();

    stageEndedResult();

    setPlayAgainButton();
    setNextStageButton();
    setMainMenuButton();
    setBackToHome();
    setBonusLevelButton();

    btnPlayMusic.setOnClickListener(this);
    btnStopMusic.setOnClickListener(this);
  }

  /** Method when to show the message and any other View when the stage ended */
  private void stageEndedResult() {
    String firstMessage;
    String valueMessage;

    bonusLevel.setVisibility(View.GONE);

    if (hangmanGameStat.getFalseGuess() == 6) { // game lost
      firstMessage =
          "You lost! The correct word was "
              + hangmanGameStat.getSecretWord()
              + ". \n"
              + "You made it to stage "
              + hangmanGameStat.getStageNum()
              + ".";
      valueMessage = "Total score: " + hangmanGameStat.getAccumulatedScore();
      hangmanGameStat.resetGameStatus();
      hangmanGameStat.setGender(originalGender);
      nextStage.setVisibility(View.GONE);
    } else {
      firstMessage = "Congratulations, you guessed the correct word! \n +1 LIFE";
      valueMessage =
          "Score this stage: "
              + hangmanGameStat.getCurrentScore()
              + "\n"
              + "Total score: "
              + hangmanGameStat.getAccumulatedScore();
      playAgain.setVisibility(View.GONE);

      if (hangmanGameStat.getStageNum() % 3 == 0) {
        bonusLevel.setVisibility(View.VISIBLE);
      } else {
        hangmanGameStat.setBonusLevelActivated(false);
      }
    }

    if (hangmanGameStat.isBonusLevelActivated()) {
      onCancel();
    }

    txtFirstMessage.setText(firstMessage);
    txtValueMessage.setText(valueMessage);
  }

  /**
   * Method that implements the action needs to be done when the music button is on or off
   *
   * @param view
   */
  @Override
  public void onClick(View view) {
    if (view == btnPlayMusic) {
      startService(new Intent(this, HangmanBackgroundMusic.class));
      HangmanMain.isPlaying = true;
    } else if (view == btnStopMusic) {
      stopService(new Intent(this, HangmanBackgroundMusic.class));
      HangmanMain.isPlaying = false;
    }
  }

  /** Method to set the Bonus Level Button */
  private void setBonusLevelButton() {
    bonusLevel.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            openDialog();
            hangmanGameStat.setBonusLevelActivated(true);
            onCancel();
          }
        });
  }

  /** Method to open the dialog for bonus stage */
  private void openDialog() {
    dialog = new BonusLevelDialog();
    dialog.show(getSupportFragmentManager(), "Bonus Level Dialog");
  }

  /**
   * Method to show the bonus level result
   *
   * @param isWon: boolean to show whether game isWon or not
   * @param bonusScore The bonus score
   */
  @Override
  public void bonusLevelResult(boolean isWon, int bonusScore) {

    String firstMessage;
    String valueMessage;

    if (isWon) {
      hangmanGameStat.setAccumulatedScore(hangmanGameStat.getAccumulatedScore() + bonusScore);
      firstMessage = "Congratulations, you guessed the correct number!";
      valueMessage = "Your new total score: " + hangmanGameStat.getAccumulatedScore();
    } else {
      firstMessage = "Try Again Next Time!";
      valueMessage = "Total score: " + hangmanGameStat.getAccumulatedScore();
    }
    dialog.dismiss();

    txtFirstMessage.setText(firstMessage);
    txtValueMessage.setText(valueMessage);
    bonusLevel.setVisibility(View.GONE);
  }

  /** Method that cancels the bonus level button */
  @Override
  public void onCancel() {
    bonusLevel.setVisibility(View.GONE);
  }

  /** Event that happens after playAgain button is clicked. */
  private void setPlayAgainButton() {
    playAgain.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HangmanGameActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            startActivity(intent);
            finish();
          }
        });
  }

  /** Button that sets the next stage button when clicked */
  private void setNextStageButton() {
    nextStage.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HangmanGameActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int falseGuess = hangmanGameStat.getFalseGuess();
            int stageNum = hangmanGameStat.getStageNum();
            int accumulatedScore = hangmanGameStat.getAccumulatedScore();
            hangmanGameStat.resetGameStatus();
            if (falseGuess > 0) {
              hangmanGameStat.setFalseGuess(falseGuess - 1);
            }
            hangmanGameStat.setAccumulatedScore(accumulatedScore);
            hangmanGameStat.setStageNum(stageNum);
            hangmanGameStat.setGender(originalGender);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            startActivity(intent);
            finish();
          }
        });
  }

  /** Event that happens after mainMenu button is clicked. */
  private void setMainMenuButton() {
    mainMenu.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HangmanMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            startActivity(intent);
            finish();
          }
        });
  }

  /** Event that happens after backToHome button is clicked. */
  private void setBackToHome() {
    backToHome.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            stopService(new Intent(getApplicationContext(), HangmanBackgroundMusic.class));
            Intent intent = new Intent(getApplicationContext(), GameMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            intent.putExtra("user", hangmanGameStat.getName());
            startActivity(intent);
            finish();
          }
        });
  }

  /** Resumes this activity and checks whether the background music should be played or not. */
  @Override
  protected void onResume() {
    super.onResume();
    if (HangmanMain.isPlaying) {
      startService(new Intent(this, HangmanBackgroundMusic.class));
    }
  }

  /** Pause the Game. */
  @Override
  protected void onPause() {
    super.onPause();
    if (BackgroundMusic.isApplicationSentToBackground(this)) {
      stopService(new Intent(this, HangmanBackgroundMusic.class));
    }
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameManager.saveGame(hangmanGameStat);
  }
}
