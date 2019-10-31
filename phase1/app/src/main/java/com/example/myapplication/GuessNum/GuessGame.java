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

public class GuessGame extends AppCompatActivity {
    private int targetNum;
    private TextView tries;
    private TextView guessInfo;
    private EditText userNumberEditText;
    private Button checkButton;
    private int colorNum;
    private int numTries;
    private TextView triesNum;
    private Intent intent1;
    private TextView bestText;
    private TextView bestNum;
    private final int REQUEST_CODE3 = 3;
    private GuessGameStat gamer;

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
        intent1 = getIntent();
        gamer = intent1.getParcelableExtra("gamer");
        setBestNum();
        setTextColor(gamer.getColor());
        triesNum.setText("" + gamer.getCurrentTries());
        createRanNum();
    }
    public void checkNumber(View view){
        String num = userNumberEditText.getText().toString();
        if (!num.isEmpty()){
            gamer.setCurrentTries();
            numTries = gamer.getCurrentTries();
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
    void setBestNum(){
        int bestTries = gamer.getBestTries();
        if (bestTries != -1){
            bestNum.setText(String.valueOf(bestTries));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE3){
            setResult(RESULT_OK, data);
            finish();
        }
    }
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
    private void setToastColor(Toast toast){
        TextView v = toast.getView().findViewById(android.R.id.message);
        v.setTextColor(colorNum);
    }

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
     */
    @Override
    protected void onPause() {
        super.onPause();
        GuessGameManager gameManager = GuessGameManager.getInstance(this);
        gameManager.saveUsers(gamer);
    }
}

