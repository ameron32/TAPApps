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
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
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
    View v = inflater.inflate(getCustomLayout(), container, false);
    mToolbar = (Toolbar) v.findViewById(getToolbarIdWithinCustomLayout());
    inflateCustomViews(v);
    return v;
  }

  @Override public void onViewCreated(
      View view,
      Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mCallbacks.onToolbarCreated(mToolbar);
    onCustomViewCreated();
  }

  protected @LayoutRes int getCustomLayout() {
    return R.layout.fragment_toolbar_default;
  }

  protected @IdRes int getToolbarIdWithinCustomLayout() {
    return R.id.toolbar_actionbar;
  }

  protected void inflateCustomViews(final View customLayoutView) {}

  protected void onCustomViewCreated() {}

  protected Toolbar getToolbar() {
    return mToolbar;
  }

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

