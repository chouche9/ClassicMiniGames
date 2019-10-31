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

public class HangmanGame extends AppCompatActivity {

    private static final String MESSAGE = "message";
    private static final String SCORE_MESSAGE = "score";

    private ImageView hangmanImage;
    private TextView txtMaskedWord;
    private EditText edtLetterGuess;
    private TextView txtLettersGuessed;
    private TextView txtScore;
    private Button btnGuess;

    private int score;
    private int falseGuess;
    private int[] pictures;
    private HangmanGameStat hangmanGameStat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game);

        Intent intent = getIntent();
        hangmanGameStat = intent.getParcelableExtra(HangmanMain.getGamestatusMsg());

        hangmanImage = findViewById(R.id.hangmanImage);
        txtMaskedWord = findViewById(R.id.txtMaskedWord);
        edtLetterGuess = findViewById(R.id.edtLetterGuess);
        txtLettersGuessed = findViewById(R.id.txtLettersGuessed);
        txtScore = findViewById(R.id.txtScore);
        btnGuess = findViewById(R.id.btnGuess);
        score = hangmanGameStat.getScore();
        falseGuess = -1;

        assert hangmanGameStat != null;
        String gender = hangmanGameStat.getGender();

        if (gender.equals("FEMALE")) {
            pictures  = new int[] {R.drawable.female_head,
                    R.drawable.female_leftarm, R.drawable.female_rightarm,
                    R.drawable.female_body,
                    R.drawable.female_leftleg, R.drawable.female_rightleg};
        } else {
            pictures  = new int[] {R.drawable.male_head,
                    R.drawable.male_leftarm, R.drawable.male_rightarm,
                    R.drawable.male_body,
                    R.drawable.male_leftleg, R.drawable.male_rightleg};
        }

        setBtnGuess();
    }

    private void setBtnGuess() {
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtLetterGuess.getText().length() > 0) {
                    String letterGuessed = edtLetterGuess.getText().toString().toLowerCase();
                    char c = letterGuessed.charAt(0);
                    if (hangmanGameStat.checkLetterInGuessed(c)) {
                        hangmanGameStat.checkLetter(c);
                        if (score != hangmanGameStat.getScore()) {
                            if (falseGuess < 5) {
                                falseGuess++;
                                hangmanGameStat.setFalseGuess(falseGuess);
                            }
                            hangmanImage.setImageResource(pictures[falseGuess]);
                        }
                        score = hangmanGameStat.getScore();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Letter " + c + " is already used! Try again.",
                                Toast.LENGTH_SHORT).show();
                    }

                    txtScore.setText(String.valueOf(hangmanGameStat.getScore()));
                    txtMaskedWord.setText(hangmanGameStat.getDisplayedMaskedWord().toString());
                    txtLettersGuessed.setText(hangmanGameStat.getLettersGuessed().toString());
                    edtLetterGuess.setText("");

                    checkIfGameEnded();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please input a letter!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkIfGameEnded() {
        int score = hangmanGameStat.getScore();
        char[] maskedCharArray = hangmanGameStat.getMaskedWordCharArray();
        if (score == 0 || !String.valueOf(maskedCharArray).contains("_")) {
            // intent opens up the "game lost" activity
            Intent intent = new Intent(this, HangmanPlayAgain.class);
            if (score == 0) {
                intent.putExtra(MESSAGE, "You lost! The correct word was " +
                        hangmanGameStat.getSecretWord() + "!");
            } else {
                intent.putExtra(MESSAGE, "Congratulations, you won!");
            }
            intent.putExtra(SCORE_MESSAGE, String.valueOf(score));
            intent.putExtra(HangmanMain.getGamestatusMsg(), hangmanGameStat);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        HangmanGameManager hangmanGameManager = HangmanGameManager.getInstance(this);
        hangmanGameManager.saveGame(hangmanGameStat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hangmanGameStat.played) {
            HangmanWordGenerator wg = new HangmanWordGenerator(this);
            String chosenWord = wg.getChosenWord();
            hangmanGameStat.generateWord(chosenWord);
        }
        score = hangmanGameStat.getScore();
        falseGuess = hangmanGameStat.getFalseGuess();
        if (falseGuess > -1) {
            hangmanImage.setImageResource(pictures[falseGuess]);
        }
        txtLettersGuessed.setText(hangmanGameStat.getLettersGuessed());
        txtScore.setText(String.valueOf(score));
        txtMaskedWord.setText(hangmanGameStat.getDisplayedMaskedWord().toString());
    }

    public static String getMessage() {
        return MESSAGE;
    }

    public static String getScoreMessage() {
        return SCORE_MESSAGE;
    }
}
