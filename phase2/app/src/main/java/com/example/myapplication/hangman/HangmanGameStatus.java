package com.example.myapplication.hangman;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.databaseconnector.GameEnum;
import com.example.myapplication.domain.GameStatus;

public class HangmanGameStatus extends GameStatus implements Parcelable {

  /** A boolean string that shows whether the game has been played before or not */
  private boolean played = false;

  /** A string that represent the secret word for the hangman game */
  private String secretWord = "";

  /** A Character array of the secret word array */
  private char[] secretWordCharArray;

  /** A Character array of the masked word Char Array to be shown in the game activity */
  private char[] maskedWordCharArray;

  /** An int that store the total value of the game */
  private int currentScore = 120;

  /**
   * An int falseGuess stores the value in which it increases whenever player failed to guess the
   * letter
   */
  private int falseGuess = 0;

  /** String gender stored the type of gender chosen for this game */
  private String gender = "MALE";

  /** A StringBuilder that will store the letters that has been guessed by the user */
  private StringBuilder lettersGuessed = new StringBuilder();

  /** A StringBuilder that will store the word to be displayed on the game but are masked. */
  private StringBuilder displayedMaskedWord = new StringBuilder();

  private int stageNum = 0;

  private int accumulatedScore = 0;

  private boolean bonusLevelActivated = false;

  /**
   * A constructor to construct the HangmanGame statistics
   *
   * @param name: Name of the user
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

  boolean isPlayed() {
    return played;
  }

  void setPlayed(boolean played) {
    this.played = played;
  }

  String getSecretWord() {
    return secretWord;
  }

  void setSecretWord(String secretWord) {
    this.secretWord = secretWord;
  }

  char[] getSecretWordCharArray() {
    return secretWordCharArray;
  }

  void setSecretWordCharArray(char[] secretWordCharArray) {
    this.secretWordCharArray = secretWordCharArray;
  }

  char[] getMaskedWordCharArray() {
    return maskedWordCharArray;
  }

  void setMaskedWordCharArray(char[] maskedWordCharArray) {
    this.maskedWordCharArray = maskedWordCharArray;
  }

  int getCurrentScore() {
    return currentScore;
  }

  void setCurrentScore(int currentScore) {
    this.currentScore = currentScore;
  }

  int getFalseGuess() {
    return falseGuess;
  }

  void setFalseGuess(int falseGuess) {
    this.falseGuess = falseGuess;
  }

  String getGender() {
    return gender;
  }

  void setGender(String gender) {
    this.gender = gender;
  }

  StringBuilder getLettersGuessed() {
    return lettersGuessed;
  }

  void setLettersGuessed(StringBuilder lettersGuessed) {
    this.lettersGuessed = lettersGuessed;
  }

  StringBuilder getDisplayedMaskedWord() {
    return displayedMaskedWord;
  }

  void setDisplayedMaskedWord(StringBuilder displayedMaskedWord) {
    this.displayedMaskedWord = displayedMaskedWord;
  }

  int getStageNum() {
    return stageNum;
  }

  void setStageNum(int stageNum) {
    this.stageNum = stageNum;
  }

  int getAccumulatedScore() {
    return accumulatedScore;
  }

  void setAccumulatedScore(int accumulatedScore) {
    this.accumulatedScore = accumulatedScore;
  }

  boolean isBonusLevelActivated() {
    return bonusLevelActivated;
  }

  void setBonusLevelActivated(boolean bonusLevelActivated) {
    this.bonusLevelActivated = bonusLevelActivated;
  }

  /** A method to reset the Game Status */
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
