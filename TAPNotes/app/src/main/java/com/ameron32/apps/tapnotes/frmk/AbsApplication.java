package com.ameron32.apps.tapnotes.frmk;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.ameron32.apps.tapnotes.impl.di.module.DemoApplicationModule;
import com.ameron32.apps.tapnotes.impl.di.module.ApplicationModule;
import com.ameron32.apps.tapnotes.impl.di.module.DefaultAndroidApplicationModule;
import com.crashlytics.android.Crashlytics;

import java.util.Arrays;
import java.util.List;

import com.ameron32.apps.tapnotes.frmk.di.stabbed.mport.StabbedApplication;
import io.fabric.sdk.android.Fabric;

/**
 * Created by klemeilleur on 3/3/2015.
 */
public abstract class AbsApplication extends StabbedApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    // example of complex initialization
//    TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET);
//    Fabric.with(this, new Crashlytics(), new Twitter(authConfig), new MoPub());
    Fabric.with(this, new Crashlytics());
  }

  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new DefaultAndroidApplicationModule(this),
        new ApplicationModule(this),
        new DemoApplicationModule()
    );
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
