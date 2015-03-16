package com.ameron32.apps.tapnotes.parse;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by klemeilleur on 3/16/2015.
 */
public class MyLoginParseActivity extends MyLoginActivity {
  @Override
  protected void performLogin(final String email, final String password) {
    ParseUser.logInInBackground(email, password, new LogInCallback() {
      @Override
      public void done(ParseUser parseUser, ParseException e) {
        if (e == null) {
          onLoginSuccess();
        } else {
          e.printStackTrace();
          onLoginError();
        }

        // if (cancelled()) {
        //   onLoginCancelled();
        // }
      }
    });
  }

  /**
   * TODO: CURRENTLY UNUSED, PLEASE INCLUDE IN MyLoginActivity PROCESS FLOW
   * @param email
   * @param password
   */
  @Override
  protected void performSignUp(String email, String password) {
    ParseUser user = new ParseUser();
    user.setEmail(email);
    user.setUsername(email);
    user.setPassword(password);
    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {

        // TODO: Confirm if login is still needed

        if (e == null) {
          onLoginSuccess();
        } else {
          // e.printStackTrace();
          onLoginError();
        }

        // if (cancelled()) {
        //   onLoginCancelled();
        // }
      }
    });
  }
}
