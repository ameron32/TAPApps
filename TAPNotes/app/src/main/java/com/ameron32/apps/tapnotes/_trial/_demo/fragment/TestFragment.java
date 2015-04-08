package com.ameron32.apps.tapnotes._trial._demo.fragment;


import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;

import com.ameron32.apps.tapnotes.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.di.controller.ActivityLoggingController;
import com.ameron32.apps.tapnotes.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.di.controller.ActivityTitleController;

import java.util.List;

import javax.inject.Inject;

public class TestFragment extends AbsContentFragment
{

  public static TestFragment create() {
    final TestFragment t = new TestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  ActivityTitleController titleController;

  @Inject
  ActivitySharedPreferencesController sharedPreferencesController;

  @Inject
  LocationManager locationManager;

  @Inject
  ActivitySnackBarController snackBarController;

  @Inject
  ActivityLoggingController logController;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.view_spacer;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    logController.tag(TestFragment.class.getSimpleName());
  }

  @Override
  public void onResume() {
    super.onResume();

    final String time = sharedPreferencesController.restoreStringPreference("time", "none");
    titleController.setTitle(time);
    logController.log(time);

    final List<String> allProviders = locationManager.getAllProviders();
    snackBarController.toast(allProviders.toString());
  }

  @Override
  public void onPause() {
    super.onPause();

    final String value = String.valueOf(System.currentTimeMillis());
    sharedPreferencesController.saveStringPreference("time", value);
  }
}
