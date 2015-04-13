package com.ameron32.apps.tapnotes.impl.di.controller;

import android.app.Activity;



public abstract class AbsController {
  private final Activity activity;

  public AbsController(Activity activity) {
    this.activity = activity;
  }

  public Activity getActivity() {
    return activity;
  }
}
