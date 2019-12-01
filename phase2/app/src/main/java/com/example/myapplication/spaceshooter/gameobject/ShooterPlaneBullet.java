package com.example.myapplication.spaceshooter.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

/**
 * The type Shooter bullet 1.
 */
public class ShooterPlaneBullet extends ShooterGameObject implements Parcelable {
    /**
     * Instantiates a new Shooter bullet 1.
     *
     * @param context the context
     * @param pX      the p x coordinate
     * @param pY      the p y coordinate
     */
    public ShooterPlaneBullet(Context context, int pX, int pY) {
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 50, 50, false));
        setWidthHeight();
        setX(pX - getWidth() / 2);
        setY(pY);
        setVelocity(80);

    }

    /**
     * Instantiates a new Shooter bullet 1.
     *
     * @param in the in
     */
    protected ShooterPlaneBullet(Parcel in) {
        super(in);
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterPlaneBullet> CREATOR = new Creator<ShooterPlaneBullet>() {
        @Override
        public ShooterPlaneBullet createFromParcel(Parcel in) {
            return new ShooterPlaneBullet(in);
        }

        @Override
        public ShooterPlaneBullet[] newArray(int size) {
            return new ShooterPlaneBullet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    @Override
    public void setUpBitmap(Context context) {
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psbullet));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 50, 50, false));
    }
}

