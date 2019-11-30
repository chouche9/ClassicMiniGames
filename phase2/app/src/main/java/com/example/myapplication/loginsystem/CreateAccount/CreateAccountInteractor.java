package com.example.myapplication.loginsystem.CreateAccount;

import com.example.myapplication.domain.UserManager;

class CreateAccountInteractor {

    interface onCreateAccountInteractorListener {

        /**
         * Called when the account is created successfully.
         */
        void onSuccess();

        /**
         * Called when the username entered is already taken.
         */
        void onFail();
    }

    /**
     * Responsible for verifying whether username has already been used.
     * @param username the username been verified.
     * @param createAccountActivity the activity that requires the verification.
     *      * @param listener responsible for calling the right method based the result of verification.
     */
    void verifyInteractor(String username, CreateAccountActivity createAccountActivity,
                                 onCreateAccountInteractorListener listener) {
        UserManager userManager = UserManager.getInstance(createAccountActivity);
        if(userManager.createAuthenticate(username)) {
            listener.onSuccess();
        } else {
            listener.onFail();
        }
    }
}
