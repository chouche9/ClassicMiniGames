package com.example.myapplication.hangman;

/** The presenter of this hangman game. */
class HangmanGamePresenter implements HangmanGameInteractor.OnValidateCharListener {

  /** The view/activity of the hangman game. */
  private HangmanGameView hangmanGameView;

  /** The interactor of the hangman game. */
  private HangmanGameInteractor hangmanGameInteractor;

  /** The word generator of the hangman game. */
  private HangmanWordGenerator hangmanWordGenerator;

  /**
   * Construct this HangmanGamePresenter.
   *
   * @param view the activity that called this presenter.
   * @param hangmanGameInteractor the interactor for the hangman game.
   */
  HangmanGamePresenter(HangmanGameActivity view, HangmanGameInteractor hangmanGameInteractor) {
    this.hangmanGameView = view;
    this.hangmanGameInteractor = hangmanGameInteractor;
    this.hangmanWordGenerator = new HangmanWordGenerator(view);
  }

  /**
   * Checks whether if the guessed letter is in the word or not.
   *
   * @param letter the letter that was guessed by the user.
   */
  void validateLetter(char letter) {
    hangmanGameInteractor.validateLetter(letter, this);
  }

  /**
   * Checks whether if the guessed word is equal to the word.
   *
   * @param guessedWord the word guessed by the user.
   */
  void validateWord(String guessedWord) {
    hangmanGameInteractor.validateWord(guessedWord, this);
  }

  /**
   * Get the hangman game status.
   *
   * @return the hangman game status.
   */
  HangmanGameStatus getHangmanGameStat() {
    return hangmanGameInteractor.getHangmanGameStat();
  }

  /**
   * Return if the game has been played or not.
   *
   * @return true if the game has been played, false otherwise.
   */
  boolean getPlayed() {
    return getHangmanGameStat().isPlayed();
  }

  /** Generate a new word from the hangmanWordGenerator. */
  void getNewWord() {
    String chosenWord = hangmanWordGenerator.getChosenWord();
    hangmanGameInteractor.generateWord(chosenWord);
  }

  /**
   * Check whether if the game has ended.
   *
   * @return true if the game has ended, false otherwise.
   */
  boolean onCheckIfGameEnded() {
    return hangmanGameInteractor.gameEnded();
  }

  /** Activate game ended activity if the game has ended. */
  void onOpenGameEndedActivity() {
    onGameEnd(getHangmanGameStat());
  }

  /** Resume the game by setting appropriate views on the activity. */
  void onResuming() {
    hangmanGameView.showTxtStageNum(getHangmanGameStat().getStageNum());
    hangmanGameView.showTxtMaskedWord(getHangmanGameStat().getDisplayedMaskedWord().toString());
    hangmanGameView.showTxtScore(getHangmanGameStat().getCurrentScore());
    hangmanGameView.showLettersGuessed(getHangmanGameStat().getLettersGuessed().toString());
    hangmanGameView.setPictureIndex(getHangmanGameStat().getFalseGuess());
    hangmanGameView.showImage();
  }

  /** Display an empty error on the display. */
  @Override
  public void onEmptyError() {
    if (hangmanGameView != null) {
      hangmanGameView.showEmptyError();
    }
  }

  /**
   * Display a letter used error on the display.
   *
   * @param letter the letter that was already guessed.
   */
  @Override
  public void onLetterUsedError(char letter) {
    if (hangmanGameView != null) {
      hangmanGameView.showLetterUsedError(letter);
      hangmanGameView.clearEdtLetterGuess();
    }
  }

  /** Display all necessary views onto the display. */
  @Override
  public void onDisplayViews() {
    if (hangmanGameView != null) {
      hangmanGameView.showTxtStageNum(getHangmanGameStat().getStageNum());
      hangmanGameView.setPictureIndex(getHangmanGameStat().getFalseGuess());
      hangmanGameView.showTxtMaskedWord(
          getHangmanGameStat().getDisplayedMaskedWord().toString());
      hangmanGameView.showLettersGuessed(getHangmanGameStat().getLettersGuessed().toString());
      hangmanGameView.showTxtScore(getHangmanGameStat().getCurrentScore());
      hangmanGameView.showImage();
      hangmanGameView.clearEdtLetterGuess();
    }
  }

  /**
   * Display a game ended activity.
   *
   * @param hm the hangman game status.
   */
  @Override
  public void onGameEnd(HangmanGameStatus hm) {
    if (hangmanGameView != null) {
      hangmanGameView.gameEnded(hm);
    }
  }

  /** Display that the word guessed was incorrect. */
  @Override
  public void onGuessWordFailed() {
    if (hangmanGameView != null) {
      hangmanGameView.showGuessWordFailed();
      onDisplayViews();
    }
  }
}
