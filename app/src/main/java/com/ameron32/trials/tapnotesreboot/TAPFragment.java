package com.ameron32.trials.tapnotesreboot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by klemeilleur on 6/12/2015.
 */
public class TAPFragment extends Fragment {

  public static TAPFragment create() {
    final TAPFragment f = new TAPFragment();
    f.setArguments(new Bundle());
    return f;
  }

  public TAPFragment() {}

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_tap, container, false);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.inject(this, view);
    setupRecycler();
  }

  @InjectView(R.id.recycler) RecyclerView mRecyclerView;

  @Override
  public void onDestroyView() {
    ButterKnife.reset(this);
    super.onDestroyView();
  }

  private void setupRecycler() {
    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext()));
    mRecyclerView.setAdapter(new DummyAdapter());
  }

  private Context getContext() {
    return getActivity();
  }

  static class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
      final View view = LayoutInflater.from(viewGroup.getContext())
          .inflate(android.R.layout.simple_list_item_1, viewGroup, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      ((TextView)holder.itemView).setText(position + "");
    }

    @Override
    public int getItemCount() {
      return 100;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
      public ViewHolder(View itemView) {
        super(itemView);
      }
    }
  }
}
