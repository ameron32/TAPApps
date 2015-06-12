package com.ameron32.trials.tapnotesreboot;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by klemeilleur on 6/12/2015.
 */
public class TAPApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initializeCalligraphy();
  }

  private void initializeCalligraphy() {
    CalligraphyConfig.initDefault(
        new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/LiberationSans-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build());
  }
}
