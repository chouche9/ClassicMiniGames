package com.example.myapplication.GuessNum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;


public class GuessSetting extends AppCompatActivity implements View.OnClickListener{
    private Button yellowButton;
    private Button greenButton;
    private Button redButton;
    private Intent intent1;
    private GuessGameStat p1;
    final int REQUEST_CODE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_setting);
        yellowButton = findViewById(R.id.yellowButton);
        greenButton = findViewById(R.id.greenButton);
        redButton = findViewById(R.id.redButton);
        yellowButton.setOnClickListener(this);
        greenButton.setOnClickListener(this);
        redButton.setOnClickListener(this);
        intent1 = getIntent();
        p1 = intent1.getParcelableExtra("gamer");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
    private void startGame(){
        Intent intent1 = new Intent(GuessSetting.this, GuessGame.class);
        intent1.putExtra("gamer", p1);
        startActivityForResult(intent1, REQUEST_CODE);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode  == REQUEST_CODE){
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
