package com.ameron32.trials.tapnotesreboot.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ameron32.trials.tapnotesreboot.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by klemeilleur on 6/15/2015.
 */
public class ProgramFragment extends TAPFragment {

  private Callbacks mCallbacks;

  public static ProgramFragment create() {
    final ProgramFragment f = new ProgramFragment();
    f.setArguments(new Bundle());
    return f;
  }

  public ProgramFragment() {}

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_program, container, false);
    return rootView;
  }

  @InjectView(R.id.nav_toolbar)
  Toolbar mToolbar;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    mToolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);
    mToolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            clickBackButton();
          }
        }
    );
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof Callbacks) {
      mCallbacks = (Callbacks) activity;
    } else {
      throw new IllegalStateException("Activity must inherit " + Callbacks.class.getSimpleName());
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallbacks = null;
  }

  @Override
  public void onDestroyView() {
    ButterKnife.reset(this);
    super.onDestroyView();
  }

  private void clickBackButton() {
    if (mCallbacks != null) {
      mCallbacks.toggleProgramPane();
    }
  }

  private Context getContext() {
    return getActivity();
  }

  public interface Callbacks {
    void toggleProgramPane();
  }
}
