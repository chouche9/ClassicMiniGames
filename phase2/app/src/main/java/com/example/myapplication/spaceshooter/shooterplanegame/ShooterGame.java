package com.example.myapplication.spaceshooter.shooterplanegame;

import android.os.Bundle;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bonuslevel.BonusLevelDialog;
import com.example.myapplication.spaceshooter.ShooterBackGroundMusic;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

/**
 * The type Shooter game.
 */
public class ShooterGame extends AppCompatActivity implements BonusLevelDialog.BonusLevelDialogListener, ShooterGameInterface {
    /**
     * The Game view.
     */
    ShooterGameView gameView;
    /**
     * The Shooter game status.
     */
    ShooterGameStatusFacade shooterGameStatus;
    /**
     * The Shooter game presenter.
     */
    ShooterGamePresenter shooterGamePresenter;
    /**
     * the pop up guessing number game
     */
    private BonusLevelDialog dialog;

    /**
     * create this activity
     * @param savedInstanceState a bundle of the resources in this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        shooterGameStatus = getIntent().getParcelableExtra("gameStatus");
        gameView = new ShooterGameView(this);
        gameView.setShooterGameStatus(shooterGameStatus);
        ShooterPlaneGameLogic shooterPlaneGameLogic = new ShooterPlaneGameLogic(
                shooterGameStatus, this, gameView);
        shooterGamePresenter = new ShooterGamePresenter(this, shooterPlaneGameLogic);
        setContentView(gameView);
    }

    /**
     * calling presenter to handle resume when resuming game
     */
    @Override
    protected void onResume() {
        super.onResume();
        shooterGamePresenter.HandleOnResume();
    }

    /**
     * calling presenter to handle pause when pause game
     */
    @Override
    protected void onPause() {
        super.onPause();
        shooterGamePresenter.HandleOnPause();

    }

    /**
     * Activate bonus game by pasing to presenter.
     */
    public void activateBonusGame(){
        shooterGamePresenter.activateBonusGame();
    }

    /**
     * open guess number dialog
     */
    public void openDialog(){
        dialog = new BonusLevelDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "Bonus Level Dialog");
    }
    /**
     * Make bonus win toast.
     */
    @Override
    public void makeBonusWinToast() {
        Toast.makeText(this,
                "You guessed the correct number!\nPlus 100 points!",
                Toast.LENGTH_SHORT).show();
    }
    /**
     * Make bonus lose toast.
     */
    @Override
    public void makeBonusLoseToast() {
        Toast.makeText(this, "Try Again Next Time!", Toast.LENGTH_SHORT).show();
    }

    /**
     * handling the bonus game result in presenter
     * @param isWon: boolean to show whether game isWon or not
     * @param bonusSore: the score of the game
     */
    @Override
    public void bonusLevelResult(boolean isWon, int bonusSore) {
        shooterGamePresenter.handleBonusGameResult(isWon, bonusSore);
        onCancel();
    }

    /**
     * handle cancel bonus game in presenter
     */
    @Override
    public void onCancel() {
        shooterGamePresenter.cancelBonus();
    }
    /**
     * Start music.
     */
    @Override
    public void startMusic() {
        startService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }
    /**
     * Stop music.
     */
    @Override
    public void stopMusic() {
        stopService(new Intent(getApplicationContext(), ShooterBackGroundMusic.class));
    }
    /**
     * Dismiss dialog.
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

}
