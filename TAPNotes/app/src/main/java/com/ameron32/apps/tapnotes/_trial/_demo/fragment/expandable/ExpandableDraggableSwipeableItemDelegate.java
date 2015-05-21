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

package com.ameron32.apps.tapnotes._trial._demo.fragment.expandable;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ameron32.apps.tapnotes.R;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

/**
 * Created by klemeilleur on 5/20/2015.
 */
public class ExpandableDraggableSwipeableItemDelegate {


  private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

  private Activity mActivity;
  private View mView;

  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.Adapter mWrappedAdapter;
  private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;
  private RecyclerViewDragDropManager mRecyclerViewDragDropManager;
  private RecyclerViewSwipeManager mRecyclerViewSwipeManager;
  private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;

  public ExpandableDraggableSwipeableItemDelegate(Activity activity, View view) {
    mActivity = activity;
    mView = view;
  }

  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    //noinspection ConstantConditions
    mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
    mLayoutManager = new LinearLayoutManager(getActivity());

    final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
    mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);

    // touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
    mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
    mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
    mRecyclerViewTouchActionGuardManager.setEnabled(true);

    // drag & drop manager
    mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
    mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
        (NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z3));

    // swipe manager
    mRecyclerViewSwipeManager = new RecyclerViewSwipeManager();

    //adapter
    final MyExpandableDraggableSwipeableItemAdapter myItemAdapter =
        new MyExpandableDraggableSwipeableItemAdapter(mRecyclerViewExpandableItemManager, getDataProvider());

    myItemAdapter.setEventListener(new MyExpandableDraggableSwipeableItemAdapter.EventListener() {
      @Override
      public void onGroupItemRemoved(int groupPosition) {
        ((ExpandableDraggableSwipeableExampleActivity) getActivity()).onGroupItemRemoved(groupPosition);
      }

      @Override
      public void onChildItemRemoved(int groupPosition, int childPosition) {
        ((ExpandableDraggableSwipeableExampleActivity) getActivity()).onChildItemRemoved(groupPosition, childPosition);
      }

      @Override
      public void onGroupItemPinned(int groupPosition) {
        ((ExpandableDraggableSwipeableExampleActivity) getActivity()).onGroupItemPinned(groupPosition);
      }

      @Override
      public void onChildItemPinned(int groupPosition, int childPosition) {
        ((ExpandableDraggableSwipeableExampleActivity) getActivity()).onChildItemPinned(groupPosition, childPosition);
      }

      @Override
      public void onItemViewClicked(View v, boolean pinned) {
        onItemViewClick(v, pinned);
      }
    });

    mAdapter = myItemAdapter;

    mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);       // wrap for expanding
    mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(mWrappedAdapter);           // wrap for dragging
    mWrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(mWrappedAdapter);              // wrap for swiping

    final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

    // Change animations are enabled by default since support-v7-recyclerview v22.
    // Disable the change animation in order to make turning back animation of swiped item works properly.
    // Also need to disable them when using animation indicator.
    animator.setSupportsChangeAnimations(false);


    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
    mRecyclerView.setItemAnimator(animator);
    mRecyclerView.setHasFixedSize(false);

    // additional decorations
    //noinspection StatementWithEmptyBody
    if (supportsViewElevation()) {
      // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
    } else {
      mRecyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z1)));
    }
    mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider), true));


    // NOTE:
    // The initialization order is very important! This order determines the priority of touch event handling.
    //
    // priority: TouchActionGuard > Swipe > DragAndDrop > ExpandableItem
    mRecyclerViewTouchActionGuardManager.attachRecyclerView(mRecyclerView);
    mRecyclerViewSwipeManager.attachRecyclerView(mRecyclerView);
    mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView);
    mRecyclerViewExpandableItemManager.attachRecyclerView(mRecyclerView);
  }

  public void onSaveInstanceState(Bundle outState) {

    // save current state to support screen rotation, etc...
    if (mRecyclerViewExpandableItemManager != null) {
      outState.putParcelable(
          SAVED_STATE_EXPANDABLE_ITEM_MANAGER,
          mRecyclerViewExpandableItemManager.getSavedState());
    }
  }

  public void onDestroyView() {
    if (mRecyclerViewDragDropManager != null) {
      mRecyclerViewDragDropManager.release();
      mRecyclerViewDragDropManager = null;
    }

    if (mRecyclerViewSwipeManager != null) {
      mRecyclerViewSwipeManager.release();
      mRecyclerViewSwipeManager = null;
    }

    if (mRecyclerViewTouchActionGuardManager != null) {
      mRecyclerViewTouchActionGuardManager.release();
      mRecyclerViewTouchActionGuardManager = null;
    }

    if (mRecyclerViewExpandableItemManager != null) {
      mRecyclerViewExpandableItemManager.release();
      mRecyclerViewExpandableItemManager = null;
    }

    if (mRecyclerView != null) {
      mRecyclerView.setItemAnimator(null);
      mRecyclerView.setAdapter(null);
      mRecyclerView = null;
    }

    if (mWrappedAdapter != null) {
      WrapperAdapterUtils.releaseAll(mWrappedAdapter);
      mWrappedAdapter = null;
    }
    mAdapter = null;
    mLayoutManager = null;

    mView = null;
    mActivity = null;
  }

  private void onItemViewClick(View v, boolean pinned) {
    final int flatPosition = mRecyclerView.getChildPosition(v);

    if (flatPosition == RecyclerView.NO_POSITION) {
      return;
    }

    final long expandablePosition = mRecyclerViewExpandableItemManager.getExpandablePosition(flatPosition);
    final int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(expandablePosition);
    final int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(expandablePosition);

    if (childPosition == RecyclerView.NO_POSITION) {
      ((ExpandableDraggableSwipeableExampleActivity) getActivity()).onGroupItemClicked(groupPosition);
    } else {
      ((ExpandableDraggableSwipeableExampleActivity) getActivity()).onChildItemClicked(groupPosition, childPosition);
    }
  }

  private boolean supportsViewElevation() {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
  }

  public AbstractExpandableDataProvider getDataProvider() {
    return ((ExpandableDraggableSwipeableExampleActivity) getActivity()).getDataProvider();
  }

  public void notifyGroupItemRestored(int groupPosition) {
    mAdapter.notifyDataSetChanged();

    final long expandablePosition = RecyclerViewExpandableItemManager.getPackedPositionForGroup(groupPosition);
    final int flatPosition = mRecyclerViewExpandableItemManager.getFlatPosition(expandablePosition);
    mRecyclerView.scrollToPosition(flatPosition);
  }

  public void notifyChildItemRestored(int groupPosition, int childPosition) {
    mAdapter.notifyDataSetChanged();

    final long expandablePosition = RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, childPosition);
    final int flatPosition = mRecyclerViewExpandableItemManager.getFlatPosition(expandablePosition);
    mRecyclerView.scrollToPosition(flatPosition);
  }

  public void notifyGroupItemChanged(int groupPosition) {
    final long expandablePosition = RecyclerViewExpandableItemManager.getPackedPositionForGroup(groupPosition);
    final int flatPosition = mRecyclerViewExpandableItemManager.getFlatPosition(expandablePosition);

    mAdapter.notifyItemChanged(flatPosition);
  }

  public void notifyChildItemChanged(int groupPosition, int childPosition) {
    final long expandablePosition = RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, childPosition);
    final int flatPosition = mRecyclerViewExpandableItemManager.getFlatPosition(expandablePosition);

    mAdapter.notifyItemChanged(flatPosition);
  }

  public View getView() {
    return mView;
  }

  public Activity getActivity() {
    return mActivity;
  }

  public Resources getResources() {
    return getActivity().getResources();
  }

}
