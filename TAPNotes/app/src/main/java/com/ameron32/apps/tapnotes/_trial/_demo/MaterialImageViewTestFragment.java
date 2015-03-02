package com.ameron32.apps.tapnotes._trial._demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ameron32.apps.tapnotes.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial.ui.MaterialImageView;
import com.ameron32.apps.tapnotes.di.ActivityTitleController;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kris on 3/1/2015.
 */
public class MaterialImageViewTestFragment
    extends AbsContentFragment
{

  @Inject
  ActivityTitleController titleController;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_materialimageview;
  }

  @InjectView(R.id.pic1) MaterialImageView materialImageView1;
  @InjectView(R.id.pic2) MaterialImageView materialImageView2;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    materialImageView1.setRotation(-30);
    materialImageView2.setRotation(15);
  }

  @Override
  public void onResume() {
    super.onResume();
    titleController.setTitle("MaterialImageViewTestFragment");
  }
}
