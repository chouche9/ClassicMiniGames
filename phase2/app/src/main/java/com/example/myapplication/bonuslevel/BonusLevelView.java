package com.example.myapplication.bonuslevel;

interface BonusLevelView {

    void updateTries(int numTries);

    void showEmptyError();

    void showOutOfBoundsError();

    void showNumberToHighError();

    void showNumberToLowError();

    void GameEnd(boolean isWon, int bonusScore);

}
