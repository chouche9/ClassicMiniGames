package com.example.myapplication.hangman;

/** Interface that contains all methods that the view of hangman must implement. */
interface HangmanGameView {

  /** Shows an empty error in the display. */
  void showEmptyError();

  /**
   * Shows a letter used error in the display.
   *
   * @param letter the character that was already guessed.
   */
  void showLetterUsedError(char letter);

  /** Shows the hangman image in the display. */
  void showImage();

  /**
   * Shows the masked word in the display.
   *
   * @param word the masked word.
   */
  void showTxtMaskedWord(String word);

  /** Clears the letters guessed in the display. */
  void clearEdtLetterGuess();

  /**
   * Shows the letters guessed so far in the display.
   *
   * @param lettersGuessed the letters guessed so far.
   */
  void showLettersGuessed(String lettersGuessed);

  /**
   * Shows the current score in the display.
   *
   * @param score the current score.
   */
  void showTxtScore(int score);

  /**
   * Sets the picture index.
   *
   * @param index the picture index.
   */
  void setPictureIndex(int index);

  /**
   * Event that happens after the current game has ended.
   *
   * @param hm the game status of the hangman game.
   */
  void gameEnded(HangmanGameStatus hm);

  /** Show that the guessed word was incorrect on the display. */
  void showGuessWordFailed();

  /**
   * Shows the current stage number in the display.
   *
   * @param stageNum the current stage number.
   */
  void showTxtStageNum(int stageNum);
}
