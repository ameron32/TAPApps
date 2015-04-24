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

package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import app.mosn.zdepthshadowlayout.ZDepthShadowLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by klemeilleur on 4/23/2015.
 */
public class AnimatedPanesTestFragment extends AbsContentFragment {

  public static AnimatedPanesTestFragment create() {
    final AnimatedPanesTestFragment t = new AnimatedPanesTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  ActivitySnackBarController snackBarController;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_animated_panes;
  }

  @InjectView(R.id.a_pane)
  ZDepthShadowLayout leftPane;

  @InjectView(R.id.b_pane)
  ZDepthShadowLayout mainPane;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    mainPane.postDelayed(this::testAnimation, 5000);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  @OnClick({R.id.a_pane_click, R.id.b_pane_click})
  void onClick() {
    testAnimation();
  }

  private void testAnimation() {

    if (isAnimating()) {
      return;
    }

    snackBarController.toast("start");
    isAnimating.set(true);

    if (!isABack()) {
      // A to back of B
      startAnimationFor(mainPane,
          () -> sendViewToBack(leftPane),
          R.animator.dodge_right, R.animator.dodge_return_right);
      startAnimationFor(leftPane,
          null,
          R.animator.dodge_left_to_back, R.animator.dodge_return_left_to_back);
    } else {
      // B to back of A
      startAnimationFor(mainPane,
          () -> sendViewToBack(mainPane),
          R.animator.dodge_right, R.animator.dodge_return_right);
      startAnimationFor(leftPane,
          null,
          R.animator.dodge_left_to_back, R.animator.dodge_return_left_to_back);
    }
  }

  private final AtomicBoolean isAnimating = new AtomicBoolean(false);
  private boolean isAnimating() {
    return isAnimating.get();
  }

  private boolean isABack() {
    final ViewGroup parent = (ViewGroup) mainPane.getParent();
    View child = parent.getChildAt(0);
    if (child.getId() == leftPane.getId()) {
      return true;
    }
    return false;
  }

  public static void sendViewToBack(final View child) {
    final ViewGroup parent = (ViewGroup)child.getParent();
    if (null != parent) {
      parent.removeView(child);
      parent.addView(child, 0);
    }
  }

  private void startAnimationFor(final View v, final Runnable runnable, @AnimatorRes int a1, @AnimatorRes int a2) {
    final Animator anim1 = AnimatorInflater.loadAnimator(getActivity(), R.animator.zoom_out_animator);
    final Animator anim2 = AnimatorInflater.loadAnimator(getActivity(), a1);
    anim1.setTarget(v);
    anim2.setTarget(v);
    anim2.setStartDelay(500);

    final AnimatorSet set1 = new AnimatorSet();
    final AnimatorSet set2 = new AnimatorSet();

    set1.play(anim1);
    set1.play(anim2).with(anim1);
    set1.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationEnd(Animator animation) {
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

    final Animator anim3 = AnimatorInflater.loadAnimator(getActivity(), a2);
    final Animator anim4 = AnimatorInflater.loadAnimator(getActivity(), R.animator.zoom_animator);
    anim3.setTarget(v);
    anim4.setTarget(v);

    set2.play(anim3);
    set2.play(anim4).with(anim3);
    set2.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationEnd(Animator animation) {
        snackBarController.toast("finished");
        isAnimating.set(false);
      }
      @Override public void onAnimationStart(Animator animation) {}
      @Override public void onAnimationCancel(Animator animation) {}
      @Override public void onAnimationRepeat(Animator animation) {}
    });
  }

}
