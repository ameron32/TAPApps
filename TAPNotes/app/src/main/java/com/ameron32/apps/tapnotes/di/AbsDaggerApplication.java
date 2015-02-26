package com.ameron32.apps.tapnotes.di;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;


public abstract class AbsDaggerApplication extends Application {

  private ObjectGraph graph;

  @Override
  public void onCreate() {
    super.onCreate();
    graph = ObjectGraph.create(getModules().toArray());
  }

  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new ApplicationModule(this),
        new ApplicationDemoModule()
    );
  }

  public void inject(Object object) {
    graph.inject(object);
  }

  public ObjectGraph getApplicationGraph() {
    return graph;
  }
}
