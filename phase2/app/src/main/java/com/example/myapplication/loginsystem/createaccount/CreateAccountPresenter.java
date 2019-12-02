package com.example.myapplication.loginsystem.createaccount;

/** The presenter of CreateAccountActivity. */
class CreateAccountPresenter implements CreateAccountInteractor.onCreateAccountInteractorListener {

  /** The view of the create account. */
  private CreateAccountView createAccountView;

  /** The activity of create account (to get the context). */
  private CreateAccountActivity createAccountActivity;

  /** The interactor of create account. */
  private CreateAccountInteractor createAccountInteractor;

  /**
   * Constructs the CreateAccountPresenter with the specified createAccountActivity.
   *
   * @param createAccountView the activity that utilizes this presenter.
   */
  CreateAccountPresenter(
      CreateAccountView createAccountView, CreateAccountActivity createAccountActivity) {
    this.createAccountView = createAccountView;
    this.createAccountActivity = createAccountActivity;
    this.createAccountInteractor = new CreateAccountInteractor();
  }

  /**
   * verifies if this username has been taken using the create account interactor.
   *
   * @param username the username being tested.
   */
  void verify(String username) {
    if (createAccountInteractor != null) {
      createAccountInteractor.verifyInteractor(username, createAccountActivity, this);
    }
  }

  /** Called when the account is created successfully. */
  @Override
  public void onSuccess() {
    if (createAccountView != null) {
      createAccountView.onSuccess();
    }
  }

  /** Called when the username entered is already taken. */
  @Override
  public void onFail() {
    createAccountView.onFail();
  }
}
