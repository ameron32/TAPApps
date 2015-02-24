package com.ameron32.apps.tapnotes;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

import com.ameron32.apps.tapnotes.di.ActivityTitleController;

import javax.inject.Inject;

public class TestFragment extends AbsContentFragment
{

  @Inject
  ActivityTitleController titleController;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.view_spacer;
  }

}
