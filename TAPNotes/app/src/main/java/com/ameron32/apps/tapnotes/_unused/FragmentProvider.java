package com.ameron32.apps.tapnotes._unused;

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

public interface FragmentProvider {
  public Fragment getFragment(int position);
  public void setSelectedFragment(int position);
  public void addFragmentChangeListener(OnFragmentChangeListener listener);
  public void removeFragmentChangeListener(OnFragmentChangeListener listener);

  public interface OnFragmentChangeListener {
    public void onFragmentChange(int position);
  }
}