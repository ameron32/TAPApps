package com.ameron32.apps.tapnotes;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

public class TestFragment extends AbsContentFragment
{
  @Override
  protected int getCustomLayoutResource() {
    return R.layout.view_spacer;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}
