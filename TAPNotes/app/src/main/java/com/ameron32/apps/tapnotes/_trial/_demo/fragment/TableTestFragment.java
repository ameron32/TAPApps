package com.ameron32.apps.tapnotes._trial._demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ameron32.apps.tapnotes.AbsContentFragment;
import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.di.controller.ActivityTitleController;
import com.ameron32.apps.tapnotes.parse.adapter.SimpleTableQueryAdapter;
import com.ameron32.apps.tapnotes.parse.object.TestObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class TableTestFragment
  extends AbsContentFragment
{

  public static TableTestFragment create() {
    final TableTestFragment t = new TableTestFragment();
    t.setArguments(new Bundle());
    return t;
  }

  @Inject
  ActivityTitleController mTitleController;
  private SimpleTableQueryAdapter<TestObject> mAdapter;
  private LinearLayoutManager mLayoutManager;

  @Override
  protected int getCustomLayoutResource() {
    return R.layout.trial_fragment_table;
  }

  @InjectView(R.id.recyclerView)
  RecyclerView mRecyclerView;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);

    final ParseQueryAdapter.QueryFactory<TestObject> factory
        = new ParseQueryAdapter.QueryFactory<TestObject>() {
      @Override
      public ParseQuery create() {
        final ParseQuery<TestObject> query = ParseQuery.getQuery("TestObject");
        query.orderByDescending("createdAt");
        return query;
      }
    };
    mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new SimpleTableQueryAdapter<TestObject>(factory, true);
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
    mTitleController.setTitle(TableTestFragment.class.getSimpleName());
  }
}
