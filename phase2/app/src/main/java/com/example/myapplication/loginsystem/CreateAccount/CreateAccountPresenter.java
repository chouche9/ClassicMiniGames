package com.example.myapplication.loginsystem.CreateAccount;

public class CreateAccountPresenter implements CreateAccountInteractor.onCreateAccountInteractorListener{

    private CreateAccountActivity createAccountActivity;

    private CreateAccountInteractor createAccountInteractor;

    /**
     * Constructs the CreateAccountPresenter with the specified createAccountActivity.
     * @param createAccountActivity the activity that utilizes this presenter.
     */
    CreateAccountPresenter(CreateAccountActivity createAccountActivity) {
        this.createAccountActivity = createAccountActivity;
        this.createAccountInteractor = new CreateAccountInteractor();
    }

    /**
     * verifies if this username has been taken using the create account interactor.
     * @param username the username being tested.
     */
    void verify(String username) {
        if (createAccountInteractor != null) {
            createAccountInteractor.verifyInteractor(username, createAccountActivity, this);
        }
    }

    /**
     * Called when the account is created successfully.
     */
    @Override
    public void onSuccess() {
        if(createAccountActivity != null) {
            createAccountActivity.onSuccess();
        }
    }

    /**
     * Called when the username entered is already taken.
     */
    @Override
    public void onFail() {
        createAccountActivity.onFail();
    }
}
