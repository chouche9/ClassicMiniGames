package com.example.myapplication.loginsystem.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.mainpage.GameMain;
import com.example.myapplication.R;

/** A login activity. */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

  /** The input field used to get the username. */
  private EditText edtUsername;

  /** The input field used to get password. */
  private EditText edtPassword;

  /** Username that was input into EditText account. */
  private String name;

  /** The code used to match the correct activity launch. */
  private final int REQUEST_CODE = 5;

  private LoginPresenter loginPresenter;

  /**
   * Initializes this login activity.
   *
   * @param savedInstanceState a bundle of the resources in this activity.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    edtUsername = findViewById(R.id.nameEntry);
    edtPassword = findViewById(R.id.passwordEntry);
    Button btnLogin = findViewById(R.id.login);
    btnLogin.setOnClickListener(this);
    Button btnBackFront = findViewById(R.id.backfront);
    loginPresenter = new LoginPresenter(this);

    btnBackFront.setOnClickListener(this);
  }

  /**
   * Dispatch incoming result to the correct fragment.
   *
   * @param requestCode The code used to match the correct activity launch.
   * @param resultCode Represents whether the result is okay or not.
   * @param data The intent that is used to pass info.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE) {
      finish();
    }
  }

  /**
   * Events that happen when each of the buttons in this activity is clicked.
   *
   * @param view view responsible for event handling.
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.login:
        boolean isUsernameEmpty = false;
        boolean isPasswordEmpty = false;

        if (edtUsername.getText().length() == 0) {
          onUsernameEmptyError();
          isUsernameEmpty = true;
        }

        if (edtPassword.getText().length() == 0) {
          onPasswordEmptyError();
          isPasswordEmpty = true;
        }

        if (!isUsernameEmpty && !isPasswordEmpty) {
          name = edtUsername.getText().toString().trim();
          String passwordString = edtPassword.getText().toString().trim();
          loginPresenter.verify(name, passwordString);
        }
        break;
      case R.id.backfront:
        finish();
        break;
    }
  }

  /** Method to show username error */
  @Override
  public void onUsernameEmptyError() {
    edtUsername.setError("Please enter username!");
  }

  /** Method to show password error */
  @Override
  public void onPasswordEmptyError() {
    edtPassword.setError("Please enter password!");
  }

  /** Method to show error when username does not match the password */
  @Override
  public void onFail() {
    Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
  }

  /** Method that implements upon login success */
  @Override
  public void onSuccess() {
    Intent intent = new Intent(this, GameMain.class);
    intent.putExtra("user", name);
    startActivityForResult(intent, REQUEST_CODE);
  }
}
