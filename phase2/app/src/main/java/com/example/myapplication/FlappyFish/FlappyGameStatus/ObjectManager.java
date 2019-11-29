package com.example.myapplication.FlappyFish.FlappyGameStatus;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.FlappyFish.GameObjects.FlappyGameBonus;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameFish;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameObjects;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameShark;
import com.example.myapplication.FlappyFish.GameObjects.FlappyGameShrimp;

public class ObjectManager implements Parcelable {

    /** The fish object that is displayed on the screen. */
    private FlappyGameFish fish = new FlappyGameFish();

    /** The shrimp object that is displayed on the screen. */
    private FlappyGameShrimp shrimp = new FlappyGameShrimp();

    /** The shark object that is displayed on the screen. */
    private FlappyGameShark shark = new FlappyGameShark();

    /** The bonus object that is displayed on the screen. */
    private FlappyGameBonus bonus = new FlappyGameBonus();

    public ObjectManager() {}

    private ObjectManager(Parcel in) {
        fish = in.readParcelable(FlappyGameObjects.class.getClassLoader());
        shrimp = in.readParcelable(FlappyGameObjects.class.getClassLoader());
        shark = in.readParcelable(FlappyGameObjects.class.getClassLoader());
        bonus = in.readParcelable(FlappyGameObjects.class.getClassLoader());
    }

    void setObjectDefault() {
        fish.setGameDefault();
        shrimp.setGameDefault();
        shark.setGameDefault();
        bonus.setGameDefault();
    }

    void increaseObjectStage() {
        fish.increaseGameStage();
        shrimp.increaseGameStage();
        shark.increaseGameStage();
        bonus.increaseGameStage();
    }

    void restartFish() {
        fish = new FlappyGameFish();
    }

    FlappyGameFish getFish() {
        return fish;
    }

    FlappyGameShrimp getShrimp() {
        return shrimp;
    }

    FlappyGameShark getShark() {
        return shark;
    }

    FlappyGameBonus getBonus() {
        return bonus;
    }

    void setFishJumpSpeed() {
        fish.setFishJumpSpeed();
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
