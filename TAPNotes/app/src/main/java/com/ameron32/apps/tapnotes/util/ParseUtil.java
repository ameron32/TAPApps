package com.ameron32.apps.tapnotes.util;

import android.app.Application;
import android.content.res.Resources;

import com.ameron32.apps.tapnotes.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

/**
 * Created by klemeilleur on 3/4/2015.
 */
public class ParseUtil {

  public static void initializeParse(Application app) {

    final Resources r = app.getResources();

    Parse.enableLocalDatastore(app);
    Parse.initialize(app, r.getString(R.string.APPLICATION_ID), r.getString(R.string.CLIENT_KEY));

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
