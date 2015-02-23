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
//    return R.layout.view_spacer;
    return R.layout.trial_fragment_test;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
//    final SlidingPaneLayout layout = (SlidingPaneLayout) view.findViewById(R.id.sliding_pane_layout);
//    layout.setSliderFadeColor(Color.TRANSPARENT);
  }
}
