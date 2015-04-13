/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015. ameron32
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ameron32.apps.tapnotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySharedPreferencesController;

import javax.inject.Inject;

/**
 * Created by klemeilleur on 3/27/2015.
 */
public class MainToolbarFragment extends ToolbarFragment {

  public static MainToolbarFragment create() {
    MainToolbarFragment f = new MainToolbarFragment();
    f.setArguments(new Bundle());
    return f;
  }

  private static final String TEACH_DRAWER_PREF_KEY = "TeachDrawer";

  @Inject
  ActivitySharedPreferencesController prefController;

  private ActivityCallbacks mActivityCallbacks;

  private DrawerImpl mDrawerImpl;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    // TODO: replace with interface rather than explicit Activity reference
    if (activity instanceof ActivityCallbacks) {
      this.mActivityCallbacks = (ActivityCallbacks) activity;
    } else {
      throw new IllegalStateException("activity should inherit ActivityCallbacks");
    }
  }

  @Override
  public void onDetach() {
    mActivityCallbacks = null;
    super.onDetach();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mDrawerImpl = new DrawerImpl(getActivity(), getToolbar(), (ViewGroup) getView(), mActivityCallbacks);
  }

  @Override
  public void onDestroyView() {
    mDrawerImpl = null;
    super.onDestroyView();
  }

  @Override
  public void onResume() {
    super.onResume();

    prefController.runOnce(TEACH_DRAWER_PREF_KEY,
        new SuccessfulRunnable() {
          @Override
          public boolean run() {
            mDrawerImpl.openDrawer();
            return mDrawerImpl.isDrawerOpen();
          }
        });
  }

  public interface ActivityCallbacks {
    public void changeFragment(Fragment f);
    public void startAbout();
    public void startSettingsActivity();
    public void onLogoutClick();
  }
}