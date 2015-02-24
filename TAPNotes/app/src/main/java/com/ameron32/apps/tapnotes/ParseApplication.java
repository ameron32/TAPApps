package com.ameron32.apps.tapnotes;

import android.app.Application;

import com.ameron32.apps.tapnotes.di.AndroidModule;
import com.ameron32.apps.tapnotes.di.DemoModule;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;


public class ParseApplication extends Application {

  private ObjectGraph graph;

  public void onCreate() {
    super.onCreate();

    graph = ObjectGraph.create(getModules().toArray());

    initializeParse();
  }

  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new AndroidModule(this),
        new DemoModule()
    );
  }

  public void inject(Object object) {
    graph.inject(object);
  }

  private void initializeParse() {
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


  public ObjectGraph getApplicationGraph() {
    return graph;
  }
}
