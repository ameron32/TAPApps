package com.ameron32.apps.tapnotes.util;

import android.app.Application;

import com.ameron32.apps.tapnotes.BuildConfig;

import timber.log.Timber;

/**
 * Created by klemeilleur on 3/4/2015.
 */
public class TimberUtil {
  public static void initializeTimber(Application app) {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      // TODO Crashlytics.start(this);
//      Timber.plant(new CrashlyticsTree());
    }
  }
}
