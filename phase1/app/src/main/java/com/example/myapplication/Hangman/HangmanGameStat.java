package com.example.myapplication.Hangman;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;

public class HangmanGameStat extends GameStatus implements Parcelable {

  /** A string type that passes on the type of the game */
  private String type;

  /** A boolean string that shows whether the game has been played before or not */
  boolean played = false;

  /** A string that represent the secret word for the hangman game */
  private String secretWord = "";

  /** A Character array of the secret word array */
  private char[] secretWordCharArray;

  /** A Character array of the masked word Char Array to be shown in the game activity */
  private char[] maskedWordCharArray;

  /** An int that store the total value of the game */
  private int score = 120;

  /**
   * An int falseGuess stores the value in which it increases whenever player failed to guess the
   * letter
   */
  private int falseGuess = -1;

  /** String gender stored the type of gender chosen for this game */
  private String gender = "MALE";

  /** A StringBuilder that will store the letters that has been guessed by the user */
  private StringBuilder lettersGuessed = new StringBuilder();

  /** A StringBuilder that will store the word to be displayed on the game but are masked. */
  private StringBuilder displayedMaskedWord = new StringBuilder();

  /**
   * A constructor to construct the HangmanGame statistics
   *
   * @param name: Name of the user
   */
  HangmanGameStat(String name) {
    super(name);
    this.type = "HangmanGameStat";
  }

  /**
   * Constructs this GameStatus using the values stored in parcel in.
   *
   * @param in the parcel that stores values of a GameStatus object.
   */
  private HangmanGameStat(Parcel in) {
    super(in);
    played = in.readByte() != 0;
    secretWord = in.readString();
    secretWordCharArray = in.createCharArray();
    maskedWordCharArray = in.createCharArray();
    score = in.readInt();
    falseGuess = in.readInt();
    gender = in.readString();
    lettersGuessed = new StringBuilder(in.readString());
    displayedMaskedWord = new StringBuilder(in.readString());
    type = in.readString();
  }

  /** Binds the GameStatus object. */
  public static final Creator<HangmanGameStat> CREATOR =
      new Creator<HangmanGameStat>() {
        @Override
        public HangmanGameStat createFromParcel(Parcel in) {
          return new HangmanGameStat(in);
        }

        @Override
        public HangmanGameStat[] newArray(int size) {
          return new HangmanGameStat[size];
        }
      };

  /**
   * Getter to get the masked word char array
   *
   * @return char[] the masked word char array
   */
  char[] getMaskedWordCharArray() {
    return maskedWordCharArray;
  }

  /**
   * Getter to get the lettersGuessed
   *
   * @return StringBuilder the attribute lettersGuessed
   */
  StringBuilder getLettersGuessed() {
    return lettersGuessed;
  }

  /**
   * Getter to get the DisplayedMaskedWord
   *
   * @return StringBuilderthe attribute DisplayedMaskedWord
   */
  StringBuilder getDisplayedMaskedWord() {
    return displayedMaskedWord;
  }

  /**
   * Getter to get the score
   *
   * @return int the score
   */
  int getScore() {
    return score;
  }

  /** Setter to get the falseGuess */
  void setFalseGuess(int falseGuess) {
    this.falseGuess = falseGuess;
  }

  /**
   * Getter to get the False Guess
   *
   * @return int the value of falseGuess
   */
  int getFalseGuess() {
    return falseGuess;
  }

  /**
   * Setter to set the gender
   *
   * @param gender : gender of the game
   */
  void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Get the gender of the user
   *
   * @return the gender of the user
   */
  String getGender() {
    return gender;
  }

  /**
   * A getter that get the secretWord of hangman
   *
   * @return String the secretWord of hangman
   */
  String getSecretWord() {
    return secretWord;
  }

  /**
   * Generating the word for the game
   *
   * @param chosenWord the generated word for this hangman game
   */
  void generateWord(String chosenWord) {
    played = true;
    secretWord = chosenWord;
    secretWordCharArray = new char[secretWord.length()];
    maskedWordCharArray = new char[secretWord.length()];
    for (int i = 0; i < secretWord.length(); i++) {
      maskedWordCharArray[i] = '_';
      secretWordCharArray[i] = secretWord.charAt(i);
    }
    displayedMaskedWord = generateDisplayedMaskedWord();
  }

  /**
   * Check whether the letter guessed is already used
   *
   * @param guessedLetter: Letter chosen by the user
   * @return boolean whether if the guessedLetter was already guessed or not
   */
  boolean checkLetterInGuessed(char guessedLetter) {
    if (!lettersGuessed.toString().contains(String.valueOf(guessedLetter))) {
      updateLettersGuessed(guessedLetter);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to check whether the letter is in the secret Word
   *
   * @param guessedLetter the letter guessed by the user
   */
  void checkLetter(char guessedLetter) {
    if (!secretWord.contains(String.valueOf(guessedLetter))) {
      decreaseScore();
    } else {
      revealLetter(guessedLetter);
    }
  }

  /**
   * Method to update the letter guessed in the masked word
   *
   * @param guessedLetter the letter guessed by the user
   */
  private void updateLettersGuessed(char guessedLetter) {
    String addLetter = guessedLetter + ", ";
    lettersGuessed.append(addLetter);
  }

  /** Method to decrease the score when the guessed letter is not in the secret word */
  private void decreaseScore() {
    score -= 20;
  }

  /**
   * method to reveal the letter in activity
   *
   * @param guessedLetter the letter guessed by the user
   */
  private void revealLetter(char guessedLetter) {
    for (int i = 0; i < secretWordCharArray.length; i++) {
      if (secretWordCharArray[i] == guessedLetter) {
        maskedWordCharArray[i] = guessedLetter;
      }
    }
    displayedMaskedWord = generateDisplayedMaskedWord();
  }

  /**
   * A method that generate the displayed masked word in the activity
   *
   * @return Stringbuilder the displayed masked word in the activity
   */
  private StringBuilder generateDisplayedMaskedWord() {
    StringBuilder maskedWord = new StringBuilder();
    for (char letter : maskedWordCharArray) {
      maskedWord.append(letter);
      maskedWord.append(" ");
    }
    return maskedWord;
  }

  /** A method to reset the Game Status */
  void resetGameStatus() {
    played = false;
    secretWord = "";
    secretWordCharArray = null;
    maskedWordCharArray = null;
    score = 120;
    falseGuess = -1;
    gender = "MALE";
    lettersGuessed = new StringBuilder();
    displayedMaskedWord = new StringBuilder();
    type = "HangmanGameStat";
  }

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
   * Write the attribute of this GameStatus to parcel.
   *
   * @param parcel parcel to write the attributes of this GameStatus.
   * @param i flags.
   */
  @Override
  public void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
    parcel.writeByte((byte) (played ? 1 : 0));
    parcel.writeString(secretWord);
    parcel.writeCharArray(secretWordCharArray);
    parcel.writeCharArray(maskedWordCharArray);
    parcel.writeInt(score);
    parcel.writeInt(falseGuess);
    parcel.writeString(gender);
    parcel.writeString(lettersGuessed.toString());
    parcel.writeString(displayedMaskedWord.toString());
    parcel.writeString(type);
  }
}
