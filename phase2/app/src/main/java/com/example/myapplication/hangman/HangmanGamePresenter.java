package com.example.myapplication.hangman;

class HangmanGamePresenter implements HangmanGameInteractor.OnValidateCharListener{

    private HangmanGameActivity hangmanGameActivity;
    private HangmanGameInteractor hangmanGameInteractor;
    private HangmanWordGenerator hangmanWordGenerator;

    HangmanGamePresenter(HangmanGameActivity view, HangmanGameInteractor hangmanGameInteractor) {
        this.hangmanGameActivity = view;
        this.hangmanGameInteractor = hangmanGameInteractor;
        this.hangmanWordGenerator = new HangmanWordGenerator(view);
    }

    void validateChar(char c) {
        hangmanGameInteractor.validateChar(c, this);
    }

    void validateWord(String guessedWord) {
        hangmanGameInteractor.validateWord(guessedWord, this);
    }

    HangmanGameStatus getHangmanGameStat() {
        return hangmanGameInteractor.getHangmanGameStat();
    }

    boolean getPlayed() {
        return getHangmanGameStat().isPlayed();
    }

    void getNewWord() {
        String chosenWord = hangmanWordGenerator.getChosenWord();
        hangmanGameInteractor.generateWord(chosenWord);
    }

    boolean onCheckIfGameEnded() {
        return hangmanGameInteractor.gameEnded();
    }

    void onOpenGameEndedActivity() {
        onGameEnd(getHangmanGameStat());
    }

    void onResuming() {
        hangmanGameActivity.showTxtStageNum(getHangmanGameStat().getStageNum());
        hangmanGameActivity.showTxtMaskedWord(getHangmanGameStat().getDisplayedMaskedWord().toString());
        hangmanGameActivity.showTxtScore(getHangmanGameStat().getCurrentScore());
        hangmanGameActivity.showLettersGuessed(getHangmanGameStat().getLettersGuessed().toString());
        hangmanGameActivity.setPictureIndex(getHangmanGameStat().getFalseGuess());
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
            hangmanGameActivity.clearEdtLetterGuess();
        }
    }

    @Override
    public void onDisplayViews() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showTxtStageNum(getHangmanGameStat().getStageNum());
            hangmanGameActivity.setPictureIndex(getHangmanGameStat().getFalseGuess());
            hangmanGameActivity.showTxtMaskedWord(getHangmanGameStat().getDisplayedMaskedWord().toString());
            hangmanGameActivity.showLettersGuessed(getHangmanGameStat().getLettersGuessed().toString());
            hangmanGameActivity.showTxtScore(getHangmanGameStat().getCurrentScore());
            hangmanGameActivity.showImage();
            hangmanGameActivity.clearEdtLetterGuess();
        }
    }

    @Override
    public void onGameEnd(HangmanGameStatus hm) {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.gameEnded(hm);
        }
    }

    @Override
    public void onGuessWordFailed() {
        if (hangmanGameActivity != null) {
            hangmanGameActivity.showGuessWordFailed();
            onDisplayViews();
        }
    }
}

