package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

import java.util.Random;

/**
 * The type Shooter enemy.
 */
public class ShooterEnemy extends ShooterGameObject implements Parcelable {
    /**
     * The constant level.
     */
    public static int level = 1;

    /**
     * Instantiates a new Shooter enemy 1 base on the level
     *
     * @param context the context
     */
    public ShooterEnemy(Context context){

        switch (level){
            case 1:
                setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy1));
                break;
            case 2:
                setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy2));
                break;
        }
        setObject(Bitmap.createScaledBitmap(
                getObject(), 180, 180, false));
        resetPosition();
        setWidthHeight();
        setVelocity(40);
    }

    /**
     * Instantiates a new Shooter enemy 1.
     *
     * @param in the parcel
     */
    private ShooterEnemy(Parcel in) {
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
                setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy1));
                break;
            case 2:
                setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psenemy2));
                break;
        }
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterEnemy> CREATOR = new Creator<ShooterEnemy>() {
        @Override
        public ShooterEnemy createFromParcel(Parcel in) {
            return new ShooterEnemy(in);
        }

        @Override
        public ShooterEnemy[] newArray(int size) {
            return new ShooterEnemy[size];
        }
    };

    /**
     * reset position for Enemy.
     */
    private void resetPosition(){
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth- 180));
        setY(-100);
    }
}
