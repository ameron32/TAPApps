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

package com.ameron32.apps.tapnotes.di.stabbed;

import android.os.Bundle;

import com.ameron32.apps.tapnotes.di.stabbed.mport.StabbedSupportFragment;

/**
 * Created by klemeilleur on 4/2/2015.
 */
public abstract class AbsStatedSupportFragment extends StabbedSupportFragment {
  Bundle savedState;

  public AbsStatedSupportFragment() {
    super();
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() == null) {
      throw new IllegalStateException("Any fragment that extends AbsStatedSupportFragment "
          + "MUST setArguments(). Use a fragment factory method "
          + "or setArguments prior to committing to FragmentManager.");
    }
    // Restore State Here
    if (!restoreStateFromArguments()) {
      // First Time, Initialize something here
      onFirstTimeLaunched();
    }
  }

  /**
   * When the fragment has never opened before, or the state has been lost, this method is called.
   * LINK:  http://inthecheesefactory.com/blog/best-approach-to-keep-android-fragment-state/en?utm_source=androiddevdigest
   */
  protected void onFirstTimeLaunched() {}

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // Save State Here
    saveStateToArguments();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    // Save State Here
    saveStateToArguments();
  }

  ////////////////////
  // Don't Touch !!
  ////////////////////

  private void saveStateToArguments() {
    if (getView() != null)
      savedState = saveState();
    if (savedState != null) {
      Bundle b = getArguments();
      b.putBundle("internalSavedViewState8954201239547", savedState);
    }
  }

  ////////////////////
  // Don't Touch !!
  ////////////////////

  private boolean restoreStateFromArguments() {
    Bundle b = getArguments();
    savedState = b.getBundle("internalSavedViewState8954201239547");
    if (savedState != null) {
      restoreState();
      return true;
    }
    return false;
  }

  /////////////////////////////////
  // Restore Instance State Here
  /////////////////////////////////

  private void restoreState() {
    if (savedState != null) {
      // For Example
      //tv1.setText(savedState.getString("text"));
      onRestoreState(savedState);
    }
  }

  /**
   * More reliable fragment restore state.
   * LINK:  http://inthecheesefactory.com/blog/best-approach-to-keep-android-fragment-state/en?utm_source=androiddevdigest
   * @param savedInstanceState
   */
  protected void onRestoreState(Bundle savedInstanceState) {}

  //////////////////////////////
  // Save Instance State Here
  //////////////////////////////

  private Bundle saveState() {
    Bundle state = new Bundle();
    // For Example
    //state.putString("text", tv1.getText().toString());
    onSaveState(state);
    return state;
  }

  /**
   * More reliable fragment save state.
   * LINK:  http://inthecheesefactory.com/blog/best-approach-to-keep-android-fragment-state/en?utm_source=androiddevdigest
   * @param outState
   */
  protected void onSaveState(Bundle outState) {}


}
