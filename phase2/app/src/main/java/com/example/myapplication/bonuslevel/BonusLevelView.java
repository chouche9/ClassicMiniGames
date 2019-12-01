package com.example.myapplication.bonuslevel;

/**
 * Interface for Bonus Level View
 */
interface BonusLevelView {

    /**
     * Abstract method to update the number of Tries done so far
     * @param numTries: number of tries
     */
    void updateTries(int numTries);

    /**
     * Abstract Method to show error when empty
     */
    void showEmptyError();

    /**
     * Abstract Method to show error when the guess is out of bounds
     */
    void showOutOfBoundsError();

    /**
     * Abstract Method to show error message if number is too high
     */
    void showNumberToHighError();

    /**
     * Abstract Method to show error when number is too low
     */
    void showNumberToLowError();

    /**
     * Abstract Method to do something when game has ended
     *
     * @param isWon boolean indicating if the game was won or not
     * @param bonusScore the bonus score earned during bonus game
     */
    void gameEnd(boolean isWon, int bonusScore);

}
