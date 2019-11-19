package com.example.myapplication.Hangman;

import com.example.myapplication.GameStatus;

public class HangmanGamePresenter implements HangmanGameStatInteractor.OnValidateCharListener{

    private HangmanGameActivity hangmanGameActivity;
    private HangmanGameStatInteractor hangmanGameStat;
    private HangmanWordGenerator hangmanWordGenerator;

    HangmanGamePresenter(HangmanGameActivity view, HangmanGameStatInteractor interactor) {
        this.hangmanGameActivity = view;
        this.hangmanGameStat = interactor;
        this.hangmanWordGenerator = new HangmanWordGenerator(view);
    }

    public void validateChar(char c) {
        hangmanGameStat.validateCharInteractor(c, this);
    }

    public GameStatus getHangmanGameStat() {
        return this.hangmanGameStat;
    }

    public boolean getPlayed() {
        return hangmanGameStat.getPlayed();
    }

    public void getNewWord() {
        String chosenWord = hangmanWordGenerator.getChosenWord();
        hangmanGameStat.generateWord(chosenWord);
    }

    public void onResuming() {
        hangmanGameActivity.showTxtMaskedWord(hangmanGameStat.getDisplayedMaskedWord().toString());
        hangmanGameActivity.showTxtScore(hangmanGameStat.getScore());
        hangmanGameActivity.showLettersGuessed(hangmanGameStat.getLettersGuessed().toString());
        hangmanGameActivity.setPictureIndex(hangmanGameStat.getFalseGuess());
        hangmanGameActivity.showImage();
    }

    @Override
    public void onEmptyError() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showEmptyError();
        }
    }

    @Override
    public void onLetterUsedError(char c) {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showLetterUsedError(c);
        }
    }

    @Override
    public void onDisplayViews() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.setPictureIndex(hangmanGameStat.getFalseGuess());
            hangmanGameActivity.showTxtMaskedWord(hangmanGameStat.getDisplayedMaskedWord().toString());
            hangmanGameActivity.showLettersGuessed(hangmanGameStat.getLettersGuessed().toString());
            hangmanGameActivity.showTxtScore(hangmanGameStat.getScore());
            hangmanGameActivity.showImage();
            hangmanGameActivity.clearEdtLetterGuess();
        }
    }

    @Override
    public void onGameEnd(String message, int score, HangmanGameStatInteractor hm) {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.gameEnded(message, score, hm);
        }
    }
}
