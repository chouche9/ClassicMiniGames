package com.example.myapplication.bonuslevel;

/** class Bonus Level Presenter */
class BonusLevelPresenter implements BonusLevelInteractor.OnValidateNumberListener {

  /** attribute that saves the instance of a bonusLevelView */
  private BonusLevelView bonusLevelView;

  /** attribute that saves te instance of bonuslevelinteractor */
  private BonusLevelInteractor bonusLevelInteractor;

  /**
   * Constructor method for BonusLevelPresenter
   *
   * @param bonusLevelView: instance of BonusLevelDialog
   */
  BonusLevelPresenter(BonusLevelView bonusLevelView) {
    this.bonusLevelView = bonusLevelView;
    this.bonusLevelInteractor = new BonusLevelInteractor();
  }

  /**
   * Method to validate the guessed number
   *
   * @param guessedNumber: guessed number that has been inputted
   */
  void validateGuessNumber(int guessedNumber) {
    if (bonusLevelInteractor != null) {
      bonusLevelInteractor.validateNumberInteractor(guessedNumber, this);
    }
  }

  /** Present an error when number is too high */
  @Override
  public void onNumberToHigh() {
    bonusLevelView.showNumberToHighError();
    bonusLevelView.updateTries(bonusLevelInteractor.getTriesLeft());
  }

  /** Present an error when number is too low */
  @Override
  public void onNumberToLow() {
    bonusLevelView.showNumberToLowError();
    bonusLevelView.updateTries(bonusLevelInteractor.getTriesLeft());
  }

  /**
   * Present something when the game ended
   *
   * @param isWon boolean indicating if the game was won or not
   * @param bonusScore the bonus score earned during bonus game
   */
  @Override
  public void onGameEnd(boolean isWon, int bonusScore) {
    bonusLevelView.gameEnd(isWon, bonusScore);
  }
}
