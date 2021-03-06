package com.ameron32.apps.tapnotes.frmk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Fragment SPECIFICALLY for use in the Content Pane of the Activity
 */
public abstract class AbsContentFragment
    extends AbsTapSupportFragment
{

  public AbsContentFragment() {}

  @Optional @InjectView(R.id.section_label) TextView label;

  @Override public final View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    // INFLATE FRAGMENT_CORE.XML
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    FrameLayout frame = ((FrameLayout) rootView.findViewById(R.id.custom_element));
    if (getCustomLayoutResource() != 0) {
      // INFLATE CUSTOM VIEW FROM RESOURCE INTO FRAME
      int layoutResource = onReplaceFragmentLayout(getCustomLayoutResource());
      View customView = inflater.inflate(layoutResource, container, false);
      frame.addView(customView);
    }
    ButterKnife.inject(this, rootView);
    return rootView;
  }

  protected abstract int getCustomLayoutResource();

  protected int onReplaceFragmentLayout(int storedLayoutResource) {
    return storedLayoutResource;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }
}
