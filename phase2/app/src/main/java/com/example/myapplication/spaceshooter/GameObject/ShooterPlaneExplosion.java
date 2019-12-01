package com.example.myapplication.spaceshooter.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.R;

/**
 * The type Shooter plane explosion.
 */
public class ShooterPlaneExplosion extends ShooterExplosion implements Parcelable {

    /**
     * Instantiates a new Shooter plane explosion.
     *
     * @param context    the context to get planeExplosion picture
     * @param explosionX the explosion x coordinate
     * @param explosionY the explosion y coordinate
     */
    public ShooterPlaneExplosion(Context context, int explosionX, int explosionY){
        super(explosionX, explosionY);
        explosions = new Bitmap[5];
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion1);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion2);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion3);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion4);
        explosions[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion5);
        setWidthHeight();

    }

    /**
     * Instantiates a new Shooter plane explosion from parcel
     *
     * @param in the parcel
     */
    private ShooterPlaneExplosion(Parcel in) {
        super(in);
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<ShooterPlaneExplosion> CREATOR = new Creator<ShooterPlaneExplosion>() {
        @Override
        public ShooterPlaneExplosion createFromParcel(Parcel in) {
            return new ShooterPlaneExplosion(in);
        }

        @Override
        public ShooterPlaneExplosion[] newArray(int size) {
            return new ShooterPlaneExplosion[size];
        }
    };

    /**
     * Get explosion bitmap.
     *
     * @param frame the frame
     * @return the bitmap
     */
    Bitmap getExplosion(int frame){
        return explosions[frame];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * write planeExplosion to Parcel
     * @param dest the parcel need to write
     * @param flags the flag integer
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    /**
     *
     * @param context the context to get plane explosion picture
     */
    @Override
    public void setUpBitmap(Context context) {
        explosions = new Bitmap[5];
        explosions[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion1);
        explosions[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion2);
        explosions[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion3);
        explosions[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion4);
        explosions[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.psplaneexplosion5);
    }


}
