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
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimatorRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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
  private @AnimatorRes int nullAnimator;


  private TuckawayStyle slideStyle;

  public TuckawaySlidingPaneLayout(Context context) {
    super(context);
  }

  public TuckawaySlidingPaneLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    initAttrs(context, attrs);
  }

  public TuckawaySlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initAttrs(context, attrs);
  }

  private void initAttrs(Context context, AttributeSet attrs) {
    final TypedArray a = context.getTheme().obtainStyledAttributes(
        attrs,
        R.styleable.TuckawaySlidingPaneLayout,
        0, 0);

    try {
      // Gets you the 'value' number - 0 or 666 in your example
      int value = a.getInt(R.styleable.TuckawaySlidingPaneLayout_tuckawayStyle, 0);
      switch (value) {
        case 2:
          slideStyle = TuckawayStyle.HEAVY_BOUNCE_ON_RIGHT_SIDE;
          break;
        case 1:
          slideStyle = TuckawayStyle.HEAVY_BOUNCE_ON_LEFT_SIDE;
          break;
        case 0:
        default:
          slideStyle = TuckawayStyle.EQUAL_SHIFT_BOTH_SIDES;
      }
    } finally {
      a.recycle();
    }
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
    nullAnimator = R.animator.null_delay_only;
  }

  private int getValueFrom(boolean isPartA) {
    int amount = 0;
    if (isPartA) {
      return amount;
    }

    // isPartB
    switch (slideStyle) {
      case EQUAL_SHIFT_BOTH_SIDES:
        amount = 200;
        break;
      case HEAVY_BOUNCE_ON_LEFT_SIDE:
        amount = 500;
        break;
      case HEAVY_BOUNCE_ON_RIGHT_SIDE:
        amount = -500;
        break;
    }
    return amount;
  }

  private int getValueTo(boolean isPartA) {
    int amount = 0;
    if (!isPartA) {
      // isPartB
      return amount;
    }

    // isPartA
    switch (slideStyle) {
      case EQUAL_SHIFT_BOTH_SIDES:
        amount = 200;
        break;
      case HEAVY_BOUNCE_ON_LEFT_SIDE:
        amount = 500;
        break;
      case HEAVY_BOUNCE_ON_RIGHT_SIDE:
        amount = -500;
        break;
    }
    return amount;
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
          nullAnimator, nullAnimator);
      startAnimationFor(leftPane,
          null,
          leftAnimatorA, leftAnimatorB);
    } else {
      // B to back of A
      startAnimationFor(mainPane,
          () -> sendViewToBack(mainPane),
          nullAnimator, nullAnimator);
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
    // TODO: rewrite animation to a far less confusing way
    // CLEAN THIS MESS UP, SON!

    final Context context = getContext();

    final Animator anim1 = AnimatorInflater.loadAnimator(context, R.animator.zoom_out_animator);
    final Animator shadowAnim1 = getShadowAnim1(context);
    final Animator anim2 = getAnim2(context, a1);
    anim1.setTarget(v);
    anim2.setTarget(v);
//    anim2.setStartDelay(500);

    final AnimatorSet set1 = new AnimatorSet();
    final AnimatorSet set2 = new AnimatorSet();

    set1.play(anim1);
    set1.play(anim2).after(anim1);
    // TODO: use shadowAnim1 and use/create shadowAnim2
    set1.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        if (v == null || set2 == null) {
          return;
        }

        v.setScaleX(0.8f);
        v.setScaleY(0.8f);
        if (runnable != null) {
          runnable.run();
        }
        set2.start();
      }

      @Override
      public void onAnimationStart(Animator animation) {
      }

      @Override
      public void onAnimationCancel(Animator animation) {
      }

      @Override
      public void onAnimationRepeat(Animator animation) {
      }
    });
    set1.start();

    final Animator anim3 = getAnim3(context, a2);
    final Animator anim4 = AnimatorInflater.loadAnimator(context, R.animator.zoom_animator);
    anim3.setTarget(v);
    anim4.setTarget(v);

    set2.play(anim3);
    set2.play(anim4).after(anim3);
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

  private Animator getShadowAnim1(Context context) {
    AnimatorSet shadowSet = new AnimatorSet();
    shadowSet.setDuration(250);
    shadowSet.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        // TODO: animate the zDepth on the shadow
      }

      @Override
      public void onAnimationEnd(Animator animation) {

      }

      @Override
      public void onAnimationCancel(Animator animation) {

      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    return shadowSet;
  }

  private Animator getShadowAnim2(Context context) {
    // TODO: duplicate getShadowAnim1()
    return null;
  }

  private Animator getAnim2(Context context, @AnimatorRes int a1) {
//    final AnimatorSet anim2 = (AnimatorSet) AnimatorInflater.loadAnimator(context, a1);
//    ((ObjectAnimator) anim2.getChildAnimations().get(0)).setIntValues(getValueFrom(true), getValueTo(true));
//    return anim2;

    if (a1 == nullAnimator) {
      return AnimatorInflater.loadAnimator(context, a1);
    }

    int inverter = 1;
    switch(a1) {
      case R.animator.dodge_left_to_back:
        inverter = -1;
        break;
      case R.animator.dodge_right:
        inverter = 1;
        break;
    }
    final ObjectAnimator anim2 = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, getValueFrom(true) * inverter, getValueTo(true) * inverter);
    anim2.setInterpolator(new AccelerateDecelerateInterpolator());
    anim2.setDuration(500);
    return anim2;
  }

  private Animator getAnim3(Context context, @AnimatorRes int a2) {
//    final AnimatorSet anim3 = (AnimatorSet) AnimatorInflater.loadAnimator(context, a2);
//    ((ObjectAnimator) anim3.getChildAnimations().get(0)).setIntValues(getValueFrom(false), getValueTo(false));
//    return anim3;

    if (a2 == nullAnimator) {
      return AnimatorInflater.loadAnimator(context, a2);
    }

    int inverter = 1;
    switch(a2) {
      case R.animator.dodge_return_left_to_back:
        inverter = -1;
        break;
      case R.animator.dodge_return_right:
        inverter = 1;
        break;
    }
    final ObjectAnimator anim3 = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, getValueFrom(false) * inverter, getValueTo(false) * inverter);
    anim3.setInterpolator(new AccelerateDecelerateInterpolator());
    anim3.setDuration(500);
    return anim3;
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

  /**
   * Created by klemeilleur on 5/1/2015.
   */
  public enum TuckawayStyle {
    EQUAL_SHIFT_BOTH_SIDES, HEAVY_BOUNCE_ON_LEFT_SIDE, HEAVY_BOUNCE_ON_RIGHT_SIDE
  }
}
