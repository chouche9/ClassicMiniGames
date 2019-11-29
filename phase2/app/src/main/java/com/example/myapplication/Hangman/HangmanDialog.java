package com.example.myapplication.Hangman;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

/** The dialog that is activated when the user wishes to guess the full word. */
public class HangmanDialog extends AppCompatDialogFragment {
  /** The input field used to get the user's guessed word. */
  private EditText edtWordGuessed;

  /** Button that the user clicks to guess the full word. */
  private Button btnDialogGuessWord;

  /** The user interface of this hangman game. */
  private HangmanGameActivity hangmanGameActivity;

  public interface HangmanDialogListener {
    /**
     * Checks whether if the full word guessed is correct or not.
     *
     * @param wordGuessed the full word guessed by the player.
     */
    void validateGuessedWord(String wordGuessed);
  }

  /**
   * Initializes this dialog.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   * @return the dialog that is displayed on the screen.
   */
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.activity_hangman_guessword_dialog, null);

    builder.setView(view);

    edtWordGuessed = view.findViewById(R.id.edtWordGuessed);
    btnDialogGuessWord = view.findViewById(R.id.btnDialogGuessWord);

    setBtnDialogGuessWord();

    return builder.create();
  }

  /** Event that happens after the btnDialogGuessWord button is clicked. */
  private void setBtnDialogGuessWord() {
    btnDialogGuessWord.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String guessedWord = edtWordGuessed.getText().toString();
            hangmanGameActivity.validateGuessedWord(guessedWord);
          }
        });
  }

  /**
   * Called when the dialog fragment is first attached to the context.
   *
   * @param context the context of the activity.
   */
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    hangmanGameActivity = (HangmanGameActivity) context;
  }
}
