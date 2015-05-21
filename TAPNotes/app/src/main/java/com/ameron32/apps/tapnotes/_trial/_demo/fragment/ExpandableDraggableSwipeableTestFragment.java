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

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes._trial._demo.fragment.expandable.AbstractExpandableDataProvider;
import com.ameron32.apps.tapnotes._trial._demo.fragment.expandable.ExampleExpandableDataProvider;
import com.ameron32.apps.tapnotes._trial._demo.fragment.expandable.ExpandableDraggableSwipeableItemDelegate;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.mport.ForApplication;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import javax.inject.Inject;

/**
 * Created by klemeilleur on 5/21/2015.
 */
public class ExpandableDraggableSwipeableTestFragment extends AbsContentFragment implements ExpandableDraggableSwipeableItemDelegate.Callbacks {

  private ExpandableDraggableSwipeableItemDelegate mItemDelegate;
  private AbstractExpandableDataProvider mDataProvider;

  public static ExpandableDraggableSwipeableTestFragment create() {
    final ExpandableDraggableSwipeableTestFragment t = new ExpandableDraggableSwipeableTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  @ForApplication
  Context mAppContext;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_expandable_draggable_pinnable;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mDataProvider = new ExampleExpandableDataProvider();

    mItemDelegate = new ExpandableDraggableSwipeableItemDelegate(this, getActivity(), getView());
    mItemDelegate.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mItemDelegate.onSaveInstanceState(outState);
  }

  @Override
  public void onDestroyView() {
    mItemDelegate.onDestroyView();
    mItemDelegate = null;
    super.onDestroyView();
  }

  private Context getApplicationContext() {
    return mAppContext;
  }

  /**
   * This method will be called when a group item is removed
   *
   * @param groupPosition The position of the group item within data set
   */
  public void onGroupItemRemoved(int groupPosition) {
    SnackbarManager.show(
        Snackbar.with(getApplicationContext())
            .text(R.string.snack_bar_text_group_item_removed)
            .actionLabel(R.string.snack_bar_action_undo)
            .actionListener(new ActionClickListener() {
              @Override
              public void onActionClicked(Snackbar snackbar) {
                onItemUndoActionClicked();
              }
            })
            .actionColorResource(R.color.snackbar_action_color_done)
            .duration(5000)
            .type(SnackbarType.SINGLE_LINE)
            .swipeToDismiss(false)
        , getActivity());
  }


  /**
   * This method will be called when a child item is removed
   *
   * @param groupPosition The group position of the child item within data set
   * @param childPosition The position of the child item within the group
   */
  public void onChildItemRemoved(int groupPosition, int childPosition) {
    SnackbarManager.show(
        Snackbar.with(getApplicationContext())
            .text(R.string.snack_bar_text_child_item_removed)
            .actionLabel(R.string.snack_bar_action_undo)
            .actionListener(new ActionClickListener() {
              @Override
              public void onActionClicked(Snackbar snackbar) {
                onItemUndoActionClicked();
              }
            })
            .actionColorResource(R.color.snackbar_action_color_done)
            .duration(5000)
            .type(SnackbarType.SINGLE_LINE)
            .swipeToDismiss(false)
        , getActivity());
  }


  /**
   * This method will be called when a group item is pinned
   *
   * @param groupPosition The position of the group item within data set
   */
  public void onGroupItemPinned(int groupPosition) {
    SnackbarManager.show(
        Snackbar.with(getApplicationContext())
            .text("group pinned: " + groupPosition)
            .duration(5000)
            .type(SnackbarType.SINGLE_LINE)
        , getActivity());
  }


  /**
   * This method will be called when a child item is pinned
   *
   * @param groupPosition The group position of the child item within data set
   * @param childPosition The position of the child item within the group
   */
  public void onChildItemPinned(int groupPosition, int childPosition) {
    SnackbarManager.show(
        Snackbar.with(getApplicationContext())
            .text("child pinned: group " + groupPosition + " & child " + childPosition)
            .duration(5000)
            .type(SnackbarType.SINGLE_LINE)
        , getActivity());
  }


  public void onGroupItemClicked(int groupPosition) {
    AbstractExpandableDataProvider.GroupData data = getDataProvider().getGroupItem(groupPosition);


    if (data.isPinnedToSwipeLeft()) {
      // unpin if tapped the pinned item
      data.setPinnedToSwipeLeft(false);
      mItemDelegate.notifyGroupItemChanged(groupPosition);
    }
  }


  public void onChildItemClicked(int groupPosition, int childPosition) {
    AbstractExpandableDataProvider.ChildData data = getDataProvider().getChildItem(groupPosition, childPosition);


    if (data.isPinnedToSwipeLeft()) {
      // unpin if tapped the pinned item
      data.setPinnedToSwipeLeft(false);
      mItemDelegate.notifyChildItemChanged(groupPosition, childPosition);
    }
  }


  private void onItemUndoActionClicked() {
    final long result = getDataProvider().undoLastRemoval();


    if (result == RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION) {
      return;
    }


    final int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(result);
    final int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(result);


    if (childPosition == RecyclerView.NO_POSITION) {
      // group item
      mItemDelegate.notifyGroupItemRestored(groupPosition);
    } else {
      // child item
      mItemDelegate.notifyChildItemRestored(groupPosition, childPosition);
    }
  }


  // implements ExpandableItemPinnedMessageDialogFragment.EventListener
  public void onNotifyExpandableItemPinnedDialogDismissed(int groupPosition, int childPosition, boolean ok) {

    if (childPosition == RecyclerView.NO_POSITION) {
      // group item
      getDataProvider().getGroupItem(groupPosition).setPinnedToSwipeLeft(ok);
      mItemDelegate.notifyGroupItemChanged(groupPosition);
    } else {
      // child item
      getDataProvider().getChildItem(groupPosition, childPosition).setPinnedToSwipeLeft(ok);
      mItemDelegate.notifyChildItemChanged(groupPosition, childPosition);
    }
  }


  public AbstractExpandableDataProvider getDataProvider() {
    return mDataProvider;
  }
}
