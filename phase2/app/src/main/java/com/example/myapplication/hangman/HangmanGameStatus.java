package com.example.myapplication.hangman;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.domain.GameStatus;

/** GameStatus for hangman game - stores the current game state. */
public class HangmanGameStatus extends GameStatus implements Parcelable {

  /** A boolean that indicates whether the game has been played before or not */
  private boolean played = false;

  /** A string that represent the secret word for the hangman game */
  private String secretWord = "";

  /** A Character array of the secret word array */
  private char[] secretWordCharArray;

  /** A Character array of the masked word Char Array to be shown in the game activity */
  private char[] maskedWordCharArray;

  /** An int that store the total value of the game */
  private int currentScore = 120;

  /** Keeps track of number of guesses that failed. */
  private int falseGuess = 0;

  /** The chosen for this game */
  private String gender = "MALE";

  /** A StringBuilder that will store the letters that has been guessed by the user */
  private StringBuilder lettersGuessed = new StringBuilder();

  /** A StringBuilder that will store the word to be displayed on the game but are masked. */
  private StringBuilder displayedMaskedWord = new StringBuilder();

  /** The current stage number. */
  private int stageNum = 0;

  /** The score accumulated so far. */
  private int accumulatedScore = 0;

  /** Indicates whether if the bonus level is currently activated or not. */
  private boolean bonusLevelActivated = false;

  /**
   * A constructor to construct the HangmanGameStatus.
   *
   * @param name the name of the user
   */
  HangmanGameStatus(String name) {
    super(name, GameEnum.HANGMAN);
  }

  /**
   * Constructs this GameStatus using the values stored in parcel in.
   *
   * @param in the parcel that stores values of a GameStatus object.
   */
  private HangmanGameStatus(Parcel in) {
    super(in);
    setGameType(GameEnum.valueOf(in.readString()));
    played = in.readByte() != 0;
    secretWord = in.readString();
    secretWordCharArray = in.createCharArray();
    maskedWordCharArray = in.createCharArray();
    currentScore = in.readInt();
    falseGuess = in.readInt();
    gender = in.readString();
    lettersGuessed = new StringBuilder(in.readString());
    displayedMaskedWord = new StringBuilder(in.readString());
    stageNum = in.readInt();
    accumulatedScore = in.readInt();
    bonusLevelActivated = in.readByte() != 0;
  }

