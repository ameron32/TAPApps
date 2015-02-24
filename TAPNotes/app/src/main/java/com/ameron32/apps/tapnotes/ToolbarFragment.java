package com.ameron32.apps.tapnotes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToolbarFragment
    extends Fragment
{

  private Toolbar mToolbar;
  private OnToolbarFragmentCallbacks mCallbacks;

  public static ToolbarFragment newInstance() {
    ToolbarFragment f = new ToolbarFragment();
    return f;
  }

  @Override public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    final Context context = getActivity();
    View v = inflater.inflate(R.layout.fragment_toolbar_default, container, false);
    mToolbar = (Toolbar) v.findViewById(R.id.toolbar_actionbar);
    inflateCustomViews(v);
    return v;
  }

  @Override public void onViewCreated(
      View view,
      Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mCallbacks.onToolbarCreated(mToolbar);
    initializeViews();
  }

  protected void inflateCustomViews(View view) {}

  protected void initializeViews() {}

  @Override public void onAttach(
      Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnToolbarFragmentCallbacks) {
      this.mCallbacks = (OnToolbarFragmentCallbacks) activity;
    } else {
      throw new IllegalStateException("activity must implement OnToolbarFragmentCallbacks");
    }
  }

  @Override public void onDetach() {
    mCallbacks = null;
    super.onDetach();
  }

  public interface OnToolbarFragmentCallbacks {
    public void onToolbarCreated(Toolbar toolbar);
  }

  public void setTitle(CharSequence title) {
    mToolbar.setTitle(title);
  }
}

