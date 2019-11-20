package com.example.myapplication.Hangman;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;

public class HangmanGameStatInteractor extends GameStatus implements Parcelable {

  interface OnValidateCharListener {
    void onEmptyError();

    void onLetterUsedError(char c);

    void onDisplayViews();

    void onGameEnd(String message, int score, HangmanGameStatInteractor hm);
  }


  private static String WINMESSAGE = "Congratulations, you won!";

  private static String LOSEMESSSAGE = "You lost! The correct word was ";

  /** A string type that passes on the type of the game */
  private String type;

  /** An attribute that stores the instance of HangmanStatistics that Stores the Statistics of the
   * Game */
//  private HangmanStatistics hangmanStatistics;

  /** A boolean string that shows whether the game has been played before or not */
  private boolean played = false;

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
  private int falseGuess = 0;

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
  HangmanGameStatInteractor(String name) {
    super(name);
    this.type = "HangmanGameStat";
  }

  /**
   * Constructs this GameStatus using the values stored in parcel in.
   *
   * @param in the parcel that stores values of a GameStatus object.
   */
  private HangmanGameStatInteractor(Parcel in) {
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
  public static final Creator<HangmanGameStatInteractor> CREATOR =
      new Creator<HangmanGameStatInteractor>() {
        @Override
        public HangmanGameStatInteractor createFromParcel(Parcel in) {
          return new HangmanGameStatInteractor(in);
        }

        @Override
        public HangmanGameStatInteractor[] newArray(int size) {
          return new HangmanGameStatInteractor[size];
        }
      };

  public boolean getPlayed() {
    return played;
  }

  public void setPlayed(boolean b) {
    played = b;
  }
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
  boolean checkLetter(char guessedLetter) {
    if (!secretWord.contains(String.valueOf(guessedLetter))) {
      decreaseScore();
      falseGuess++;
      return false;
    } else {
      revealLetter(guessedLetter);
      return true;
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
    falseGuess = 0;
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

  /**
   * Checks if the game has ended (either user has won or lost) and sends intent to HangmanPlayAgain
   * activity.
   */
  public boolean checkIfGameEnded() {
//    int score = hangmanGameStat.getScore();
//    char[] maskedCharArray = hangmanGameStat.getMaskedWordCharArray();
    return (score == 0 || !String.valueOf(maskedWordCharArray).contains("_"));
  }

  public void validateCharInteractor(char c, HangmanGamePresenter listener) {
    if (checkLetterInGuessed(c)) {
      checkLetter(c);
      listener.onDisplayViews();
    } else {
      listener.onLetterUsedError(c);
    }
    if (checkIfGameEnded()) {
      if (score == 0) {
        listener.onGameEnd(LOSEMESSSAGE + secretWord + "!", score, this);
      } else {
        listener.onGameEnd(WINMESSAGE, score, this);
      }
    }

  }
}
