package com.ameron32.apps.tapnotes.parse;

import android.os.AsyncTask;

import com.ameron32.apps.tapnotes.R;

/**
 * Created by klemeilleur on 3/16/2015.
 */
public class MyLoginDummyActivity extends MyLoginActivity {

  /**
   * A dummy authentication store containing known user names and passwords.
   * TODO: remove after connecting to a real authentication system.
   */
  private static final String[] DUMMY_CREDENTIALS = new String[]{
      "foo@example.com:hello", "bar@example.com:world"
  };
  /**
   * Keep track of the login task to ensure we can cancel it if requested.
   */
  private UserLoginTask mAuthTask = null;

  @Override
  protected int inflateActivityLayout() {
    return R.layout.activity_my_login;
  }

  @Override
  protected void performLogin(String email, String password) {
    if (mAuthTask != null) { return; }
      mAuthTask = new UserLoginTask(email, password);
      mAuthTask.execute((Void) null);
  }

  @Override
  protected void performSignUp(String email, String password) {
    // TODO
  }

  @Override
  protected void performForgotPassword(String email) {
    // do nothing
  }


  /**
   * Represents an asynchronous login/registration task used to authenticate
   * the user.
   */
  public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;

    UserLoginTask(String email, String password) {
      mEmail = email;
      mPassword = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
      // TODO: attempt authentication against a network service.

      try {
        // Simulate network access.
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        return false;
      }

      String email;
      String password;

      for (String credential : DUMMY_CREDENTIALS) {
        String[] pieces = credential.split(":");
        email = pieces[0];
        password = pieces[1];

        if (email.equals(mEmail)) {
          // Account exists, return true if the password matches.
          return password.equals(mPassword);
        }
      }

      // TODO: register the new account here.
      performSignUp(mEmail, mPassword);
      return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
      mAuthTask = null;
      showProgress(false);

      if (success) {
        onLoginSuccess();
      } else {
        onLoginError();
      }
    }

    @Override
    protected void onCancelled() {
      onLoginCancelled();
    }
  }

}