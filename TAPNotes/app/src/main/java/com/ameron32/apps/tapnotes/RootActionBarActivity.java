package com.ameron32.apps.tapnotes;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import javax.inject.Inject;

public abstract class RootActionBarActivity extends ActionBarActivity {

  @Inject
  LocationManager locationManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Perform injection so that when this call returns all dependencies will be available for use.
    ((ParseApplication) getApplication()).inject(this);
  }
}
