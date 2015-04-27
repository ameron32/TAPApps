package com.ameron32.apps.tapnotes.impl.di.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.di.controller.AbsController;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.Dialog;

/**
 * Created by Kris on 4/25/2015.
 */
public class ActivityAlertDialogController extends AbsController {
  public ActivityAlertDialogController(Activity activity) {
    super(activity);
  }

  public void showInformationDialog(final String title, final String message) {
    final Dialog dialog = new Dialog(getActivity(), title, message);
    dialog.setButtonAccept((ButtonFlat) LayoutInflater.from(getActivity()).inflate(R.layout.template_button_flat, null));
    dialog.show();

//    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//    builder
//        .setTitle(title)
//        .setMessage(message)
//        .setCancelable(false)
//        .setNeutralButton("OK", (dialog, which) -> { dialog.dismiss(); })
//        .create()
//        .show();
  }
}
