package com.ameron32.apps.tapnotes;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;


public class ParseApplication extends Application {
  public void onCreate() {

    Parse.enableLocalDatastore(this);
    Parse.initialize(this, getString(R.string.APPLICATION_ID), getString(R.string.CLIENT_KEY));

    // Save the current Installation to Parse.
    ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {

      @Override public void done(
          ParseException e) {
        // PushService.setDefaultPushCallback(
        // ParseApplication.this,
        // ParseStarterProjectActivityUnused.class);
      }
    });

    Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
  }
}
