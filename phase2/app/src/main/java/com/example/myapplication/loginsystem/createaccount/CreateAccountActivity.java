package com.example.myapplication.loginsystem.createaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.domain.User;
import com.example.myapplication.R;
import com.example.myapplication.domain.UserManager;

/** The activity of Create Account. */
public class CreateAccountActivity extends AppCompatActivity
        implements View.OnClickListener, CreateAccountView {

    /** EditText of the accountName */
    private EditText accountEntry;

    /** EditText of first time password entry */
    private EditText password;

    /** EditText of the second time password entry */
    private EditText retypePassword;

    /** String representation of the username entered by the user. */
    private String username;

    /** String representation of the second password entered by the user. */
    private String strRetypePassword;

    /** singleton userManager */
    private UserManager userManager;

    /** Presenter of this create account activity. */
    private CreateAccountPresenter createAccountPresenter;

    /**
     * Initializes this login activity.
     *
     * @param savedInstanceState a bundle of the resources in this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userManager = UserManager.getInstance(this);
        accountEntry = findViewById(R.id.accountEntry);
        password = findViewById(R.id.password1);
        retypePassword = findViewById(R.id.password2);
        Button createAccount = findViewById(R.id.enterButton);
        createAccount.setOnClickListener(this);
        createAccountPresenter = new CreateAccountPresenter(this, this);
    }

    /**
     * Events that happen when each of the buttons in this activity is clicked.
     *
     * @param view view responsible for event handling.
     */
    @Override
    public void onClick(View view) {
        boolean isUsernameEmpty = false;
        boolean isPasswordEmpty = false;
        boolean isPasswordMismatch = false;

        if (accountEntry.getText().length() == 0) {
            onUsernameEmptyError();
            isUsernameEmpty = true;
        }

        if (password.getText().length() == 0 || retypePassword.getText().length() == 0) {
            onPasswordEmptyError();
            isPasswordEmpty = true;
        }

        if (!password.getText().toString().equals(retypePassword.getText().toString())) {
            onPasswordMismatchError();
            isPasswordMismatch = true;
        }

        if (!isUsernameEmpty && !isPasswordEmpty && !isPasswordMismatch) {
            username = accountEntry.getText().toString().trim();
            strRetypePassword = retypePassword.getText().toString().trim();
            createAccountPresenter.verify(username);
        }
    }

    /** Called when the user fails to enter a username. */
    @Override
    public void onUsernameEmptyError() {
        accountEntry.setError("Please enter a username");
    }

    /** Called when the user fails to enter either the first or second password. */
    @Override
    public void onPasswordEmptyError() {
        password.setError("Please enter a password!");
    }

    /** Called when the two passwords entered by the user does not match. */
    @Override
    public void onPasswordMismatchError() {
        password.setError("Please enter the same password!");
    }

    /** Called when the username entered is already taken. */
    @Override
    public void onFail() {
        Toast.makeText(this, "This user name is already taken!", Toast.LENGTH_SHORT).show();
    }

    /** Called when the account is created successfully. */
    @Override
    public void onSuccess() {
        User newUser = new User(username, strRetypePassword);
        Toast.makeText(this, "Create Successfully!", Toast.LENGTH_SHORT).show();
        userManager.saveUser(newUser);
        finish();
    }
}
