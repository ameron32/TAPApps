package com.ameron32.apps.tapnotes.di.controller;

import android.app.Activity;
import android.widget.Toast;

import com.ameron32.apps.tapnotes.R;
import com.gc.materialdesign.widgets.SnackBar;

/**
 * Created by klemeilleur on 3/2/2015.
 */
public class ActivitySnackBarController extends AbsController {

  public ActivitySnackBarController(Activity activity) {
    super(activity);
  }

  public void toast(String message) {
    // from package com.kenny.snackbar.SnackBar
//    SnackBar.show(getActivity(), message);

    // from package com.gc.materialdesign.widgets.SnackBar
    new SnackBar(getActivity(), message).show();

    //
//    com.rey.material.widget.
//    SnackBar.make(getActivity())
//        .applyStyle(R.style.Material_Widget_SnackBar)
//        .text(message)
//        .show();

    // fallback
//    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }
}
