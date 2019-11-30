package com.example.myapplication.loginsystem.CreateAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.domain.User;
import com.example.myapplication.R;
import com.example.myapplication.domain.UserManager;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, com.example.myapplication.loginsystem.CreateAccount.CreateAccountView {

    /** EditText of the accountName */
    EditText accountEntry;

    /** EditText of first time password entry */
    EditText password1;

    /** EditText of the second time password entry */
    EditText password2;

    /** Button for create account */
    Button createAccount;

    /** the intent that get passed in */
    Intent createAccountIntent;

    /**
     * String representation of the username entered by the user.
     */
    String username;

    /**
     * String representation of the first password entered by the user.
     */
    String password1Str;

    /**
     * String representation of the second password entered by the user.
     */
    String password2Str;

    /** singleton userManager */
    UserManager userManager;

    private com.example.myapplication.loginsystem.CreateAccount.CreateAccountPresenter createAccountPresenter;

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
        createAccountIntent = getIntent();
        accountEntry = findViewById(R.id.accountEntry);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        createAccount = findViewById(R.id.enterButton);
        createAccount.setOnClickListener(this);
        createAccountPresenter = new com.example.myapplication.loginsystem.CreateAccount.CreateAccountPresenter(this);
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

        if(accountEntry.getText().length() == 0) {
            onUsernameEmptyError();
            isUsernameEmpty = true;
        }

        if(password1.getText().length() == 0 || password2.getText().length() == 0) {
            onPasswordEmptyError();
            isPasswordEmpty = true;
        }

        if(!password1.getText().toString().equals(password2.getText().toString())) {
            onPasswordMismatchError();
            isPasswordMismatch = true;
        }

        if (!isUsernameEmpty && !isPasswordEmpty && !isPasswordMismatch) {
            username = accountEntry.getText().toString().trim();
            password1Str = password1.getText().toString().trim();
            password2Str = password2.getText().toString().trim();
            createAccountPresenter.verify(username);
        }
    }

    /**
     * Called when the user fails to enter a username.
     */
    @Override
    public void onUsernameEmptyError() { accountEntry.setError("Please enter a username"); }

    /**
     * Called when the user fails to enter either the first or second password.
     */
    @Override
    public void onPasswordEmptyError() { password1.setError("Please enter a password!"); }

    /**
     * Called when the two passwords entered by the user does not match.
     */
    @Override
    public void onPasswordMismatchError() { password1.setError("Please enter the same password!"); }

    /**
     * Called when the username entered is already taken.
     */
    @Override
    public void onFail() {
        Toast.makeText(this, "This user name is already taken!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the account is created successfully.
     */
    @Override
    public void onSuccess() {
        User newUser = new User(username, password2Str);
        Toast.makeText(this, "Create Successfully!", Toast.LENGTH_SHORT).show();
        userManager.saveUser(newUser);
        finish();
    }
}
