package com.ameron32.apps.tapnotes.di.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;



public abstract class AbsDaggerFragment extends Fragment {

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ((AbsDaggerActivity) getActivity()).inject(this);
  }
}
