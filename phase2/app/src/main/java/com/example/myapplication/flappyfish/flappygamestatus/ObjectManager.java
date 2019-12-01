package com.example.myapplication.flappyfish.flappygamestatus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.flappyfish.gameobjects.FlappyGameBonus;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameFish;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameObjects;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameShark;
import com.example.myapplication.flappyfish.gameobjects.FlappyGameShrimp;

/**
 * The manager object responsible for tracking all game objects.
 */
public class ObjectManager implements Parcelable {

    /** The fish object that is displayed on the screen. */
    private FlappyGameFish fish = new FlappyGameFish();

    /** The shrimp object that is displayed on the screen. */
    private FlappyGameShrimp shrimp = new FlappyGameShrimp();

    /** The shark object that is displayed on the screen. */
    private FlappyGameShark shark = new FlappyGameShark();

    /** The bonus object that is displayed on the screen. */
    private FlappyGameBonus bonus = new FlappyGameBonus();

    /**
     * Constructs a new object manager.
     */
    public ObjectManager() {}

    /**
     * Build the object manager from parcel.
     * @param in the parcel that stores the object manager.
     */
    private ObjectManager(Parcel in) {
        fish = in.readParcelable(FlappyGameObjects.class.getClassLoader());
        shrimp = in.readParcelable(FlappyGameObjects.class.getClassLoader());
        shark = in.readParcelable(FlappyGameObjects.class.getClassLoader());
        bonus = in.readParcelable(FlappyGameObjects.class.getClassLoader());
    }

    /**
     * Set all the game objects tracked by this object manager to their default state.
     */
    void setObjectDefault() {
        fish.setGameDefault();
        shrimp.setGameDefault();
        shark.setGameDefault();
        bonus.setGameDefault();
    }

    /**
     * Update all the game objects tracked by this object manager when the stage increases.
     */
    void increaseObjectStage() {
        fish.increaseGameStage();
        shrimp.increaseGameStage();
        shark.increaseGameStage();
        bonus.increaseGameStage();
    }

    /**
     * Reset the fish object tracked by this object manager.
     */
    void restartFish() {
        fish = new FlappyGameFish();
    }

    /**
     * Return the fish object tracked by this object manager.
     * @return the fish object tracked by this object manager.
     */
    FlappyGameFish getFish() {
        return fish;
    }

    /**
     * Return the shrimp object tracked by this object manager.
     * @return the shrimp object tracked by this object manager.
     */
    FlappyGameShrimp getShrimp() {
        return shrimp;
    }

    /**
     * Return the shark object tracked by this object manager.
     * @return the shark object tracked by this object manager.
     */
    FlappyGameShark getShark() {
        return shark;
    }

    /**
     * Return the fish object tracked by this object manager.
     * @return the fish object tracked by this object manager.
     */
    FlappyGameBonus getBonus() {
        return bonus;
    }

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
        parcel.writeParcelable(fish, i);
        parcel.writeParcelable(shrimp, i);
        parcel.writeParcelable(shark, i);
        parcel.writeParcelable(bonus, i);
    }

    /** Create FlappyGameStatatus by the super Creator object. */
    public static final Creator<ObjectManager> CREATOR =
            new Creator<ObjectManager>() {
                @Override
                public ObjectManager createFromParcel(Parcel in) {
                    return new ObjectManager(in);
                }

                @Override
                public ObjectManager[] newArray(int size) {
                    return new ObjectManager[size];
                }
            };

}
