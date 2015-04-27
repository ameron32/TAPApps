package com.ameron32.apps.tapnotes.impl.di.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.ameron32.apps.tapnotes.frmk.di.controller.AbsController;

/**
 * Created by Kris on 4/25/2015.
 */
public class ActivityAlertDialogController extends AbsController {
  public ActivityAlertDialogController(Activity activity) {
    super(activity);
  }

  public void showInformationDialog(String title, String message) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setNeutralButton("OK", (dialog, which) -> { dialog.dismiss(); })
        .create()
        .show();
  }
}
