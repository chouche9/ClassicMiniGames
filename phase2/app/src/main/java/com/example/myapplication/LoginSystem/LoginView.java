package com.example.myapplication.LoginSystem;

public interface LoginView {

    void onUsernameEmptyError();

    void onPasswordEmptyError();

    void onFail();

    void onSuccess();

}
