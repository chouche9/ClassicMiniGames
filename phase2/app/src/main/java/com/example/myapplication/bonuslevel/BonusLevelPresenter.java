package com.example.myapplication.bonuslevel;

class BonusLevelPresenter implements BonusLevelInteractor.OnValidateNumberListener{

    private BonusLevelDialog bonusLevelDialog;

    private BonusLevelInteractor bonusLevelInteractor;

    BonusLevelPresenter(BonusLevelDialog bonusLevelDialog) {
        this.bonusLevelDialog = bonusLevelDialog;
        this.bonusLevelInteractor = new BonusLevelInteractor();
    }


    void validateGuessNumber(int guessedNumber) {
        if (bonusLevelInteractor != null) {
            bonusLevelInteractor.validateNumberInteractor(guessedNumber, this);
        }
    }

    @Override
    public void onNumberToHigh() {
        bonusLevelDialog.showNumberToHighError();
        bonusLevelDialog.updateTries(bonusLevelInteractor.getTriesLeft());
    }

    @Override
    public void onNumberToLow() {
        bonusLevelDialog.showNumberToLowError();
        bonusLevelDialog.updateTries(bonusLevelInteractor.getTriesLeft());
    }

    @Override
    public void onGameEnd(boolean isWon, int bonusScore) {
        bonusLevelDialog.GameEnd(isWon, bonusScore);
    }
}
