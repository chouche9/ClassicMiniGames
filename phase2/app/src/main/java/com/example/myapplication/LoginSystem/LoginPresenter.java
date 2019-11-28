package com.example.myapplication.LoginSystem;

public class LoginPresenter implements LoginInteractor.onLoginInteractorListener{
    
    private LoginActivity loginActivity;
    
    private LoginInteractor loginInteractor;
    
    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginInteractor = new LoginInteractor();
    }

    public void verify(String username, String password) {
        if (loginInteractor != null){
            loginInteractor.verifyInteractor(username, password, loginActivity, this);
        }
    }

    @Override
    public void onSuccess() {
        if (loginActivity != null) {
            loginActivity.onSuccess();
        }
    }

    @Override
    public void onFail() {
        loginActivity.onFail();
    }
}
