package com.ameron32.apps.tapnotes.di;

import android.app.Activity;

import com.kenny.snackbar.SnackBar;

/**
 * Created by klemeilleur on 3/2/2015.
 */
public class ActivitySnackBarController extends AbsController {

  public ActivitySnackBarController(Activity activity) {
    super(activity);
  }

  public void toast(String message) {
    SnackBar.show(getActivity(), message);
  }
}
