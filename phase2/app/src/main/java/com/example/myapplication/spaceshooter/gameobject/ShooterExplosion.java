package com.example.myapplication.spaceshooter.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Shooter explosion.
 */
public abstract class ShooterExplosion extends ShooterItem implements Parcelable {
    /**
     * The Explosions.
     */
    Bitmap[] explosions;
    /**
     * the frameNumber of explosion
     */
    private int explosionFrame = 0;

    /**
     * Instantiates a new Shooter explosion.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    ShooterExplosion(int x, int y) {
        super(x, y);
    }

    /**
     * Instantiates a new Shooter explosion.
     *
     * @param in the in parcel
     */
    ShooterExplosion(Parcel in) {
        super(in);
        explosionFrame = in.readInt();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(explosions[explosionFrame], getX(), getY(), null);
        explosionFrame++;
    }

    /**
     * Check if the frame number is valid
     *
     * @return the boolean
     */
    public boolean checkFrameValid() {
        return explosionFrame < explosions.length;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(explosionFrame);
    }

    /**
     * Sets up bitmap for shooter explosion
     *
     * @param context the context
     */
    public abstract void setUpBitmap(Context context);

    /**
     * set width and height of explosion
     */
    @Override
    void setWidthHeight() {
        setWidth(explosions[0].getWidth());
        setHeight(explosions[1].getHeight());
    }

    ;
}