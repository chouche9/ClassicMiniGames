package com.example.myapplication.spaceshooter.GameObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;

import com.example.myapplication.R;
import com.example.myapplication.spaceshooter.ShooterGameStatus.ShooterGameStatusFacade;

/**
 * The type Shooter point buff.
 */
public class ShooterPointBuff extends ShooterSpecialItem {
    /**
     * Instantiates a new Shooter point buff.
     * that increase the point of shooter game status
     * @param context the context to get puff picture
     */
    public ShooterPointBuff(Context context){
        super();
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psgold));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }

    /**
     * Instantiates a new Shooter point buff.
     *
     * @param in the in
     */
    private ShooterPointBuff(Parcel in){
        super(in);
    }
    @Override
    public void getBuff(ShooterGameStatusFacade shooterGameStatus) {
        shooterGameStatus.addPoint(50);
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterPointBuff> CREATOR = new Creator<ShooterPointBuff>() {
        @Override
        public ShooterPointBuff createFromParcel(Parcel in) {
            return new ShooterPointBuff(in);
        }

        @Override
        public ShooterPointBuff[] newArray(int size) {
            return new ShooterPointBuff[size];
        }
    };

    /**
     * set up bitmap for shooter point buff
     * @param context the context
     */
    @Override
    public void setUpBitmap(Context context) {
        setObject(BitmapFactory.decodeResource(context.getResources(), R.drawable.psgold));
        setObject(Bitmap.createScaledBitmap(
                getObject(), 150, 150, false));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

