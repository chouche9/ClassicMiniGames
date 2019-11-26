package com.example.myapplication.SpaceShooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;
import com.example.myapplication.SpaceShooter.shootergameview.ShooterGameView;

import java.util.Random;

public class ShooterEnemy1 extends ShooterGameObject implements Parcelable {
    public static int level = 1;
    public ShooterEnemy1(Context context){

        switch (level){
            case 1:
                object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy1);
                break;
            case 2:
                object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy2);
                break;
        }
        object = Bitmap.createScaledBitmap(
                object, 180, 180, false);
        resetPosition();
        setWidthHeight();
        velocity = 40;
    }

    protected ShooterEnemy1(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public void setUpBitmap(Context context) {
        switch (level){
            case 1:
                object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy1);
                break;
            case 2:
                object = BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy2);
                break;
        }
        object = Bitmap.createScaledBitmap(
                object, 150, 150, false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShooterEnemy1> CREATOR = new Creator<ShooterEnemy1>() {
        @Override
        public ShooterEnemy1 createFromParcel(Parcel in) {
            return new ShooterEnemy1(in);
        }

        @Override
        public ShooterEnemy1[] newArray(int size) {
            return new ShooterEnemy1[size];
        }
    };

    private void resetPosition(){
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth- 180));
        setY(-100);
    }
}
