package com.example.myapplication.bonuslevel;

import java.util.Random;

/** class Bonus Level Interactor */
class BonusLevelInteractor {

  /** interface onValidateNumberListener */
  interface OnValidateNumberListener {

    /** method when number is too high */
    void onNumberToHigh();

    /** method when number is too low */
    void onNumberToLow();

    /**
     * method when game ended
     *
     * @param isWon boolean indicating if the bonus game is won or not.
     * @param bonusScore the bonus score earned.
     */
    void onGameEnd(boolean isWon, int bonusScore);
  }

  /** attribute that saves the target number to be guessed */
  private int targetNum;

  /** attribute that saves the number of tries left */
  private int triesLeft = 5;

  /**
   * method that return the number of tries left
   *
   * @return number of tries left
   */
  int getTriesLeft() {
    return triesLeft;
  }

  /** Constructor method of BonusLevelInteractor */
  BonusLevelInteractor() {
    createRanNum();
  }

  /** Method that creates a random number between 1 and 20 that needs to be */
  private void createRanNum() {
    final int min = 1;
    final int max = 20;
    targetNum = new Random().nextInt((max - min) + 1) + min;
  }

  /**
   * Method to validate whether the number is correct or not
   *
   * @param guessedNumber: the guessed number
   * @param listener: The bonus level presenter
   */
  void validateNumberInteractor(int guessedNumber, OnValidateNumberListener listener) {
    if (guessedNumber == targetNum) {
      listener.onGameEnd(true, 100);
    } else if (guessedNumber > targetNum) {
      triesLeft--;
      listener.onNumberToHigh();
    } else {
      triesLeft--;
      listener.onNumberToLow();
    }

    if (triesLeft == 0) {
      listener.onGameEnd(false, 0);
    }
  }
}
