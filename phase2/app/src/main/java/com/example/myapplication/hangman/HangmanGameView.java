package com.example.myapplication.hangman;

interface HangmanGameView {

    void showEmptyError();

    void showLetterUsedError(char c);

    void showImage();

    void showTxtMaskedWord(String word);

    void clearEdtLetterGuess();

    void showLettersGuessed(String word);

    void showTxtScore(int score);

    void setPictureIndex(int index);

    void gameEnded(HangmanGameStatus hm);

    void showGuessWordFailed();

    void showTxtStageNum(int stageNum);
}
