package com.ameron32.apps.tapnotes._trial._demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;

/**
 * Created by klemeilleur on 3/2/2015.
 */
public class TestContentAdapter
    extends RecyclerView.Adapter<TestContentAdapter.ViewHolder> {

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nav_text_drawer,parent,false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.itemName.setText("Test Fragment");
  }

  @Override
  public int getItemCount() {
    return 1;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public ViewHolder(View itemView) {
      super(itemView);
      itemName = (TextView) itemView.findViewById(R.id.item_name);
    }
  }
}
