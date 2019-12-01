package com.example.myapplication.spaceshooter.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.shootergameview.ShooterGameView;

import java.util.Random;

/**
 * The type Shooter bonus.
 */
public class ShooterBonus extends ShooterGameObject {

    /**
     * Instantiates a new Shooter bonus.
     *
     * @param context the context
     */
    public ShooterBonus(Context context) {
        Random random = new Random();
        setX(random.nextInt(ShooterGameView.dWidth - 150));
        setY(-60);
        setVelocity(30);
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.treasure_chest));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
        setWidthHeight();
    }

    /**
     * Instantiates a new Shooter bonus.
     *
     * @param in the in
     */
    private ShooterBonus(Parcel in) {
        super(in);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterBonus> CREATOR = new Creator<ShooterBonus>() {
        @Override
        public ShooterBonus createFromParcel(Parcel in) {
            return new ShooterBonus(in);
        }

        @Override
        public ShooterBonus[] newArray(int size) {
            return new ShooterBonus[size];
        }
    };

    @Override
    public void setUpBitmap(Context context) {
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.treasure_chest));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }


}
