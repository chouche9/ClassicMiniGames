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
import com.example.myapplication.spaceshooter.GameObject.ShooterPlane;
import com.example.myapplication.spaceshooter.ShooterGameStatus;
import com.example.myapplication.spaceshooter.shootergameover.ShooterGameOver;


public class ShooterGameView extends View {
    static int enemyDown = 0;
    static int bulletLoad = 0;
    ShooterGameStatus shooterGameStatus;
    Bitmap background;
    ShooterColisionManager shooterColisionManager;
    ShooterLoadItemManager loadItemManager;
    ShooterDrawItemManager drawItemManager;
    ShooterBitmapManager bitmapManager;
    ShooterPlane plane;
    Rect rect;
    Context context;
    CountDownTimer countDownTimer;
    Handler handler;
    Runnable runnable;
    int finish = 0;
    public static int dWidth;
    static int dHeight;
    final long UPDATE_MILLIS = 60;
    SoundPool sp;
    private boolean activityFinish;

    public ShooterGameView(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();

    }
    public void setShooterGameStatus(ShooterGameStatus shooterGameStatus){
        this.shooterGameStatus = shooterGameStatus;
        plane = shooterGameStatus.plane;
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
    public void startTimer(){
        countDownTimer = new CountDownTimer(shooterGameStatus.millsecondLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                shooterGameStatus.millsecondLeft = (int) millisUntilFinished;
            }

            @Override
            public void onFinish() {
                shooterGameStatus.levelFinish = true;

                if (finish == 0){
                    onViewFinish();
                }
            }
        }.start();
    }

    void setBackground(){
        switch (shooterGameStatus.level){
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rect, null);
        loadItemManager.loadItem();
        shooterColisionManager.handleColision();
        drawItemManager.setCanvas(canvas);
        drawItemManager.draw();
        if(shooterGameStatus.gameSuccess && !shooterGameStatus.levelFinish && !activityFinish){
            handler.postDelayed(runnable, UPDATE_MILLIS);}
        else {
            if(finish == 0 && !activityFinish){
                onViewFinish();
                countDownTimer.cancel();
            }
            else if(finish == 0){
                countDownTimer.cancel();
            }
        };
    }

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

    void onViewFinish(){
        Intent intent = new Intent(context, ShooterGameOver.class);
        finish ++;
        intent.putExtra("gameStatus", shooterGameStatus);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
    public void setActivityFinish(boolean finish){
        this.activityFinish = finish;
    }
    public boolean getActivityFinish(){
        return activityFinish;
    }

}

