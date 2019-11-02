package com.example.myapplication.GuessNum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;

import java.util.Random;
/**
 * the activity class that play the GuessNumber Game.
 */
public class GuessGame extends AppCompatActivity {
    /**
     * the correct value of this guessing game
     */
    private int targetNum;
    /**
     * textView that display "Tries"
     */
    private TextView tries;
    /**
     * instruction of number range current answer is in
     */
    private TextView guessInfo;
    /**
     * userNumber that player enter
     */
    private EditText userNumberEditText;
    /**
     * button for check value
     */
    private Button checkButton;
    /**
     * the integer value of color
     */
    private int colorNum;
    /**
     * the current Tries this user has displaying on screen in TextView
     */
    private TextView triesNum;
    /**
     * textView that display "Best"
     */
    private TextView bestText;
    /**
     * the best Tries this user has displaying on screen in TextView
     */
    private TextView bestNum;
    /**
     * request_code pass in to start activity
     */
    private final int REQUEST_CODE3 = 3;
    /**
     * the GuessGameStat of the user that's playing right now.
     */
    private GuessGameStat gamer;
    /**
     * create GuessGame activity
     * @param savedInstanceState bundle of the resource in this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_game);
        tries = findViewById(R.id.tries);
        guessInfo = findViewById(R.id.guess_info);
        userNumberEditText = findViewById(R.id.userNumberEditText);
        checkButton = findViewById(R.id.checkButton);
        triesNum = findViewById(R.id.Num);
        bestText = findViewById(R.id.bestText);
        bestNum = findViewById(R.id.bestNum);
        Intent intent1 = getIntent();
        gamer = intent1.getParcelableExtra("gamer");
        setBestNum();
        setTextColor(gamer.getColor());
        triesNum.setText("" + gamer.getCurrentTries());
        createRanNum();
    }

    /**
     * method that check if the user's number match the target number
     * @param view the button's view
     */
    public void checkNumber(View view){
        String num = userNumberEditText.getText().toString();
        if (!num.isEmpty()){
            gamer.setCurrentTries();

            int numTries = gamer.getCurrentTries();
            triesNum.setText("" + numTries);
            int userNum = Integer.parseInt(num);
            if (userNum == targetNum){
                Intent intent1 = new Intent(GuessGame.this, GuessCongradulation.class);
                intent1.putExtra("gamer", gamer);
                startActivityForResult(intent1, REQUEST_CODE3);
            }
            else if (userNum < targetNum){
                Toast toast= Toast.makeText(GuessGame.this, "Actual Number is higher", Toast.LENGTH_SHORT);
                setToastColor(toast);
                toast.show();
            }
            else{
                Toast toast= Toast.makeText(GuessGame.this, "Actual Number is lower", Toast.LENGTH_SHORT);
                setToastColor(toast);
                toast.show();
            }
            triesNum.setText(Integer.toString(numTries));
        }
        else {
            Toast toast= Toast.makeText(GuessGame.this, "Enter number", Toast.LENGTH_SHORT);
            setToastColor(toast);
            toast.show();
        }
    }

    /**
     * set up the best number of tries displaying on screen
     */
    void setBestNum(){
        int bestTries = gamer.getBestTries();
        if (bestTries != -1){
            bestNum.setText(String.valueOf(bestTries));
        }
    }

    /**
     * pass the data to the activity below
     * @param requestCode: the code used to match the correct activity GuessGame lunch
     * @param resultCode: represent whether the result is okay or not
     * @param data: the intent that used to pass info
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE3){
            setResult(RESULT_OK, data);
            finish();
        }
    }

    /**
     * create random guessing number
     */
    private void createRanNum(){
        if(gamer.getLastNum() != 0){
            targetNum = gamer.getLastNum();
        }
        else{
            Random random = new Random();
            targetNum = random.nextInt(100) + 1;
            gamer.setLastNum(targetNum);
        }
    }

    /**
     * set the toast color
     * @param toast the toast we want to set
     */
    private void setToastColor(Toast toast){
        TextView v = toast.getView().findViewById(android.R.id.message);
        v.setTextColor(colorNum);
    }

    /**
     * set all texts' color to colr
     * @param color color we want to set for texts
     */
    private void setTextColor(String color){
        if (color.equals("yellow")){
            colorNum =  getResources().getColor(R.color.yellow);
        }
        else if (color.equals("red")){
            colorNum =  getResources().getColor(R.color.red);
        }
        else {
            colorNum = getResources().getColor(R.color.green);
        }
        tries.setTextColor(colorNum);
        guessInfo.setTextColor(colorNum);
        userNumberEditText.setTextColor(colorNum);
        userNumberEditText.setHintTextColor(colorNum);
        Drawable drawable = userNumberEditText.getBackground();
        drawable.setColorFilter(colorNum, PorterDuff.Mode.SRC_ATOP);
        userNumberEditText.setBackgroundDrawable(drawable);
        checkButton.setBackgroundColor(colorNum);
        triesNum.setTextColor(colorNum);
        bestText.setTextColor(colorNum);
        bestNum.setTextColor(colorNum);
    }

    /**
     * Dispatch onPause() to fragments.
     * store new GuessGameStat.
     */
    @Override
    protected void onPause() {
        super.onPause();
        GuessGameManager gameManager = GuessGameManager.getInstance(this);
        gameManager.saveGame(gamer);
    }
}

