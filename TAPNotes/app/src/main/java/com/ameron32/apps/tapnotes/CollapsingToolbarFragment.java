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

import com.ameron32.apps.tapnotes._trial.ui.CollapsingTitleLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CollapsingToolbarFragment
    extends ToolbarFragment
{

  public static CollapsingToolbarFragment newInstance() {
    CollapsingToolbarFragment f = new CollapsingToolbarFragment();
    return f;
  }

  @Override public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    final Context context = getActivity();
    View v = inflater.inflate(R.layout.fragment_toolbar_collapsable, container, false);
    mToolbar = (Toolbar) v.findViewById(R.id.toolbar_actionbar);
    return v;
  }

  @InjectView(R.id.backdrop_toolbar)
  CollapsingTitleLayout mTitleLayout;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
  }
}

