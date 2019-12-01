package com.example.myapplication.loginsystem.Login;

import com.example.myapplication.domain.UserManager;

/**
 * Class Login Interactor
 */
class LoginInteractor {

    /**
     * Interface onLoginInteractorListener
     */
    interface onLoginInteractorListener {

        /**
         * Abstract method to be overridden upon login success
         */
        void onSuccess();

        /**
         * Abstract method to be overridden upon fail on login
         */
        void onFail();

    }

    /**
     * method to verify whether the username match the password
     * @param username input username
     * @param password input password
     * @param loginActivity the login Activity instance
     * @param listener
     */
    public void verifyInteractor(String username, String password,
                                 LoginActivity loginActivity, onLoginInteractorListener listener) {

        UserManager userManager = UserManager.getInstance(loginActivity);
        if(userManager.authenticate(username, password)){
            listener.onSuccess();
        } else {
            listener.onFail();
        }

    }

}
