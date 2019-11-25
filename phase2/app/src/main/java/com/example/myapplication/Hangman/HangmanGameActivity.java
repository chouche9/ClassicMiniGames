package com.example.myapplication.Hangman;

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

/**
 * The User Interface of this Hangman Game.
 */
public class HangmanGameActivity extends AppCompatActivity
        implements HangmanGameView, HangmanDialog.HangmanDialogListener {

    /**
     * Name used globally to send/retrieve the winning/losing message to/from an intent.
     */
    private static final String MESSAGE = "message";

    /**
     * Name used globally to send/retrieve the score of this user to/from an intent.
     */
    private static final String SCORE_MESSAGE = "score";

    /**
     * Images used in this hangman game.
     */
    private ImageView hangmanImage;

    /**
     * Text that displays the current masked word (words with underscores) to the display screen.
     */
    private TextView txtMaskedWord;

    /**
     * The input field used to get the user's guessed letter.
     */
    private EditText edtLetterGuess;

    /**
     * Text that displays all the letters guessed by this user to the display screen.
     */
    private TextView txtLettersGuessed;

    /**
     * Text that displays the current score of this user.
     */
    private TextView txtScore;

    /**
     * Button that the user clicks to guess a letter.
     */
    private Button btnGuessLetter;

    /**
     * Button that the user clicks to guess the full word.
     */
    private Button btnGuessWord;

    /**
     * The dialog that pops up when the user presses btnGuessWord.
     */
    private HangmanDialog dialog;

    /**
     * Array for storing all possible pictures for this Hangman Game (specific to the gender chosen).
     */
    private int[] pictures;

    /**
     * Number of false guesses made by this user so far.
     */
    private int picture_index;

    /**
     * The presenter of this hangman game.
     */
    private HangmanGamePresenter hangmanGamePresenter;

    private TextView txtStageNum;

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

        ConstraintLayout layout =(ConstraintLayout)findViewById(R.id.hangman_game_layout);

        picture_index = 0;

        Intent intent = getIntent();
        HangmanGameStatInteractor hangmanGameStat =
                intent.getParcelableExtra(HangmanMain.getGamestatusMsg());

        hangmanGamePresenter = new HangmanGamePresenter(this, hangmanGameStat);

        // Review Below!
        assert hangmanGameStat != null;
        String gender = hangmanGameStat.getGender();

        if (gender.equals("FEMALE")) {
            pictures =
                new int[]{
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
                new int[]{
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
    }

    /**
     * Event that happens after the btnGuessLetter button is clicked.
     */
    private void setBtnGuessLetter() {
        btnGuessLetter.setOnClickListener(
                new View.OnClickListener() {
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

    /**
     * Event that happens after the btnGuessWord button is clicked.
     */
    private void setBtnGuessWord() {
        btnGuessWord.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDialog();
                    }
                });
    }

    /**
     * Opens the dialog for guessing the full word.
     */
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

    /**
     * Pauses this HangmanGame activity.
     */
    @Override
    protected void onPause() {
        super.onPause();
        HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
        hangmanGameManager.saveGame(hangmanGamePresenter.getHangmanGameStat());
    }

    /**
     * Resumes this HangmanGame activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
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
        Toast.makeText(getApplicationContext(), "Please input a letter!", Toast.LENGTH_SHORT).show();
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
        txtScore.setText(String.valueOf(score));
    }

    @Override
    public void setPictureIndex(int index) {
        picture_index = index;
    }

    @Override
    public void showTxtStageNum(int stageNum) {
        txtStageNum.setText(String.valueOf(stageNum));
    }

    @Override
    public void gameEnded(HangmanGameStatInteractor hm) {
        // intent opens up the "game lost" activity
        Intent intent = new Intent(this, HangmanStageEnded.class);
        intent.putExtra(HangmanMain.getGamestatusMsg(), hm);
        startActivity(intent);
        finish();
    }

    @Override
    public void showGuessWordFailed() {
        Toast.makeText(getApplicationContext(), "You guessed the wrong word!", Toast.LENGTH_SHORT)
                .show();
    }
}
