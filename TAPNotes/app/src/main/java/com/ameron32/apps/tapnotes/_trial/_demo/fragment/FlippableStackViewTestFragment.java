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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.bartoszlipinski.flippablestackview.FlippableStackView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by klemeilleur on 5/22/2015.
 */
public class FlippableStackViewTestFragment extends AbsContentFragment {

  private PagerAdapter mAdapter;

  public static FlippableStackViewTestFragment create() {
    final FlippableStackViewTestFragment t = new FlippableStackViewTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_flippable_stack_view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    initAdapter();
    initStackView();
  }

  @InjectView(R.id.stack)
  FlippableStackView mStackView;

  final int[] colors = {
      Color.BLUE, Color.CYAN, Color.GREEN, Color.WHITE, Color.BLACK,
      Color.YELLOW, Color.RED, Color.MAGENTA
  };

  private void initAdapter() {
    mAdapter = new PagerAdapter() {
      @Override
      public int getCount() {
        return colors.length;
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
        return view == object;
      }

      @Override
      public Object instantiateItem(ViewGroup container, int position) {
        //  Object o = super.instantiateItem(container, position);

        final FrameLayout l = new FrameLayout(container.getContext());
        l.setBackgroundColor(getColorFrom(position));
        container.addView(l);

        return l;
      }

      private int getColorFrom(int position) {
        return colors[(getCount()-1) - position];
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
      }
    };
  }

  private void initStackView() {
    mStackView.initStack(5);
    mStackView.setAdapter(mAdapter);
  }
}
