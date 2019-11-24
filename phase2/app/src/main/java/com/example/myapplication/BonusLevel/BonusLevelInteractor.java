package com.example.myapplication.BonusLevel;

import java.util.Random;

class BonusLevelInteractor {

    interface OnValidateNumberListener {

        void onNumberToHigh();

        void onNumberToLow();

        void onGameEnd(boolean isWon, int bonusScore);
    }

    private int BONUS_SCORE = 100;

    private int FAIL_SCORE = 100;

    private int targetNum;

    private int triesLeft = 5;

    public int getTriesLeft() {
        return triesLeft;
    }

    BonusLevelInteractor() {
        createRanNum();
    }

    private void createRanNum() {
        final int min = 1;
        final int max = 20;
        targetNum = new Random().nextInt((max - min) + 1) + min;
    }


    public void validateNumberInteractor(int guessedNumber, BonusLevelPresenter listener) {
        if(guessedNumber == targetNum){
            listener.onGameEnd(true, BONUS_SCORE);
        } else if (guessedNumber > targetNum) {
            triesLeft--;
            listener.onNumberToHigh();
        } else {
            triesLeft--;
            listener.onNumberToLow();
        }

        if (triesLeft == 0) {
            listener.onGameEnd(false, FAIL_SCORE);
        }

    }

}
