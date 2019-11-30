package com.example.myapplication.spaceshooter.ShooterGameStatus;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.domain.GameStatus;
import com.example.myapplication.spaceshooter.GameObject.ShooterPlane;



public class ShooterGameStatusFacade extends GameStatus implements Parcelable {

    public void setShooterCrossLevelManager(ShooterCrossLevelManager shooterCrossLevelManager) {
        this.shooterCrossLevelManager = shooterCrossLevelManager;
    }

    public void setShooterGameLevelManager(ShooterGameLevelManager shooterGameLevelManager) {
        this.shooterGameLevelManager = shooterGameLevelManager;
    }

    private ShooterCrossLevelManager shooterCrossLevelManager;
    private ShooterGameLevelManager shooterGameLevelManager;

    public ShooterGameStatusFacade(String name){
        super(name, GameEnum.SPACESHOOTER);

    }

    protected ShooterGameStatusFacade(Parcel in) {
        super(in);
        setGameType(GameEnum.valueOf(in.readString()));
        shooterGameLevelManager = in.readParcelable(ShooterGameLevelManager.class.getClassLoader());
        shooterCrossLevelManager = in.readParcelable(ShooterCrossLevelManager.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(getGameType().toString());
        dest.writeParcelable(shooterGameLevelManager, flags);
        dest.writeParcelable(shooterCrossLevelManager, flags);

    }
    public ShooterPlane getPlane(){
        return shooterGameLevelManager.getPlane();
    }

    public void addPoint(int point){
        shooterCrossLevelManager.setPoint(shooterCrossLevelManager.getPoint() + point);
    }

    public void resetGameStatus(){
        shooterGameLevelManager.resetLevel();
        shooterCrossLevelManager.resetLevel();
    }
    public void eraseGameStatus(){
        shooterCrossLevelManager.resetGame();
        shooterGameLevelManager.resetGame();
    }
    public void setGameSuccess(boolean success){
        shooterCrossLevelManager.setGameSuccess(success);
    }

    public void setPlane(int planeNum, Context context){
            shooterGameLevelManager.setPlane(planeNum, context);
    }

    public static final Creator<ShooterGameStatusFacade> CREATOR =
            new Creator<ShooterGameStatusFacade>() {
                @Override
                public ShooterGameStatusFacade createFromParcel(Parcel in) {
                    return new ShooterGameStatusFacade(in);
                }

                @Override
                public ShooterGameStatusFacade[] newArray(int size) {
                    return new ShooterGameStatusFacade[size];
                }
            };
    public ShooterCrossLevelManager getShooterCrossLevelManager() {
        return shooterCrossLevelManager;
    }

    public ShooterGameLevelManager getShooterGameLevelManager() {
        return shooterGameLevelManager;
    }
}

