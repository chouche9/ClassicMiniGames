package com.example.myapplication.loginsystem.createaccount;

/**
 * Interface that must be implemented by CreateAccountActivity.
 */
interface CreateAccountView {

    /** Called when the user fails to enter a username. */
    void onUsernameEmptyError();

    /** Called when the user fails to enter either the first or second password. */
    void onPasswordEmptyError();

    /** Called when the two passwords entered by the user does not match. */
    void onPasswordMismatchError();

    /** Called when the username entered is already taken. */
    void onFail();

    /** Called when the account is created successfully. */
    void onSuccess();
}
