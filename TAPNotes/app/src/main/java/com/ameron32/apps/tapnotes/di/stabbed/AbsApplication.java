package com.ameron32.apps.tapnotes.di.stabbed;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.ameron32.apps.tapnotes.di.module.DemoApplicationModule;
import com.ameron32.apps.tapnotes.di.module.ApplicationModule;
import com.ameron32.apps.tapnotes.di.module.DefaultAndroidApplicationModule;

import java.util.Arrays;
import java.util.List;

import de.psdev.stabbedandroid.StabbedApplication;

/**
 * Created by klemeilleur on 3/3/2015.
 */
public abstract class AbsApplication extends StabbedApplication {
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
