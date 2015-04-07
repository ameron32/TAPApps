package com.ameron32.apps.tapnotes.unused;

/*********************************************************************
 * *******************************************************************
 * *******************************************************************
 *
 * WARNING: OBJECT NOT NECESSARY AT THE MOMENT
 *
 * *******************************************************************
 * *******************************************************************
 * *******************************************************************
 */

import android.support.v4.app.Fragment;

import com.ameron32.apps.tapnotes._trial._demo.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;


public class TestContentManager implements FragmentProvider {

  @Override
  public Fragment getFragment(int position) {
    return new TestFragment();
  }

  @Override
  public void setSelectedFragment(int position) {
    return;
  }

  private List<OnFragmentChangeListener> listeners = new ArrayList<>();

  @Override
  public void addFragmentChangeListener(OnFragmentChangeListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeFragmentChangeListener(OnFragmentChangeListener listener) {
    listeners.remove(listener);
  }
}
