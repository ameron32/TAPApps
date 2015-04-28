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

package com.ameron32.apps.tapnotes._trial.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.AnimatorRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ameron32.apps.tapnotes.R;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by klemeilleur on 4/28/2015.
 */
public class TuckawaySlidingPaneLayout extends FrameLayout {

  private static final String TAG = TuckawaySlidingPaneLayout.class.getSimpleName();

  private View mainPane = null;
  private View leftPane = null;
  private @AnimatorRes int leftAnimatorA;
  private @AnimatorRes int leftAnimatorB;
  private @AnimatorRes int rightAnimatorA;
  private @AnimatorRes int rightAnimatorB;

  public TuckawaySlidingPaneLayout(Context context) {
    super(context);
  }

  public TuckawaySlidingPaneLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TuckawaySlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    if (getChildCount() != 2) {
      Log.e(TAG, TAG + " must have 2 child views to work.");
      return;
    }

//    final View panel = getChildAt(0);
//    if (!(panel instanceof ViewGroup)) {
//      Log.e(TAG, "first View of " + TAG + " must be a ViewGroup.");
//      return;
//    }
//
//    final ViewGroup viewGroup = (ViewGroup) panel;
//    if (viewGroup.getChildCount() != 2) {
//      Log.e(TAG, "getChildCount() for " + TAG + " should be 2. Less or more cannot work.");
//      return;
//    }

    leftPane = getChildAt(0);
    mainPane = getChildAt(1);

    // assign the animators
    leftAnimatorA = R.animator.dodge_left_to_back;
    leftAnimatorB = R.animator.dodge_return_left_to_back;
    rightAnimatorA = R.animator.dodge_right;
    rightAnimatorB = R.animator.dodge_return_right;
  }

  public void toggleLayout() {
    testAnimation();
  }

  private void testAnimation() {

    if (isAnimating()) {
      return;
    }

    Log.v(TAG, "start Animation");
    isAnimating.set(true);

    if (!isABack()) {
      // A to back of B
      startAnimationFor(mainPane,
          () -> sendViewToBack(leftPane),
          rightAnimatorA, rightAnimatorB);
      startAnimationFor(leftPane,
          null,
          leftAnimatorA, leftAnimatorB);
    } else {
      // B to back of A
      startAnimationFor(mainPane,
          () -> sendViewToBack(mainPane),
          rightAnimatorA, rightAnimatorB);
      startAnimationFor(leftPane,
          null,
          leftAnimatorA, leftAnimatorB);
    }
  }

  private final AtomicBoolean isAnimating = new AtomicBoolean(false);
  private boolean isAnimating() {
    return isAnimating.get();
  }

  private boolean isABack() {
    final ViewGroup parent = (ViewGroup) mainPane.getParent();
    final View child = parent.getChildAt(0);
    if (child.getId() == leftPane.getId()) {
      return true;
    }
    return false;
  }

  public static void sendViewToBack(final View child) {
    final ViewGroup parent = (ViewGroup) child.getParent();
    if (null != parent) {
      parent.removeView(child);
      parent.addView(child, 0);
    }
  }

  private void startAnimationFor(final View v, final Runnable runnable, @AnimatorRes int a1, @AnimatorRes int a2) {
    final Context context = getContext();

    final Animator anim1 = AnimatorInflater.loadAnimator(context, R.animator.zoom_out_animator);
    final Animator anim2 = AnimatorInflater.loadAnimator(context, a1);
    anim1.setTarget(v);
    anim2.setTarget(v);
    anim2.setStartDelay(500);

    final AnimatorSet set1 = new AnimatorSet();
    final AnimatorSet set2 = new AnimatorSet();

    set1.play(anim1);
    set1.play(anim2).with(anim1);
    set1.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationEnd(Animator animation) {
        if (v == null || set2 == null) {
          return;
        }

        v.setScaleX(0.5f);
        v.setScaleY(0.5f);
        if (runnable != null) {
          runnable.run();
        }
        set2.start();
      }
      @Override public void onAnimationStart(Animator animation) {}
      @Override public void onAnimationCancel(Animator animation) {}
      @Override public void onAnimationRepeat(Animator animation) {}
    });
    set1.start();

    final Animator anim3 = AnimatorInflater.loadAnimator(context, a2);
    final Animator anim4 = AnimatorInflater.loadAnimator(context, R.animator.zoom_animator);
    anim3.setTarget(v);
    anim4.setTarget(v);

    set2.play(anim3);
    set2.play(anim4).with(anim3);
    set2.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationEnd(Animator animation) {
        if (isAnimating == null) {
          return;
        }

        Log.v(TAG, "end Animation");
        isAnimating.set(false);
      }
      @Override public void onAnimationStart(Animator animation) {}
      @Override public void onAnimationCancel(Animator animation) {}
      @Override public void onAnimationRepeat(Animator animation) {}
    });
  }


//  public View findChildViewUnder(View parentView, float x, float y) {
//    return this.getChildAt(findChildPositionUnder(parentView, x, y));
//  }
//
//  public View findHighestChildViewUnder(View parentView, float x, float y) {
//    return this.getChildAt(findHighestChildPositionUnder(parentView, x, y));
//  }
//
//  public int findChildPositionUnder(View parentView, float x, float y) {
//    final int childCount = this.getChildCount();
//
//    int iX = Math.round(x);
//    int iY = Math.round(y);
//
//    // find the first child within view (bottom to top)
//    for (int i = 0; i < childCount; i++) {
//      View child = this.getChildAt(i);
//      if (isViewContains(child, parentView, iX, iY)) {
//        return i;
//      }
//    }
//
//    return -1;
//  }
//
//  public int findHighestChildPositionUnder(View parentView, float x, float y) {
//    final int childCount = this.getChildCount();
//
//    final int iX = Math.round(x);
//    final int iY = Math.round(y);
//
//    // find the first child within view (top to bottom)
//    for (int i = childCount - 1; i >= 0; i--) {
//      final View child = this.getChildAt(i);
//      if (isViewContains(child, parentView, iX, iY)) {
//        return i;
//      }
//    }
//
//    return -1;
//  }
//
//  private boolean isViewContains(View view, View parentView, int rx, int ry) {
//    int[] l = new int[2];
//    view.getLocationOnScreen(l);
//    int x = l[0];
//    int y = l[1];
//    int w = view.getWidth();
//    int h = view.getHeight();
//
//    int[] k = new int[2];
//    parentView.getLocationOnScreen(k);
//    int oX = k[0];
//    int oY = k[1];
//
//    int roX = oX + rx;
//    int roY = oY + ry;
//
//    if (roX < x || roX > x + w || roY < y || roY > y + h) {
//      return false;
//    }
//    return true;
//  }
}
