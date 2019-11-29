package com.example.myapplication.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.bonuslevel.BonusLevelDialog;
import com.example.myapplication.mainpage.GameMain;
import com.example.myapplication.R;

/** Activity that get activated when a stage ends in hangman. */
public class HangmanStageEnded extends AppCompatActivity
    implements BonusLevelDialog.BonusLevelDialogListener, View.OnClickListener {

  /** A HangmanGameStatInteractor that stores the hangman game status. */
  private HangmanGameStatFacade hangmanGameStat;

  /** A String to store the original gender */
  private String originalGender;

  /** Button that user clicks to play again. */
  private Button playAgain;

  /** Button that user clicks to go back to the main menu of the Hangman game. */
  private Button mainMenu;

  /** Button that the user clicks to go back to the home page of the app. */
  private Button backToHome;

  private Button nextStage;

  private Button bonusLevel;

  private TextView txtFirstMessage;

  private TextView txtValueMessage;

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

    Intent received_intent = getIntent();
    hangmanGameStat = received_intent.getParcelableExtra(HangmanMain.getGamestatusMsg());
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
      firstMessage = "Congratulations, you guessed the correct word!";
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

  @Override
  public void onClick(View view) {
    if (view == btnPlayMusic) {
      startService(new Intent(this, HangmanBackgroundMusic.class));
    } else if (view == btnStopMusic) {
      stopService(new Intent(this, HangmanBackgroundMusic.class));
    }
  }

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

  private void openDialog() {
    dialog = new BonusLevelDialog();
    dialog.show(getSupportFragmentManager(), "Bonus Level Dialog");
  }

  @Override
  public void bonusLevelResult(boolean isWon, int bonusScore) {

    String firstMessage;
    String valueMessage;

    if (isWon) {
      hangmanGameStat.addAccumulatedScore(bonusScore);
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

  @Override
  public void onCancel() {
    bonusLevel.setEnabled(false);
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

  /** Pause the Game */
  @Override
  protected void onPause() {
    super.onPause();
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameManager.saveGame(hangmanGameStat);
  }
}
