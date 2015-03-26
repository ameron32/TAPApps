package com.ameron32.apps.tapnotes._trial._demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ameron32.apps.tapnotes.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial.ui.MaterialImageView;
import com.ameron32.apps.tapnotes.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.di.controller.ActivityTitleController;
import com.ameron32.apps.tapnotes.rx.WeakSubscriberDecorator;

import java.lang.ref.WeakReference;

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

  @Inject
  ActivityTitleController titleController;

  @Inject
  ActivitySnackBarController snackBarController;

  private Subscription subscription;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_materialimageview;
  }

  @InjectView(R.id.pic1) MaterialImageView materialImageView1;
  int mRotation1 = -30;
  @InjectView(R.id.pic2) MaterialImageView materialImageView2;
  int mRotation2 = 15;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    materialImageView1.setRotation(mRotation1);
    materialImageView2.setRotation(mRotation2);
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
                mRotation1 = mRotation1 + 5;
                v.setRotation(mRotation1);
                snackBarController.toast("Clicked button!");
              }
            });
  }

  @Override
  public void onResume() {
    super.onResume();
    titleController.setTitle("MaterialImageViewTestFragment");
  }

  @Override
  public void onDestroy() {
    subscription = null;
    super.onDestroy();
  }
}
