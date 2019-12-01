package com.example.myapplication.flappyfish.gameobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.flappyfish.flappygamestatus.FlappyGameStatusFacade;

/**
 * A class representation of objects inside the flappy fish game.
 */
public abstract class FlappyGameObjects implements Parcelable {

    /**
     * X coordinate of this game object.
     */
    private int x;

    /**
     * Y coordinate of this game object.
     */
    private int y;

    /**
     * Velocity of this game object. Direction of the object is indicated
     * with positive velocity facing right and negative velocity facing left.
     */
    private int velocity;

    /**
     * The height of the bitmap representation of this game object.
     */
    private int height;

    /**
     * The width of the bitmap representation of this game object.
     */
    private int width;

    /**
     * Constructs a new game object at the specified x and y coordinate with
     * the given velocity.
     * @param x the x coordinate of the new game object.
     * @param y the y coordinate of the new game object.
     * @param vel the velocity of the new game object.
     */
    FlappyGameObjects(int x, int y, int vel) {
        this.x = x;
        this.y = y;
        this.velocity = vel;
    }

    /**
     * Build the game object from parcel
     * @param in the parcel that stores the previously saved game object.
     */
    FlappyGameObjects(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        velocity = in.readInt();
        width = in.readInt();
        height = in.readInt();
    }

    /**
     * Return the x coordinate of the game object.
     * @return the x coordinate of the game object.
     */
    public int getX() {
        return x;
    }

    /**
     * Return the y coordinate of the game object.
     * @return the y coordinate of the game obejct.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the x coordinate of the game object to x.
     * @param x the new x coordinate of the game object.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate of the game object to y.
     * @param y the new x coordinate of the game object.
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * Return the velocity of the game object.
     * @return the y coordinate of the game obejct.
     */
    int getVelocity() {
        return velocity;
    }

    /**
     * Set the velocity of the game object to velocity.
     * @param velocity the new velocity of the game object.
     */
    void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * Return the height of the bitmap representation of this game object.
     * @return the height of the bitmap representation of this game object.
     */
    int getHeight() {
        return height;
    }

    /**
     * Return the width of the bitmap representation of this game object.
     * @return the width of the bitmap representation of this game object.
     */
    int getWidth() {
        return width;
    }

    /**
     * Set the height of this game object to height.
     * @param height the height of the bitmap representation of this game object.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set the width of this game object to height.
     * @param width the height of the bitmap representation of this game object.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Set the velocity of this game object to the default value.
     */
    public abstract void setGameDefault();

    /**
     * Increase the velocity of this game object by the defined increment.
     */
    public abstract void increaseGameStage();

    /**
     * Move the game object according to its current velocity.
     */
    public abstract void move();

    /**
     * Update the game object according to the specified game status
     * and ensure the game object does not move out of the screen
     * using canvasWidth, minY and maxY.
     */
    public abstract boolean update(FlappyGameStatusFacade gameStatus, int canvasWidth, int minY, int maxY);

    abstract void validCheck(int canvasWidth, int minY, int maxY);

    /**
     * Default method from the super class.
     *
     * @return 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Default method from the super class.
     *
     * @param parcel parcel to write the attributes of this GameStatus.
     * @param i flags.
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(x);
        parcel.writeInt(y);
        parcel.writeInt(velocity);
        parcel.writeInt(width);
        parcel.writeInt(height);
    }

}
