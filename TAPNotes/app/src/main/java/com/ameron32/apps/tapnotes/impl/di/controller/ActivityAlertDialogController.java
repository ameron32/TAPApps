package com.ameron32.apps.tapnotes.impl.di.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.di.controller.AbsController;


/**
 * Created by Kris on 4/25/2015.
 */
public class ActivityAlertDialogController extends AbsController {
  private DialogInterface.OnClickListener dialogListener;

  public ActivityAlertDialogController(Activity activity) {
    super(activity);
    dialogListener = (dialog, which) -> {
      switch(which) {
        case DialogInterface.BUTTON_POSITIVE:

          break;
        case DialogInterface.BUTTON_NEGATIVE:

          break;
        case DialogInterface.BUTTON_NEUTRAL:
        default:

      }
      dialog.dismiss();
    };
  }

  public void showInformationDialog(final String title, final String message) {
    final AlertDialog informationDialog = new AlertDialog.Builder(getActivity())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("OK", dialogListener)
        .create();
    informationDialog.show();

    // from MaterialDesignLibrary
//    final Dialog dialog = new Dialog(getActivity(), title, message);
//    dialog.setButtonAccept((ButtonFlat) LayoutInflater.from(getActivity()).inflate(R.layout.template_button_flat, null));
//    dialog.show();

    //
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
