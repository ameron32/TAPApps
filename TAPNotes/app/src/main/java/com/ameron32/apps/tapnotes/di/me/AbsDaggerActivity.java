package com.ameron32.apps.tapnotes.di.me;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ameron32.apps.tapnotes.ParseApplication;
import com.ameron32.apps.tapnotes.di.ActivityModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public abstract class AbsDaggerActivity extends ActionBarActivity {

  private ObjectGraph activityGraph;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Perform injection so that when this call returns all dependencies will be available for use.
    ParseApplication app = (ParseApplication) getApplication();
//    app.inject(this);

    activityGraph = app.getObjectGraph().plus(getModules().toArray());
    activityGraph.inject(this);
  }

  @Override
  protected void onDestroy() {
    activityGraph = null;
    super.onDestroy();
  }

  /**
   * A list of modules to use for the individual activity graph. Subclasses can override this
   * method to provide additional modules provided they call and include the modules returned by
   * calling {@code super.getModules()}.
   */
  protected List<Object> getModules() {
    return Arrays.<Object>asList(new ActivityModule(this));
  }

  /** Inject the supplied {@code object} using the activity-specific graph. */
  public void inject(Object object) {
    activityGraph.inject(object);
  }
}