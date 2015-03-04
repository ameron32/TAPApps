package com.ameron32.apps.tapnotes.di.stabbed;


import android.os.Bundle;
import android.os.PersistableBundle;

import com.ameron32.apps.tapnotes.CoreApplication;
import com.ameron32.apps.tapnotes.di.ActivityModule;

import java.util.Arrays;
import java.util.List;


/**
 * Created by klemeilleur on 3/3/2015.
 */
public abstract class AbsActionBarActivity extends StabbedActionBarActivity {

  @Override
  public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    CoreApplication app = (CoreApplication) getApplication();
  }

  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new DefaultAndroidActivityModule(this),
        new ActivityModule(this));
  }
}
