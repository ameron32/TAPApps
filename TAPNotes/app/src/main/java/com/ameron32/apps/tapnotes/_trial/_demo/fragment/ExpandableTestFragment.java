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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.fragment.AbsContentFragment;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityLoggingController;
import com.levelupstudio.recyclerview.ExpandableRecyclerView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by klemeilleur on 4/13/2015.
 */
public class ExpandableTestFragment extends AbsContentFragment {

  private ExpandableDraggableSwipeableItemDelegate mDelegate;

  public static ExpandableTestFragment create() {
    final ExpandableTestFragment t = new ExpandableTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  Bus bus;

  @Inject
  ActivityLoggingController loggingController;

  private static final String[] dummyHeaders = {
    "Friday",
    "Saturday",
    "Sunday"
  };

  private static final String[][] dummyContent = {
      {"fri 1","fri 2","fri 3"},
      {"sat 4","sat 5","sat 6","sat 7","sat 8"},
      {"sun 9","sun 10"}
  };

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_expandable;
  }

  @InjectView(R.id.expandable_recycler_view)
  ExpandableRecyclerView exp;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    exp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    exp.setExpandableAdapter(getExpandableAdapter());
    mDelegate = new ExpandableDraggableSwipeableItemDelegate();
  }

  @Inject
  ActivityAlertDialogController alertDialogController;

  @Override
  protected void onFinishInject() {
    super.onFinishInject();
    alertDialogController.showInformationDialog("Fragment Demo",
        "This fragment demonstrates..." + "\n" +
            "--Expandable RecyclerView" + "\n" +
            "--Event Bus (not yet implemented)" + "\n" +
            "--Logging (not yet implemented)" + "\n" +
            "--AlertDialog usage"
    );

    bus.register(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    bus.unregister(this);
  }

  @Subscribe
  public void hearMessage(String message) {
    // TODO: event receipt
  }

  private ExpandableRecyclerView.ExpandableAdapter getExpandableAdapter() {
    final DummyAdapter dummyAdapter = new DummyAdapter();
    dummyAdapter.setStableIdsMode(2);
    return dummyAdapter;
  }

  public static class DummyAdapter extends ExpandableRecyclerView.ExpandableAdapter<DummyAdapter.ViewHolder, Integer> {

    private static final int VIEW_TYPE_CHILD = 0;
    private static final int VIEW_TYPE_GROUP = 1;

    public abstract static class ViewHolder extends ExpandableRecyclerView.ExpandableViewHolder {

      @InjectView(R.id.item_name)
      TextView textView;

      public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
      }
    }

    public static class GroupViewHolder extends ViewHolder {

      public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
      }

      @Override
      protected boolean canExpand() {
        return true;
      }
    }

    public static class ChildViewHolder extends ViewHolder {

      public ChildViewHolder(@NonNull View itemView) {
        super(itemView);
      }

      @Override
      protected boolean canExpand() {
        return false;
      }

      @Override
      protected boolean onViewClicked(final View myView) {
        myView.setVisibility(View.INVISIBLE);
        myView.postDelayed(new Runnable() {
          @Override
          public void run() {
            if (myView != null) {
              myView.setVisibility(View.VISIBLE);
            }
          }
        }, 2000);
        return false;
      }
    }

    @NonNull
    @Override
    protected DummyAdapter.ViewHolder onCreateExpandableViewHolder(ViewGroup viewGroup, int i) {
      final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_nav_text_drawer, viewGroup, false);
      switch(i) {
        case VIEW_TYPE_CHILD:
          return new ChildViewHolder(v);
        case VIEW_TYPE_GROUP:
        default:
          return new GroupViewHolder(v);
      }
    }

    @Override
    protected void onBindGroupView(DummyAdapter.ViewHolder viewHolder, int i) {
      if (!(viewHolder instanceof GroupViewHolder)) {
        // fail: ensure onCreate is generating the correct GroupViewHolder
        return;
      }

      GroupViewHolder gvh = (GroupViewHolder) viewHolder;
      gvh.textView.setText(dummyHeaders[i].toString());
    }

    @Override
    protected void onBindChildView(DummyAdapter.ViewHolder viewHolder, int i, int i2) {
      if (!(viewHolder instanceof ChildViewHolder)) {
        // fail: ensure onCreate is generating the correct ChildViewHolder
        return;
      }

      ChildViewHolder cvh = (ChildViewHolder) viewHolder;
      cvh.textView.setText(dummyContent[i][i2].toString());
    }

    @Override
    protected int getGroupCount() {
      return dummyHeaders.length;
    }

    @Override
    protected int getChildrenCount(int i) {
      return dummyContent[i].length;
    }

    @Override
    protected int getGroupViewType(int i) {
      return VIEW_TYPE_GROUP;
    }

    @Override
    protected int getChildViewType(int i, int i2) {
      return VIEW_TYPE_CHILD;
    }

    @Override
    public Integer getGroup(int i) {
      return i;
    }
  }
}
