package com.ameron32.apps.tapnotes._trial._demo;


import android.location.LocationManager;

import com.ameron32.apps.tapnotes.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.di.controller.ActivityTitleController;

import java.util.List;

import javax.inject.Inject;

public class TestFragment extends AbsContentFragment
{

  @Inject
  ActivityTitleController titleController;

  @Inject
  ActivitySharedPreferencesController sharedPreferencesController;

  @Inject
  LocationManager locationManager;

  @Inject
  ActivitySnackBarController snackBarController;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.view_spacer;
  }

  @Override
  public void onResume() {
    super.onResume();

    final String time = sharedPreferencesController.restorePreference("time", "none");
    titleController.setTitle(time);

    final List<String> allProviders = locationManager.getAllProviders();
    snackBarController.toast(allProviders.toString());
  }

  @Override
  public void onPause() {
    super.onPause();

    final String value = String.valueOf(System.currentTimeMillis());
    sharedPreferencesController.savePreference("time", value);
  }
}
