package com.ameron32.trials.tapnotesreboot;

import android.app.Application;
import android.content.res.Resources;

import com.ameron32.trials.tapnotesreboot.di.Injector;
import com.ameron32.trials.tapnotesreboot.di.module.ApplicationModule;
import com.ameron32.trials.tapnotesreboot.di.module.DefaultAndroidApplicationModule;
import com.ameron32.trials.tapnotesreboot.di.module.DemoApplicationModule;
import com.ameron32.trials.tapnotesreboot.di.module.RootModule;
import com.ameron32.trials.tapnotesreboot.parse.object.TestObject;
import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by klemeilleur on 6/12/2015.
 */
public class TAPApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initializeAppModules_Dagger1(this);
    initializeTimber();
    initializeParse(this);
    initializeCalligraphy();
  }

  private void initializeTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      // TODO Crashlytics.start(this);
      Fabric.with(this, new Crashlytics());
      Timber.plant(new CrashlyticsTree());
    }
  }

  private void initializeAppModules_Dagger1(Application app) {
//    Injector.INSTANCE.init(new RootModule());
    Injector.INSTANCE.init(new DefaultAndroidApplicationModule(app));
    Injector.INSTANCE.init(new ApplicationModule(app));
    Injector.INSTANCE.init(new DemoApplicationModule());
    Injector.INSTANCE.inject(app);
  }

  private void initializeCalligraphy() {
    CalligraphyConfig.initDefault(
        new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/LiberationSans-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build());
  }

  public void initializeParse(Application app) {
    final Resources r = app.getResources();
    ParseObject.registerSubclass(TestObject.class);
    Parse.enableLocalDatastore(app);

    // Enable Crash Reporting
    ParseCrashReporting.enable(app);
    Parse.initialize(app, r.getString(R.string.APPLICATION_ID), r.getString(R.string.CLIENT_KEY));

    // Save the current Installation to Parse.
    ParseInstallation.getCurrentInstallation().saveInBackground(
        new SaveCallback() {
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
