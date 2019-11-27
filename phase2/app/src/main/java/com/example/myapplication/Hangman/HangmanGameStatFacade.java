package com.example.myapplication.Hangman;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.GameStatus;

public class HangmanGameStatFacade extends GameStatus
        implements Parcelable, HangmanGameGuessedLetter.onHangmanGameGuessedLetterListener,
        HangmanGameGuessedWord.onHangmanGameGuessedWordListener{

    interface OnValidateCharListener {
        void onEmptyError();

        void onLetterUsedError(char c);

        void onDisplayViews();

        void onGameEnd(HangmanGameStatFacade hm);

        void onGuessWordFailed();
    }

    /**
     * A string type that passes on the type of the game
     */
    private String type;

    /**
     * A boolean string that shows whether the game has been played before or not
     */
    private boolean played = false;

    /**
     * A string that represent the secret word for the hangman game
     */
    private String secretWord = "";

    /**
     * A Character array of the secret word array
     */
    private char[] secretWordCharArray;

    /**
     * A Character array of the masked word Char Array to be shown in the game activity
     */
    private char[] maskedWordCharArray;

    /**
     * An int that store the total value of the game
     */
    private int currentScore = 120;

    /**
     * An int falseGuess stores the value in which it increases whenever player failed to guess the
     * letter
     */
    private int falseGuess = 0;

    /**
     * String gender stored the type of gender chosen for this game
     */
    private String gender = "MALE";

    /**
     * A StringBuilder that will store the letters that has been guessed by the user
     */
    private StringBuilder lettersGuessed = new StringBuilder();

    /**
     * A StringBuilder that will store the word to be displayed on the game but are masked.
     */
    private StringBuilder displayedMaskedWord = new StringBuilder();

    private int stageNum = 0;

    private int accumulatedScore = 0;

    private boolean bonusLevelActivated = false;

    private HangmanGameGuessedLetter hangmanGameGuessedLetter;
    private HangmanGameGuessedWord hangmanGameGuessedWord;

    /**
     * A constructor to construct the HangmanGame statistics
     *
     * @param name: Name of the user
     */
    HangmanGameStatFacade(String name) {
        super(name);
        this.type = "HangmanGameStat";
    }

    /**
     * Constructs this GameStatus using the values stored in parcel in.
     *
     * @param in the parcel that stores values of a GameStatus object.
     */
    private HangmanGameStatFacade(Parcel in) {
        super(in);
        played = in.readByte() != 0;
        secretWord = in.readString();
        secretWordCharArray = in.createCharArray();
        maskedWordCharArray = in.createCharArray();
        currentScore = in.readInt();
        falseGuess = in.readInt();
        gender = in.readString();
        lettersGuessed = new StringBuilder(in.readString());
        displayedMaskedWord = new StringBuilder(in.readString());
        type = in.readString();
        stageNum = in.readInt();
        accumulatedScore = in.readInt();
        bonusLevelActivated = in.readByte() != 0;
    }

    /**
     * Binds the GameStatus object.
     */
    public static final Creator<HangmanGameStatFacade> CREATOR =
            new Creator<HangmanGameStatFacade>() {
                @Override
                public HangmanGameStatFacade createFromParcel(Parcel in) {
                    return new HangmanGameStatFacade(in);
                }

                @Override
                public HangmanGameStatFacade[] newArray(int size) {
                    return new HangmanGameStatFacade[size];
                }
            };

    // GETTER & SETTER //
    boolean getPlayed() {
        return played;
    }

    int getStageNum() {
        return stageNum;
    }

    void setFalseGuess(int falseGuess) {
        this.falseGuess = falseGuess;
    }

    void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

    String getSecretWord() {
        return secretWord;
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
     * Getter to get the currentScore
     *
     * @return int the currentScore
     */
    int getCurrentScore() {
        return currentScore;
    }

    int getAccumulatedScore() {
        return accumulatedScore;
    }

    void setAccumulatedScore(int accumulatedScore) {
        this.accumulatedScore = accumulatedScore;
    }

    void addAccumulatedScore(int addScore) {
        accumulatedScore += addScore;
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


    boolean isBonusLevelActivated() {
        return bonusLevelActivated;
    }

    void setBonusLevelActivated(boolean bonusLevelActivated) {
        this.bonusLevelActivated = bonusLevelActivated;
    }

    // END OF GETTERS AND SETTER //

    /**
     * Checks if the game has ended (either user has won or lost) and sends intent to
     * HangmanStageEnded activity.
     */
    private void checkIfGameEnded(HangmanGamePresenter listener) {
        if (gameEndedInteractor()) {
            accumulatedScore += currentScore;
            listener.onGameEnd(this);
        }
    }

    void validateCharInteractor(char guessedLetter, HangmanGamePresenter listener) {
        if (isLetterInGuessed(guessedLetter)) {
            hangmanGameGuessedLetter.checkLetter(secretWord, guessedLetter, this);
            listener.onDisplayViews();
        } else {
            listener.onLetterUsedError(guessedLetter);
        }
        checkIfGameEnded(listener);
    }


    void validateWordInteractor(String guessedWord, HangmanGamePresenter listener) {
        hangmanGameGuessedWord.checkWord(secretWord, guessedWord, this, listener);
        checkIfGameEnded(listener);
    }

    @Override
    public void onCorrectLetter(char guessedLetter) {
        decreaseScore();
        falseGuess++;
    }

    @Override
    public void onWrongLetter(char guessedLetter) {
        revealLetter(guessedLetter);
    }

    @Override
    public void onCorrectWord(String guessedWord) {
        maskedWordCharArray = secretWord.toCharArray();
    }

    @Override
    public void onWrongWord(String guessedWord, HangmanGamePresenter hm) {
        decreaseScore();
        falseGuess++;
        hm.onGuessWordFailed();
    }

    /**
     * Generating the word for the game
     *
     * @param chosenWord the generated word for this hangman game
     */
    void generateWord(String chosenWord) {
        played = true;
        stageNum++;
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
    private boolean isLetterInGuessed(char guessedLetter) {
        if (!lettersGuessed.toString().contains(String.valueOf(guessedLetter))) {
            String addLetter = guessedLetter + ", ";
            lettersGuessed.append(addLetter);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to decrease the currentScore when the guessed letter is not in the secret word
     */
    private void decreaseScore() {
        currentScore -= 20;
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

    /**
     * A method to reset the Game Status
     */
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
        type = "HangmanGameStat";
        stageNum = 0;
        accumulatedScore = 0;
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
     * @param i      flags.
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte((byte) (played ? 1 : 0));
        parcel.writeString(secretWord);
        parcel.writeCharArray(secretWordCharArray);
        parcel.writeCharArray(maskedWordCharArray);
        parcel.writeInt(currentScore);
        parcel.writeInt(falseGuess);
        parcel.writeString(gender);
        parcel.writeString(lettersGuessed.toString());
        parcel.writeString(displayedMaskedWord.toString());
        parcel.writeString(type);
        parcel.writeInt(stageNum);
        parcel.writeInt(accumulatedScore);
        parcel.writeByte((byte) (bonusLevelActivated ? 1 : 0));
    }

    boolean gameEndedInteractor() {
        if (maskedWordCharArray != null) {
            return falseGuess == 6 || !String.valueOf(maskedWordCharArray).contains("_");
        }
        return false;
    }
}
