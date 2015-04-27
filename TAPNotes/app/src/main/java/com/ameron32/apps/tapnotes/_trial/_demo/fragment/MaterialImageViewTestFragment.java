package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ameron32.apps.tapnotes._trial.ui.BlurringView;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial.ui.MaterialImageView;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityTitleController;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.lifecycle.LifecycleObservable;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;

/**
 * Created by Kris on 3/1/2015.
 */
public class MaterialImageViewTestFragment
    extends AbsContentFragment
{

  public static MaterialImageViewTestFragment create() {
    final MaterialImageViewTestFragment t = new MaterialImageViewTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  private static final String ROTATION_PREF_KEY = "TRIAL ROTATION PREFERENCE KEY";

  @Inject
  ActivityTitleController titleController;

  @Inject
  ActivitySnackBarController snackBarController;

  @Inject
  ActivitySharedPreferencesController prefController;

  private Subscription subscription;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_materialimageview;
  }

  @InjectView(R.id.pic1) MaterialImageView materialImageView1;
  int mRotation1;
  @InjectView(R.id.pic2) MaterialImageView materialImageView2;
  int mRotation2 = 15;
  @InjectView(R.id.blurring_view) BlurringView blurringView;


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    blurringView.setBlurredView(materialImageView2);
  }

  @Inject
  ActivityAlertDialogController alertDialogController;

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    alertDialogController.showInformationDialog("Fragment Demo",
        "This fragment demonstrates..." + "\n" +
            "--Material Design ImageView with shadows and rotation" + "\n" +
            "--BlurringView (poor results so far)" + "\n" +
            "--SnackBar usage" + "\n" +
            "--AlertDialog usage" + "\n" +
            "--SharedPreferences restore last rotation state (top image)" + "\n" +
            "--StatedFragment restore rotation state from bundle (bottom image)" + "\n" +
            "--RxJava usage with LifeCycleObservables" + "\n" +
            "--Title on Toolbar set via Controller"
    );
  }

  @Override
  public void onStart() {
    super.onStart();

    subscription =
        LifecycleObservable.bindFragmentLifecycle(lifecycle(), ViewObservable.clicks(materialImageView1))
            .subscribe(new Action1<OnClickEvent>() {
              @Override
              public void call(OnClickEvent onClickEvent) {
                MaterialImageView v = (MaterialImageView) onClickEvent.view();
                mRotation1 = mRotation1 + 5 * getDirection(v);
                mRotation2 = mRotation2 - 5 * getDirection(v);
                v.setRotation(mRotation1);
                otherView(v).setRotation(mRotation2);
//                snackBarController.toast("Clicked button!");
                prefController.saveIntPreference(ROTATION_PREF_KEY, mRotation1);
                blurringView.invalidate();
              }
            });
  }

  public int getDirection(MaterialImageView view) {
    switch(view.getId()) {
      case R.id.pic1:
        return 1;

      case R.id.pic2:
      default:
        return -1;
    }
  }

  public MaterialImageView otherView(MaterialImageView view) {
    if (getDirection(view) == -1) {
      return materialImageView1;
    } else {
      return materialImageView2;
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    titleController.setTitle("MaterialImageViewTestFragment");

    mRotation1 = prefController.restoreIntPreference(ROTATION_PREF_KEY, -30);
    materialImageView1.setRotation(mRotation1);
    materialImageView2.setRotation(mRotation2);
  }

  @Override
  public void onDestroy() {
    subscription = null;
    super.onDestroy();
  }

  @Override
  protected void onSaveState(Bundle outState) {
    super.onSaveState(outState);
    outState.putInt("mRotation2", mRotation2);
  }

  @Override
  protected void onRestoreState(Bundle savedInstanceState) {
    super.onRestoreState(savedInstanceState);
    mRotation2 = savedInstanceState.getInt("mRotation2");
  }
}
