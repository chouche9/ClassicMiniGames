package com.example.myapplication.loginsystem.Login;

/**
 * Class Login Presenter
 */
class LoginPresenter implements LoginInteractor.onLoginInteractorListener{

    /**
     * LoginActivity instance
     */
    private LoginActivity loginActivity;

    /**
     * Login Interactor instance
     */
    private LoginInteractor loginInteractor;

    /**
     * Constructor for LoginPresenter
     * @param loginActivity Login Activity instance
     */
    LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginInteractor = new LoginInteractor();
    }

    /**
     * Method to verify the username with the password to be passed to he interactor
     * @param username input username
     * @param password input password
     */
    void verify(String username, String password) {
        if (loginInteractor != null){
            loginInteractor.verifyInteractor(username, password, loginActivity, this);
        }
    }

    /**
     * Method that activates upon login Activity
     */
    @Override
    public void onSuccess() {
        if (loginActivity != null) {
            loginActivity.onSuccess();
        }
    }

    /**
     * Method that activates upon login fail
     */
    @Override
    public void onFail() {
        loginActivity.onFail();
    }
}
