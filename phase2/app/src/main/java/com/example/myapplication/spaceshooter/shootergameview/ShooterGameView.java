package com.example.myapplication.spaceshooter.shootergameview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.gameobject.ShooterPlane;
import com.example.myapplication.spaceshooter.shootergamestatus.ShooterGameStatusFacade;
import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOver;


/**
 * The type Shooter game view.
 */
public class ShooterGameView extends View {
    /**
     * The Enemy down.
     */
    static int enemyDown = 0;
    /**
     * The Bullet load.
     */
    static int bulletLoad = 0;
    /**
     * The Shooter game status.
     */
    ShooterGameStatusFacade shooterGameStatus;
    /**
     * The Background.
     */
    Bitmap background;
    /**
     * The Shooter colision manager.
     */
    ShooterColisionManager shooterColisionManager;
    /**
     * The Load item manager.
     */
    ShooterLoadItemManager loadItemManager;
    /**
     * The Draw item manager.
     */
    ShooterDrawItemManager drawItemManager;
    /**
     * The Bitmap manager.
     */
    ShooterBitmapManager bitmapManager;
    /**
     * The Plane.
     */
    ShooterPlane plane;
    /**
     * The Rect.
     */
    Rect rect;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Count down timer.
     */
    CountDownTimer countDownTimer;
    /**
     * The Handler.
     */
    Handler handler;
    /**
     * The Runnable.
     */
    Runnable runnable;
    /**
     * The Finish.
     */
    int finish = 0;
    /**
     * The constant dWidth.
     */
    public static int dWidth;
    /**
     * The D height.
     */
    static int dHeight;
    /**
     * The Update millisecond.
     */
    final long UPDATE_MILLIS = 60;
    /**
     * The sound pool storing the sound effect.
     */
    SoundPool sp;
    private boolean activityFinish;

    /**
     * Instantiates a new Shooter game view.
     *
     * @param context the context
     */
    public ShooterGameView(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();

    }

    /**
     * Set shooter game status.
     *
     * @param shooterGameStatus the shooter game status
     */
    public void setShooterGameStatus(ShooterGameStatusFacade shooterGameStatus){
        this.shooterGameStatus = shooterGameStatus;
        plane = shooterGameStatus.getShooterGameLevelManager().getPlane();
        setBackground();
        setUpSoundPool();
        this.shooterColisionManager = new ShooterColisionManager(shooterGameStatus, context, sp);
        this.loadItemManager = new ShooterLoadItemManager(shooterGameStatus, context, sp);
        this.drawItemManager = new ShooterDrawItemManager(shooterGameStatus);
        this.bitmapManager = new ShooterBitmapManager(context, shooterGameStatus);
        bitmapManager.loadBitmap();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
    private void setUpSoundPool(){
        sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        enemyDown = sp.load(context, R.raw.enemy1_down, 1);
        bulletLoad = sp.load(context, R.raw.fire, 1);

    }

    /**
     * Start timer of the game.
     */
    public void startTimer(){
        countDownTimer = new CountDownTimer(shooterGameStatus.getShooterGameLevelManager().getMillsecondLeft(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                shooterGameStatus.getShooterGameLevelManager().setMillisecondLeft(
                        (int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                shooterGameStatus.getShooterCrossLevelManager().setLevelFinish(true);

                if (finish == 0){
                    onViewFinish();
                }
            }
        }.start();
    }

    /**
     * Set background of canvas base on game level.
     */
    void setBackground(){
        switch (shooterGameStatus.getShooterCrossLevelManager().getLevel()){
            case 1:
                background = BitmapFactory.decodeResource(getResources(), R.drawable.psbackground1);
                break;
            case 2:
                background = BitmapFactory.decodeResource(getResources(), R.drawable.psbackground2);
                break;
        }
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);
    }

    /**
     * draw items on canvas
     * @param canvas canvas of ShooterGame
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rect, null);
        loadItemManager.loadItem();
        shooterColisionManager.handleCollision();
        drawItemManager.setCanvas(canvas);
        drawItemManager.draw();
        if(shooterGameStatus.getShooterCrossLevelManager().isGameSuccess() &&
                !shooterGameStatus.getShooterCrossLevelManager().isLevelFinish() &&
                !activityFinish){
            handler.postDelayed(runnable, UPDATE_MILLIS);}
        else {
            if(finish == 0 && !activityFinish){
                onViewFinish();
                countDownTimer.cancel();
            }
            else {
                countDownTimer.cancel();
            }
        };
    }

    /**
     * set up plane's location base on the touch location
     * @param event the touching motion
     * @return whether touch succeed
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();
        int planewidth = plane.getWidth()/2;
        int planeHeight = plane.getHeight()/2;
        if (action == MotionEvent.ACTION_MOVE) {
            if (!plane.touchInRange(touchX, touchY)){
                return true;
            }
            if (touchX < planewidth){
                touchX = planewidth;
            }
            else if (touchX > dWidth - planewidth){
                touchX = dWidth - planewidth;
            }
            if (touchY < 0){
                touchY = 0;
            }
            else if (touchY > dHeight -planeHeight){
                touchY = dHeight - planeHeight;
            }
            plane.setX((int)touchX-planewidth);
            plane.setY((int)touchY -planeHeight);
        }
        return true;
    }

    /**
     * instruction when view finish.
     */
    void onViewFinish(){
        Intent intent = new Intent(context, ShooterGameOver.class);
        finish ++;
        intent.putExtra("gameStatus", shooterGameStatus);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    /**
     * Set activity finish.
     *
     * @param finish the finish
     */
    public void setActivityFinish(boolean finish){
        this.activityFinish = finish;
    }

    /**
     * Get activity finish boolean.
     *
     * @return the boolean
     */
    public boolean getActivityFinish(){
        return activityFinish;
    }

}

