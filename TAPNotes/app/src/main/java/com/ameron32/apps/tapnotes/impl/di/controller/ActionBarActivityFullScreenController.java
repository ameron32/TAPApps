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

package com.ameron32.apps.tapnotes.impl.di.controller;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.ameron32.apps.tapnotes.frmk.di.controller.AbsActionBarController;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.ActivityWithDelegate;

/**
 * Created by klemeilleur on 4/20/2015.
 */
public class ActionBarActivityFullScreenController extends AbsActionBarController {

  public ActionBarActivityFullScreenController(final ActivityWithDelegate actionBarActivity) {
    super(actionBarActivity);
  }

  public void hideSystemUI(final View v) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      hideSystemUI_JellyBean(v);
    } else {
      // do nothing
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private void hideSystemUI_JellyBean(View v) {
    v.setSystemUiVisibility(
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public void showSystemUI(final View v) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      showSystemUI_JellyBean(v);
    } else {
      // do nothing
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private void showSystemUI_JellyBean(View v) {
    v.setSystemUiVisibility(
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
        View.SYSTEM_UI_FLAG_VISIBLE);
  }

  public void toggleSystemUI(View v) {
    if (isSystemUIShown(v)) {
      hideSystemUI(v);
    } else {
      showSystemUI(v);
    }
  }

  private boolean isSystemUIShown(View v) {
    final int uiVisibility = v.getSystemUiVisibility();
    switch(uiVisibility) {
      case View.SYSTEM_UI_FLAG_FULLSCREEN:
        return false;

      case View.SYSTEM_UI_FLAG_VISIBLE:
      default:
        return true;
    }
  }
}
