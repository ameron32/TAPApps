package com.ameron32.apps.tapnotes.di.stabbed;

import com.ameron32.apps.tapnotes.di.ApplicationDemoModule;
import com.ameron32.apps.tapnotes.di.ApplicationModule;

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
        new ApplicationDemoModule()
    );
  }
}
