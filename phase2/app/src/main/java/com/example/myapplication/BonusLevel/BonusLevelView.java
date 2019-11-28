package com.example.myapplication.BonusLevel;

interface BonusLevelView {

    void updateTries(int numTries);

    void showEmptyError();

    void showOutOfBoundsError();

    void showNumberToHighError();

    void showNumberToLowError();

    void GameEnd(boolean isWon, int bonusScore);

}