  /**
   * Write the attribute of this GameStatus to parcel.
   *
   * @param parcel parcel to write the attributes of this GameStatus.
   * @param i flags.
   */
  @Override
  public void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
    parcel.writeString(getGameType().toString());
    parcel.writeByte((byte) (played ? 1 : 0));
    parcel.writeString(secretWord);
    parcel.writeCharArray(secretWordCharArray);
    parcel.writeCharArray(maskedWordCharArray);
    parcel.writeInt(currentScore);
    parcel.writeInt(falseGuess);
    parcel.writeString(gender);
    parcel.writeString(lettersGuessed.toString());
    parcel.writeString(displayedMaskedWord.toString());
    parcel.writeInt(stageNum);
    parcel.writeInt(accumulatedScore);
    parcel.writeByte((byte) (bonusLevelActivated ? 1 : 0));
  }

  /** Binds the GameStatus object. */
  public static final Creator<HangmanGameStatus> CREATOR =
      new Creator<HangmanGameStatus>() {
        @Override
        public HangmanGameStatus createFromParcel(Parcel in) {
          return new HangmanGameStatus(in);
        }

        @Override
        public HangmanGameStatus[] newArray(int size) {
          return new HangmanGameStatus[size];
        }
      };

  /**
   * Default method from Parcelable interface.
   *
   * @return int 0
   */
  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Get whether if the hangman game is played or not.
   *
   * @return true if the hangman game has been played, false otherwise.
   */
  boolean isPlayed() {
    return played;
  }

  /**
   * Set whether if the hangman game is played or not.
   *
   * @param played indicates whether if the hangman game is played or not.
   */
  void setPlayed(boolean played) {
    this.played = played;
  }

  /**
   * Get the secret word of this hangman game.
   *
   * @return the secret word of this hangman game.
   */
  String getSecretWord() {
    return secretWord;
  }

  /**
   * Set the secret word of this hangman game.
   *
   * @param secretWord the secret word of this hangman game.
   */
  void setSecretWord(String secretWord) {
    this.secretWord = secretWord;
  }

  /**
   * Get the SecretWordCharArray of this hangman game.
   *
   * @return the SecretWordCharArray of this hangman game.
   */
  char[] getSecretWordCharArray() {
    return secretWordCharArray;
  }

  /**
   * Set the SecretWordCharArray of this hangman game.
   *
   * @param secretWordCharArray the SecretWordCharArray of this hangman game.
   */
  void setSecretWordCharArray(char[] secretWordCharArray) {
    this.secretWordCharArray = secretWordCharArray;
  }

  /**
   * Get the MaskedWordCharArray of this hangman game.
   *
   * @return the MaskedWordCharArray of this hangman game.
   */
  char[] getMaskedWordCharArray() {
    return maskedWordCharArray;
  }

  /**
   * Set the MaskedWordCharArray of this hangman game.
   *
   * @param maskedWordCharArray the MaskedWordCharArray of this hangman game.
   */
  void setMaskedWordCharArray(char[] maskedWordCharArray) {
    this.maskedWordCharArray = maskedWordCharArray;
  }

  /**
   * Get the current score of this hangman game.
   *
   * @return the current score of this hangman game.
   */
  int getCurrentScore() {
    return currentScore;
  }

  /**
   * Set the current score of this hangman game.
   *
   * @param currentScore the current score of this hangman game.
   */
  void setCurrentScore(int currentScore) {
    this.currentScore = currentScore;
  }

  /**
   * Get the current number of false guesses of this hangman game.
   *
   * @return the current number of false guesses of this hangman game.
   */
  int getFalseGuess() {
    return falseGuess;
  }

  /**
   * Set the current number of false guesses of this hangman game.
   *
   * @param falseGuess the current number of false guesses of this hangman game.
   */
  void setFalseGuess(int falseGuess) {
    this.falseGuess = falseGuess;
  }

  /**
   * Get the chosen gender of this hangman game.
   *
   * @return the chosen gender of this hangman game.
   */
  String getGender() {
    return gender;
  }

  /**
   * Set the chosen gender of this hangman game.
   *
   * @param gender the chosen gender of this hangman game.
   */
  void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Get the letters guessed so far of this hangman game.
   *
   * @return the letters guessed so far of this hangman game.
   */
  StringBuilder getLettersGuessed() {
    return lettersGuessed;
  }

  /**
   * Set the letters guessed so far of this hangman game.
   *
   * @param lettersGuessed the letters guessed so far of this hangman game.
   */
  void setLettersGuessed(StringBuilder lettersGuessed) {
    this.lettersGuessed = lettersGuessed;
  }

  /**
   * Get the displayedMaskedWord of this hangman game.
   *
   * @return the displayedMaskedWord of this hangman game.
   */
  StringBuilder getDisplayedMaskedWord() {
    return displayedMaskedWord;
  }

  /**
   * Set the the displayedMaskedWord of this hangman game.
   *
   * @param displayedMaskedWord the displayedMaskedWord of this hangman game.
   */
  void setDisplayedMaskedWord(StringBuilder displayedMaskedWord) {
    this.displayedMaskedWord = displayedMaskedWord;
  }

  /**
   * Get the current stage number of this hangman game.
   *
   * @return the current stage number of this hangman game.
   */
  int getStageNum() {
    return stageNum;
  }

  /**
   * Set the stage number of this hangman game.
   *
   * @param stageNum the stage number of this hangman game.
   */
  void setStageNum(int stageNum) {
    this.stageNum = stageNum;
  }

  /**
   * Get the current accumulated score of this hangman game.
   *
   * @return the current accumulated score of this hangman game.
   */
  int getAccumulatedScore() {
    return accumulatedScore;
  }

  /**
   * Set the current accumulated score of this hangman game.
   *
   * @param accumulatedScore the current accumulated score of this hangman game.
   */
  void setAccumulatedScore(int accumulatedScore) {
    this.accumulatedScore = accumulatedScore;
  }

  /**
   * Get whether if the bonus level is activated or not.
   *
   * @return true if the bonus level is activated, false otherwise.
   */
  boolean isBonusLevelActivated() {
    return bonusLevelActivated;
  }

  /**
   * Set whether if the bonus level is activated or not.
   *
   * @param bonusLevelActivated whether if the bonus level is activated or not.
   */
  void setBonusLevelActivated(boolean bonusLevelActivated) {
    this.bonusLevelActivated = bonusLevelActivated;
  }

  /** Resets this HangmanGameStatus. */
  void resetGameStatus() {
    played = false;
    secretWord = "";
    secretWordCharArray = null;
    maskedWordCharArray = null;
    currentScore = 120;
    falseGuess = 0;
    gender = "MALE";
    lettersGuessed = new StringBuilder();
    displayedMaskedWord = new StringBuilder();
    stageNum = 0;
    accumulatedScore = 0;
  }
}
