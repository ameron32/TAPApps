package com.ameron32.apps.tapnotes;


import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;

import com.ameron32.apps.tapnotes.di.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.di.ActivityTitleController;

import javax.inject.Inject;

public class TestFragment extends AbsContentFragment
{

  @Inject
  ActivityTitleController titleController;

  @Inject
  ActivitySharedPreferencesController sharedPreferencesController;

  @Inject
  LocationManager locationManager;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.view_spacer;
  }

  @Override
  public void onResume() {
    super.onResume();

    final String time = sharedPreferencesController.restorePreference("time", "none");
    titleController.setTitle(time);
  }

  @Override
  public void onPause() {
    super.onPause();

    String value = String.valueOf(System.currentTimeMillis());
    sharedPreferencesController.savePreference("time", value);
  }
}
