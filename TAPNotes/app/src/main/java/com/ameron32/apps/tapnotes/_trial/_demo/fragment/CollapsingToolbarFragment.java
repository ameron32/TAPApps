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

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial.ui.CollapsingTitleLayout;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.impl.di.controller.ActionBarActivityFullScreenController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;
import com.github.alexkolpa.fabtoolbar.FabToolbar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by klemeilleur on 4/14/2015.
 */
public class CollapsingToolbarFragment extends AbsContentFragment {

  public static CollapsingToolbarFragment create() {
    final CollapsingToolbarFragment t = new CollapsingToolbarFragment();
    t.setArguments(new Bundle());
    t.setHasOptionsMenu(true);
    return t;
  }

  @Inject
  ActivitySnackBarController snackBarController;

  @Inject
  ActionBarActivityFullScreenController fullScreenController;

  private static final int DUMMY_DATA_LENGTH = 100;

  @InjectView(R.id.backdrop_toolbar)
  CollapsingTitleLayout mCollapsingTitleLayout;

  @InjectView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  @InjectView(R.id.toolbar)
  Toolbar mToolbar;

  @InjectView(R.id.fab_toolbar)
  FabToolbar mFabToolbar;

  @OnClick(R.id.attach)
  void onClick(View v) {
    mFabToolbar.hide();
    snackBarController.toast("FabToolbar hidden.");
  }

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_collapsing_title_layout_with_toolbar;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    mCollapsingTitleLayout.setTitle(CollapsingToolbarFragment.class.getSimpleName());

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    mRecyclerView.setAdapter(new QuickAdapter());
    mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrolled(RecyclerView recyclerView, int scrollAmountX, int scrollAmountY) {
        super.onScrolled(recyclerView, scrollAmountX, scrollAmountY);

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        View firstView = recyclerView.getChildAt(0);
        if (firstView != null) {
          if (layoutManager.findFirstVisibleItemPosition() == 0) {
            final int toolbarHeight = mToolbar.getHeight();
            final int y = -firstView.getTop();
            final float percent = y / (float) (firstView.getHeight() - mToolbar.getHeight());

            if (firstView.getBottom() > toolbarHeight) {
              mCollapsingTitleLayout.setTranslationY(0);
              mCollapsingTitleLayout.setScrollOffset(percent);
            } else {
              mCollapsingTitleLayout.setTranslationY(firstView.getBottom() - toolbarHeight);
              mCollapsingTitleLayout.setScrollOffset(1.0f);
            }
          } else {
            mCollapsingTitleLayout.setScrollOffset(1.0f);
          }
        }
      }
    });
  }

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    fullScreenController.hideSystemUI(mRecyclerView);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.immersive, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
  switch (item.getItemId()) {
    case R.id.action_status_bar:
      fullScreenController.toggleSystemUI(mRecyclerView);
      return true;
    default:
      // none
  }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onDestroyView() {
    ButterKnife.reset(this);
    super.onDestroyView();
  }

  public static class QuickAdapter extends RecyclerView.Adapter<QuickAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

      public ViewHolder(View itemView) {
        super(itemView);
      }
    }

    public static class ItemViewHolder extends ViewHolder {

      @InjectView(R.id.item_name)
      TextView textView;

      public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
      }
    }

    public static class HeaderViewHolder extends ViewHolder {

      public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
      }
    }

    @Override
    public QuickAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      int layoutRes;
      if (viewType == 1) {
        layoutRes = R.layout.view_spacer;
        final View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new HeaderViewHolder(v);
      } else {
        // viewType == 0
        layoutRes = R.layout.row_nav_text_drawer;
        final View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ItemViewHolder(v);
      }
    }

    @Override
    public void onBindViewHolder(QuickAdapter.ViewHolder holder, int position) {
      if (position == 0) {
        HeaderViewHolder hHolder = (HeaderViewHolder) holder;
        return;
      }

      ItemViewHolder iHolder = (ItemViewHolder) holder;
      iHolder.textView.setText("Item " + (position));
    }

    @Override
    public int getItemCount() {
      return DUMMY_DATA_LENGTH + 1;
    }

    @Override
    public int getItemViewType(int position) {
      if (position == 0) {
        return 1;
      }
      return 0;
    }
  }
}
