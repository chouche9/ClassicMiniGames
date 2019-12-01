package com.example.myapplication.spaceshooter.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;

/**
 * The type Shooter game object.
 */
public abstract class ShooterGameObject extends ShooterItem {
    /**
     * bitmap of shooterGameObject
     */
    private Bitmap object;
    /**
     * moving velocity of this project
     */
    private int velocity;

    /**
     * Instantiates a new Shooter game object.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    ShooterGameObject(int x, int y) {
        super(x, y);
    }

    /**
     * Instantiates a new Shooter game object.
     */
    ShooterGameObject() {
    }

    /**
     * Instantiates a new Shooter game object.
     *
     * @param in the in parcel
     */
    ShooterGameObject(Parcel in) {
        super(in);
        velocity = in.readInt();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(object, getX(), getY(), null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(velocity);
    }

    /**
     * Sets up bitmap.
     *
     * @param context the context
     */
    public abstract void setUpBitmap(Context context);

    /**
     * Clean bitmap.
     */
    void cleanBitmap() {
        object = null;
    }

    ;

    @Override
    void setWidthHeight() {
        setWidth(object.getWidth());
        setHeight(object.getHeight());
    }

    /**
     * Gets the bitmap of shooter game object.
     *
     * @return the object
     */
    public Bitmap getObject() {
        return object;
    }

    /**
     * Sets object's bitmap
     *
     * @param object the object
     */
    public void setObject(Bitmap object) {
        this.object = object;
    }

    /**
     * Gets velocity of the object
     *
     * @return the velocity
     */
    public int getVelocity() {
        return velocity;
    }

    /**
     * Sets velocity of the object
     *
     * @param velocity the velocity
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}

