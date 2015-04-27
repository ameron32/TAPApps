package com.ameron32.apps.tapnotes._trial._demo.fragment;


import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityLoggingController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityTitleController;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.exception.FileNotFoundException;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_test_basic;
  }

  @InjectView(R.id.pdfview)
  PDFView pdfView;

  @InjectView(R.id.textview)
  TextView textView;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }

  @Inject
  ActivityAlertDialogController alertDialogController;

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    alertDialogController.showInformationDialog("Fragment Demo",
        "This fragment demonstrates..." + "\n" +
            "--Logging with Controller" + "\n" +
            "--Title set with Controller" + "\n" +
            "--SnackBar usage" + "\n" +
            "--AlertDialog usage" + "\n" +
            "--PDF rendering with PDFView" + "\n" +
            "--GPS Location by Controller"
    );
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  @Override
  public void onResume() {
    super.onResume();

    loadPdf();

    final List<String> allProviders = locationManager.getAllProviders();
    snackBarController.toast(allProviders.toString());
  }

  private void loadPdf() {

    textView.setText("Loading...");

    // assets/2015ProgramConventionOptimized.pdf excluded from source control. see gitignore.
    final String pdfPath = "2015ProgramConventionOptimized.pdf";

    try {
      pdfView.fromAsset(pdfPath)
          .onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int i) {
              if (textView != null) {
                textView.setText("Loaded " + i + ".");
              }
            }
          })
          .load();
    } catch (FileNotFoundException e) {
      final String[] failMessage = {
          "failed to load " + pdfPath + " [FileNotFound]",
          "Kris excluded some files from github. Ask him.",
          "pdf Rendering cancelled."
      };
      snackBarController.toast(failMessage[0]);
      snackBarController.toast(failMessage[1]);
      snackBarController.toast(failMessage[2]);
      alertDialogController.showInformationDialog(pdfPath + " failed...",
          failMessage[0] + "\n" +
              failMessage[1] + "\n" +
              failMessage[2]);
    }
  }
}
