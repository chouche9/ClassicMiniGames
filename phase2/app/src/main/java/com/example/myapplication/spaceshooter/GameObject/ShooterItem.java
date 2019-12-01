package com.example.myapplication.spaceshooter.GameObject;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Shooter item.
 */
public abstract class ShooterItem implements Parcelable {
    /**
     * x coordinate of game item
     */
    private int x;
    /**
     * y coordinate of game item
     */
    private int y;
    /**
     * width and height of game item
     */
    private int width, height;

    /**
     * Gets item's width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets item's width.
     *
     * @param weight the weight
     */
    public void setWidth(int weight) {
        width = weight;
    }

    /**
     * Gets item's height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets item's height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Instantiates a new Shooter item.
     *
     * @param x the x coordinate of item
     * @param y the y coordinate of item
     */
    ShooterItem(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Shooter item.
     */
    ShooterItem(){

    }

    /**
     * Instantiates a new Shooter item.
     *
     * @param in the parcel
     */
    protected ShooterItem(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        width = in.readInt();
        height = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Gets x coordinate of item
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x coordinate of item
     *
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y coordinate of item
     *
     * @return the y coordinate of item
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y coordinate of item
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * write item into parcel
     * @param dest the parcel
     * @param flags the flag integer
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    /**
     *  draw item on canvas
     *
     * @param canvas the canvas
     */
    public abstract void onDraw(Canvas canvas);

    /**
     * Sets width and height of shooter item
     */
    abstract void setWidthHeight();
}