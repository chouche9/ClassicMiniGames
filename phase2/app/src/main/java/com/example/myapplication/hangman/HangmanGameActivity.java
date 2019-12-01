package com.example.myapplication.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.backgroundmusic.BackgroundMusic;

/** The User Interface of this Hangman Game. */
public class HangmanGameActivity extends AppCompatActivity
    implements HangmanGameView, HangmanDialog.HangmanDialogListener, View.OnClickListener {

  /** Images used in this hangman game. */
  private ImageView hangmanImage;

  /** Text that displays the current masked word (words with underscores) to the display screen. */
  private TextView txtMaskedWord;

  /** The input field used to get the user's guessed letter. */
  private EditText edtLetterGuess;

  /** Text that displays all the letters guessed by this user to the display screen. */
  private TextView txtLettersGuessed;

  /** Text that displays the current score of this user. */
  private TextView txtScore;

  /** Button that the user clicks to guess a letter. */
  private Button btnGuessLetter;

  /** Button that the user clicks to guess the full word. */
  private Button btnGuessWord;

  /** The dialog that pops up when the user presses btnGuessWord. */
  private HangmanDialog dialog;

  /** Array for storing all possible pictures for this Hangman Game depending on gender. */
  private int[] pictures;

  /** Number of false guesses made by this user so far. */
  private int picture_index;

  /** The presenter of this hangman game. */
  private HangmanGamePresenter hangmanGamePresenter;

  /** Text that displays the current stage number. */
  private TextView txtStageNum;

  /** Button that the user clicks to play background music. */
  private Button btnPlayMusic;

  /** Button that the user clicks to stop background music. */
  private Button btnStopMusic;

  /**
   * Initializes this HangmanGame activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hangman_game);

    hangmanImage = findViewById(R.id.hangmanImage);
    txtMaskedWord = findViewById(R.id.txtMaskedWord);
    edtLetterGuess = findViewById(R.id.edtLetterGuess);
    txtLettersGuessed = findViewById(R.id.txtLettersGuessed);
    txtScore = findViewById(R.id.txtScore);
    btnGuessLetter = findViewById(R.id.btnGuess);
    btnGuessWord = findViewById(R.id.btnGuessWord);
    txtStageNum = findViewById(R.id.txtStageNum);
    btnPlayMusic = findViewById(R.id.btnPlayMusic);
    btnStopMusic = findViewById(R.id.btnStopMusic);
    ConstraintLayout layout = findViewById(R.id.hangman_game_layout);
    picture_index = 0;

    Intent intent = getIntent();
    HangmanGameStatus hangmanGameStat = intent.getParcelableExtra(HangmanMain.getGamestatusMsg());
    hangmanGamePresenter =
        new HangmanGamePresenter(this, this, new HangmanGameInteractor(hangmanGameStat));

    assert hangmanGameStat != null;
    String gender = hangmanGameStat.getGender();

    if (gender.equals("FEMALE")) {
      pictures =
          new int[] {
            R.drawable.start,
            R.drawable.female_head,
            R.drawable.female_leftarm,
            R.drawable.female_rightarm,
            R.drawable.female_body,
            R.drawable.female_leftleg,
            R.drawable.female_rightleg
          };
      layout.setBackgroundResource(R.drawable.hangman_bg_female);
    } else {
      pictures =
          new int[] {
            R.drawable.start,
            R.drawable.male_head,
            R.drawable.male_leftarm,
            R.drawable.male_rightarm,
            R.drawable.male_body,
            R.drawable.male_leftleg,
            R.drawable.male_rightleg
          };
      layout.setBackgroundResource(R.drawable.hangman_bg_male);
    }

    setBtnGuessLetter();
    setBtnGuessWord();
    btnPlayMusic.setOnClickListener(this);
    btnStopMusic.setOnClickListener(this);
  }

  /**
   * Event that happens when btnPlayMusic or btnStopMusic is pressed.
   *
   * @param view the view that called this method.
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

  /** Event that happens after the btnGuessLetter button is clicked. */
  private void setBtnGuessLetter() {
    btnGuessLetter.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (edtLetterGuess.getText().length() > 0) {
              String letterGuessed = edtLetterGuess.getText().toString().toLowerCase();
              char letter = letterGuessed.charAt(0);
              hangmanGamePresenter.validateLetter(letter);
            } else {
              showEmptyError();
            }
          }
        });
  }

  /** Event that happens after the btnGuessWord button is clicked. */
  private void setBtnGuessWord() {
    btnGuessWord.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            openDialog();
          }
        });
  }

  /** Opens the dialog for guessing the full word. */
  private void openDialog() {
    dialog = new HangmanDialog();
    dialog.show(getSupportFragmentManager(), "Hangman Dialog");
  }

  /**
   * Checks whether if the full word guessed is correct or not.
   *
   * @param wordGuessed the full word guessed by the player.
   */
  @Override
  public void validateGuessedWord(String wordGuessed) {
    if (wordGuessed.length() > 0) {
      hangmanGamePresenter.validateWord(wordGuessed);
    }
    dialog.dismiss();
  }

  /** Pauses this HangmanGame activity. */
  @Override
  protected void onPause() {
    super.onPause();
    if (BackgroundMusic.isApplicationSentToBackground(this)) {
      stopService(new Intent(this, HangmanBackgroundMusic.class));
    }
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameManager.saveGame(hangmanGamePresenter.getHangmanGameStat());
  }

  /** Resumes this HangmanGame activity. */
  @Override
  protected void onResume() {
    super.onResume();
    if (HangmanMain.isPlaying) {
      startService(new Intent(this, HangmanBackgroundMusic.class));
    }
    // go back to HangmanStageEnded activity if the game has already ended before pausing the game
    if (hangmanGamePresenter.onCheckIfGameEnded()) {
      hangmanGamePresenter.onOpenGameEndedActivity();
    } else {
      if (!hangmanGamePresenter.getPlayed()) {
        hangmanGamePresenter.getNewWord();
      }
      hangmanGamePresenter.onResuming();
    }
  }

  /** Shows an empty error in the display. */
  @Override
  public void showEmptyError() {
    Toast.makeText(getApplicationContext(), "Please input a letter!", Toast.LENGTH_SHORT).show();
  }

  /**
   * Shows a letter used error in the display.
   *
   * @param letter the character that was already guessed.
   */
  @Override
  public void showLetterUsedError(char letter) {
    Toast.makeText(
            getApplicationContext(),
            "Letter " + letter + " is already used! Try again.",
            Toast.LENGTH_SHORT)
        .show();
  }

  /** Shows the hangman image in the display. */
  @Override
  public void showImage() {
    hangmanImage.setImageResource(pictures[picture_index]);
  }

  /**
   * Shows the masked word in the display.
   *
   * @param word the masked word.
   */
  @Override
  public void showTxtMaskedWord(String word) {
    txtMaskedWord.setText(word);
  }

  /** Clears the letters guessed in the display. */
  @Override
  public void clearEdtLetterGuess() {
    edtLetterGuess.setText("");
  }

  /**
   * Shows the letters guessed so far in the display.
   *
   * @param lettersGuessed the letters guessed so far.
   */
  @Override
  public void showLettersGuessed(String lettersGuessed) {
    txtLettersGuessed.setText(lettersGuessed);
  }

  /**
   * Shows the current score in the display.
   *
   * @param score the current score.
   */
  @Override
  public void showTxtScore(int score) {
    txtScore.setText(String.valueOf(score));
  }

  /**
   * Sets the picture index.
   *
   * @param index the picture index.
   */
  @Override
  public void setPictureIndex(int index) {
    picture_index = index;
  }

  /**
   * Event that happens after the current game has ended.
   *
   * @param hm the game status of the hangman game.
   */
  @Override
  public void gameEnded(HangmanGameStatus hm) {
    // intent opens up the "game lost" activity
    Intent intent = new Intent(this, HangmanStageEnded.class);
    intent.putExtra(HangmanMain.getGamestatusMsg(), hm);
    startActivity(intent);
    finish();
  }

  /** Show that the guessed word was incorrect on the display. */
  @Override
  public void showGuessWordFailed() {
    Toast.makeText(getApplicationContext(), "You guessed the wrong word!", Toast.LENGTH_SHORT)
        .show();
  }

  /**
   * Shows the current stage number in the display.
   *
   * @param stageNum the current stage number.
   */
  @Override
  public void showTxtStageNum(int stageNum) {
    txtStageNum.setText(String.valueOf(stageNum));
  }

}
