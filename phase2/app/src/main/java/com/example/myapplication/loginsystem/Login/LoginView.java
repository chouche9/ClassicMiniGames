package com.example.myapplication.loginsystem.Login;

/**
 * interface for LoginView
 */
interface LoginView {

    /**
     * Method for empty username error
     */
    void onUsernameEmptyError();

    /**
     * Method for empty password error
     */
    void onPasswordEmptyError();

    /**
     * Method when login fail
     */
    void onFail();

    /**
     * Method when login success
     */
    void onSuccess();

}
