package com.example.myapplication.Hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

/** The User Interface of this Hangman Game. */
public class HangmanGameActivity extends AppCompatActivity implements HangmanGameView{

  /** Name used globally to send/retrieve the winning/losing message to/from an intent. */
  private static final String MESSAGE = "message";

  /** Name used globally to send/retrieve the score of this user to/from an intent. */
  private static final String SCORE_MESSAGE = "score";

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
  private Button btnGuess;

  /**
   * Array for storing all possible pictures for this Hangman Game (specific to the gender chosen).
   */
  private int[] pictures;

  /** Number of false guesses made by this user so far. */
  private int picture_index;

  /** The game state object of this user. */
//  private HangmanGameStat hangmanGameStat;

  private HangmanGamePresenter hangmanGamePresenter;

  /**
   * Initializes this HangmanGame activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hangman_game);

    Intent intent = getIntent();
    HangmanGameStatInteractor hangmanGameStat = intent.getParcelableExtra(HangmanMain.getGamestatusMsg());

    hangmanGamePresenter = new HangmanGamePresenter(this, hangmanGameStat);

    hangmanImage = findViewById(R.id.hangmanImage);
    txtMaskedWord = findViewById(R.id.txtMaskedWord);
    edtLetterGuess = findViewById(R.id.edtLetterGuess);
    txtLettersGuessed = findViewById(R.id.txtLettersGuessed);
    txtScore = findViewById(R.id.txtScore);
    btnGuess = findViewById(R.id.btnGuess);

    picture_index = -1;

    //Review Below!
    assert hangmanGameStat != null;
    String gender = hangmanGameStat.getGender();

    if (gender.equals("FEMALE")) {
      pictures =
          new int[] {
            R.drawable.female_head,
            R.drawable.female_leftarm,
            R.drawable.female_rightarm,
            R.drawable.female_body,
            R.drawable.female_leftleg,
            R.drawable.female_rightleg
          };
    } else {
      pictures =
          new int[] {
            R.drawable.male_head,
            R.drawable.male_leftarm,
            R.drawable.male_rightarm,
            R.drawable.male_body,
            R.drawable.male_leftleg,
            R.drawable.male_rightleg
          };
    }

    setBtnGuess();
  }

  /** Pauses this HangmanGame activity. */
  @Override
  protected void onPause() {
    super.onPause();
    HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
    hangmanGameManager.saveGame(hangmanGamePresenter.getHangmanGameStat());
//    hangmanGameManager.saveGame(hangmanGameStat);
  }

  /** Resumes this HangmanGame activity. */
  @Override
  protected void onResume() {
    super.onResume();

    if (!hangmanGamePresenter.getPlayed()) {
      hangmanGamePresenter.getNewWord();
    }

    if (picture_index > -1) {
      showImage();
    }
    hangmanGamePresenter.onResuming();

    // ANY LOGIC CANNOT BE IN THE UI
//    if (!hangmanGameStat.getPlayed()) {
//      HangmanWordGenerator wg = new HangmanWordGenerator(this);
//      String chosenWord = wg.getChosenWord();
//      hangmanGameStat.generateWord(chosenWord);
//    }
//    int score = hangmanGameStat.getScore();
//    picture_index = hangmanGameStat.getFalseGuess();
//    if (picture_index > -1) {
//      hangmanImage.setImageResource(pictures[picture_index]);
//    }
//    txtLettersGuessed.setText(hangmanGameStat.getLettersGuessed());
//    txtScore.setText(String.valueOf(score));
//    txtMaskedWord.setText(hangmanGameStat.getDisplayedMaskedWord().toString());
  }

  /**
   * Getter for MESSAGE attribute.
   *
   * @return String the MESSAGE attribute.
   */
  public static String getMessage() {
    return MESSAGE;
  }

  /**
   * Getter for the SCORE_MESSAGE attribute.
   *
   * @return String the SCORE_MESSAGE attribute.
   */
  public static String getScoreMessage() {
    return SCORE_MESSAGE;
  }

  @Override
  public void showEmptyError() {
    Toast.makeText(getApplicationContext(), "Please input a letter!", Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void showLetterUsedError(char c) {
    Toast.makeText(
            getApplicationContext(),
            "Letter " + c + " is already used! Try again.",
            Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void showImage() {
    hangmanImage.setImageResource(pictures[picture_index]);
  }

  @Override
  public void showTxtMaskedWord(String word) {
    txtMaskedWord.setText(word);
  }

  @Override
  public void clearEdtLetterGuess() {
    edtLetterGuess.setText("");
  }

  @Override
  public void showLettersGuessed(String word) {
    txtLettersGuessed.setText(word);
  }

  @Override
  public void showTxtScore(int score) {
    txtScore.setText(score);
  }

  @Override
  public void setPictureIndex(int index) {
    picture_index = index;
  }

  @Override
  public void gameEnded(String message, int score, HangmanGameStatInteractor hm) {
    //intent opens up the "game lost" activity
    Intent intent = new Intent(this, HangmanPlayAgain.class);

    intent.putExtra(MESSAGE, message);
    intent.putExtra(SCORE_MESSAGE, String.valueOf(score));
    intent.putExtra(HangmanMain.getGamestatusMsg(), hm);
    startActivity(intent);
  }

  private void setBtnGuess() {
    btnGuess.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (edtLetterGuess.getText().length() > 0) {
          String letterGuessed = edtLetterGuess.getText().toString().toLowerCase();
          char c = letterGuessed.charAt(0);
          hangmanGamePresenter.validateChar(c);
        } else {
          showEmptyError();
        }
      }
    });

  }
  //   /** Event that happens when the btnGuess button is clicked. */
//  private void setBtnGuess() {
//    btnGuess.setOnClickListener(
//        new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//            if (edtLetterGuess.getText().length() > 0) {
//              // convert letter guessed to lower case in case the user inputs upper case letter
//              String letterGuessed = edtLetterGuess.getText().toString().toLowerCase();
//              char c = letterGuessed.charAt(0);
//
//              // check if the letter was already guessed
//              if (hangmanGameStat.checkLetterInGuessed(c)) {
//                // if score has changed, user made a wrong guess
//                if (!hangmanGameStat.checkLetter(c)) {
//                  if (hangmanGameStat.getFalseGuess() < 5) {
//                    picture_index++;
//                  }
//                  // change image
//                  showImage();
//                }
//              } else {
//                showLetterUsedError(c);
//              }
//
//              txtScore.setText(String.valueOf(hangmanGameStat.getScore()));
//              txtMaskedWord.setText(hangmanGameStat.getDisplayedMaskedWord().toString());
//              txtLettersGuessed.setText(hangmanGameStat.getLettersGuessed().toString());
//              edtLetterGuess.setText("");
//
//              checkIfGameEnded();
//            } else {
//              showEmptyError();
//            }
//          }
//        });
//  }
//
//  /**
//   * Checks if the game has ended (either user has won or lost) and sends intent to HangmanPlayAgain
//   * activity.
//   */
//  private void checkIfGameEnded() {
//    if (hangmanGameStat.checkIfGameEnded()) {
//      // intent opens up the "game lost" activity
//      Intent intent = new Intent(this, HangmanPlayAgain.class);
//      int score = hangmanGameStat.getScore();
//      if (score == 0) {
//        intent.putExtra(
//            MESSAGE, LOSEMESSSAGE + hangmanGameStat.getSecretWord() + "!");
//      } else {
//        intent.putExtra(MESSAGE, WINMESSAGE);
//      }
//      intent.putExtra(SCORE_MESSAGE, String.valueOf(score));
//      intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
//      startActivity(intent);
//    }
//  }
}
