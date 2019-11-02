package com.example.myapplication.GuessNum;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;
/**
 * the class that store a particular player's Guessing Game info .
 */
public class GuessGameStat extends GameStatus implements Parcelable {
    /**
     * color of the Text for the GuessGame player choose
     */
    private String color;
    /**
     * the current Tries of this player
     */
    private int currentTries = 0;
    /**
     * the best tries of this player
     */
    private int bestTries = -1;
    /**
     * binary indicate whether player finish the last game or not. 0 represent finish, 1 represent
     * not finish
     */
    private int played = 0;
    /**
     * the target number for this player on the last game if he did not finish
     */
    private int lastNum;
    /**
     * type of the class in string
     */
    private String type;

    /**
     * construct GuessGameStat instance for user: name
     * @param name name of the user
     */
    GuessGameStat(String name){
        super(name);
        this.type = "GuessGameStat";
    }

    /**
     * build GuessGameStat from Parcel
     * @param in the Parcel that store GuessGameStat
     */
    private GuessGameStat(Parcel in) {
        super(in);
        color = in.readString();
        currentTries = in.readInt();
        bestTries = in.readInt();
        played = in.readInt();
        lastNum = in.readInt();
        type = in.readString();
    }

    /**
     * the the number of target Number when exiting the game
     * @param lastNum the target Number that in the game that just pause
     */
    void setLastNum(int lastNum) {
        this.lastNum = lastNum;
    }

    /**
     * get the target Number in the paused game
     * @return the last target number
     */
    int getLastNum() {
        return lastNum;
    }

    /**
     * create GuessGameStat by the super Creator object
     */
    public static final Creator<GuessGameStat> CREATOR = new Creator<GuessGameStat>() {
        @Override
        public GuessGameStat createFromParcel(Parcel in) {
            return new GuessGameStat(in);
        }

        @Override
        public GuessGameStat[] newArray(int size) {
            return new GuessGameStat[size];
        }
    };

    /**
     * update currentTries by increasing 1
     */
    void setCurrentTries() {
        this.currentTries ++;
    }

    /**
     * reset the current tries to zero
     */
    private void resetCurrentTries() {
        this.currentTries  = 0;
    }

    /**
     * set attribute color to colr
     * @param color the color want to set
     */

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * get the color player choose
     * @return the color in String
     */

    public String getColor() {
        return color;
    }

    /**
     * get the best tries for this player
     * @return the best tries number
     */
     int getBestTries() {
        return bestTries;
    }

    /**
     *
     * @return whether this player has a pause game or not
     */
     boolean played(){
        return played == 1;
    }

    /**
     * default method for implementing parcelable
     * @return the special content in parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * write attribute of GuessGameStat into parcel
     * @param parcel parcel to write the attributes of this GameStatus.
     * @param i flags.
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(color);
        parcel.writeInt(currentTries);
        parcel.writeInt(bestTries);
        parcel.writeInt(played);
        parcel.writeInt(lastNum);
        parcel.writeString(type);
    }

    /**
     * update GuessGameStat when finish guessing the number
     * @param finalTries: the total tries to get the right answer
     */
     void finishUpdate(int finalTries){
        if (bestTries == -1 || bestTries > finalTries){
            bestTries = finalTries;
        }
        currentTries = 0;
        lastNum = 0;
        setPlayed(false);
    }

    /**
     * update GuessGameStat when player start a new round
     */
     void startUpdate(){
        this.setPlayed(true);
        this.resetCurrentTries();
        this.setLastNum(0);
    }

    /**
     *
     * @return the current tries;
     */
     int getCurrentTries(){
        return currentTries;
    }

    /**
     * set whether it has a pause game or not
     * @param played the indicator of having a paused game
     */

    private void setPlayed(boolean played) {
        if(played){
            this.played = 1 ;}
        else{
            this.played = 0;
        }
    }
}