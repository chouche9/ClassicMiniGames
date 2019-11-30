package com.example.myapplication.bonuslevel;

/**
 * class Bonus Level Presenter
 */
class BonusLevelPresenter implements BonusLevelInteractor.OnValidateNumberListener{

    /**
     * attribute that saves the instance of bonusleveldialog
     */
    private BonusLevelDialog bonusLevelDialog;

    /**
     * attribute that saves te instance of bonuslevelinteractor
     */
    private BonusLevelInteractor bonusLevelInteractor;

    /**
     * Constructor method for BonusLevelPresenter
     * @param bonusLevelDialog: instance of BonusLevelDialog
     */
    BonusLevelPresenter(BonusLevelDialog bonusLevelDialog) {
        this.bonusLevelDialog = bonusLevelDialog;
        this.bonusLevelInteractor = new BonusLevelInteractor();
    }

    /**
     * Method to validate the guessed number
     * @param guessedNumber: guessed number that has been inputted
     */
    void validateGuessNumber(int guessedNumber) {
        if (bonusLevelInteractor != null) {
            bonusLevelInteractor.validateNumberInteractor(guessedNumber, this);
        }
    }

    /**
     * Present an error when number is too high
     */
    @Override
    public void onNumberToHigh() {
        bonusLevelDialog.showNumberToHighError();
        bonusLevelDialog.updateTries(bonusLevelInteractor.getTriesLeft());
    }

    /**
     * Present an error when number is too low
     */
    @Override
    public void onNumberToLow() {
        bonusLevelDialog.showNumberToLowError();
        bonusLevelDialog.updateTries(bonusLevelInteractor.getTriesLeft());
    }

    /**
     * Present something when the game ended
     * @param isWon
     * @param bonusScore
     */
    @Override
    public void onGameEnd(boolean isWon, int bonusScore) {
        bonusLevelDialog.GameEnd(isWon, bonusScore);
    }
}
