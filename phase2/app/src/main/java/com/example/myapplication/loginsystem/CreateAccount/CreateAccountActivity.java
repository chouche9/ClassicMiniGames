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

    String username;

    String password1Str;

    String password2Str;

    /** singleton userManager */
    UserManager userManager;

    private com.example.myapplication.loginsystem.CreateAccount.CreateAccountPresenter createAccountPresenter;



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

    @Override
    public void onUsernameEmptyError() { accountEntry.setError("Please enter a username"); }

    @Override
    public void onPasswordEmptyError() { password1.setError("Please enter a password!"); }

    @Override
    public void onPasswordMismatchError() { password1.setError("Please enter the same password!"); }

    @Override
    public void onFail() {
        Toast.makeText(this, "This user name is already taken!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        User newUser = new User(username, password2Str);
        Toast.makeText(this, "Create Successfully!", Toast.LENGTH_SHORT).show();
        userManager.saveUser(newUser);
        finish();
    }
}
