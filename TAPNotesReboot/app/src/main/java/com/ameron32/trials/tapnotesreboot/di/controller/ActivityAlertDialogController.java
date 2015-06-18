package com.ameron32.trials.tapnotesreboot.di.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;


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
