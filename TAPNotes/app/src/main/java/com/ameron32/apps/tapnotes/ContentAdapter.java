package com.ameron32.apps.tapnotes;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class ContentAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



  private List<ContentManager.ContentItem> mData;
  private int mSelectedDataPosition;
  private int mTouchedDataPosition = -1;
  private boolean isClick = false;

  public ContentAdapter(
      List<ContentManager.ContentItem> data) {
    mData = data;
  }

  private static final int POSITION_SPACER_HEADER = 0;

  private static final int TYPE_SPACER_HEADER = 0;
  private static final int TYPE_ITEM = 1;

  @Override public RecyclerView.ViewHolder onCreateViewHolder(
      ViewGroup viewGroup, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    if (viewType == TYPE_SPACER_HEADER) {
      View v = inflater.inflate(R.layout.view_spacer, viewGroup, false);
      return new HeaderViewHolder(v);
    }

    View v = inflater.inflate(R.layout.row_nav_text_drawer, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override public int getItemViewType(
      int position) {
    if (isPositionHeader(position)) { return TYPE_SPACER_HEADER; }
    return TYPE_ITEM;
  }

  private boolean isPositionHeader(int position) {
    return position == POSITION_SPACER_HEADER;
  }

  private ContentManager.ContentItem getItem(int itemPosition) {
    return mData.get(itemPosition);
  }

  @Override public void onBindViewHolder(
      RecyclerView.ViewHolder viewHolder,
      int recyclerPosition) {
    if (viewHolder instanceof HeaderViewHolder) {
      return;
    }

    ContentAdapter.ViewHolder holder = (ViewHolder) viewHolder;
    final int dataPosition = recyclerPosition - 1;
    holder.textView.setText(getItem(dataPosition).title);
    Drawable d = holder.textView.getContext().getResources().getDrawable(getItem(dataPosition).imageResource);
    holder.textView.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);

    holder.itemView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(
          View v, MotionEvent event) {

        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            touchPosition(dataPosition);
            return false;
          case MotionEvent.ACTION_CANCEL:
            touchPosition(-1);
            return false;
          case MotionEvent.ACTION_MOVE:
            return false;
          case MotionEvent.ACTION_UP:
            touchPosition(-1);
            return false;
        }
        return true;
      }
    });
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(
          View v) {
        ContentManager.get().setCurrentSelectedFragmentPosition(dataPosition);
      }
    });

    // TODO: selected menu position, change layout accordingly
    if (mSelectedDataPosition == dataPosition || mTouchedDataPosition == dataPosition) {
      holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.selected_gray));
    } else {
      holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }
  }

  private void touchPosition(
      int dataPosition) {
    int lastDataPosition = mTouchedDataPosition;
    mTouchedDataPosition = dataPosition;
    Log.d("itt", lastDataPosition + " / " + dataPosition);
    if (lastDataPosition >= 0)
      notifyItemChanged(lastDataPosition+1);
    if (dataPosition >= 0)
      notifyItemChanged(dataPosition+1);
  }

  public void selectPosition(
      int dataPosition) {
    int prevItemPosition = mSelectedDataPosition;
    mSelectedDataPosition = dataPosition;
    Log.d("its", prevItemPosition + " / " + dataPosition);
    notifyItemChanged(prevItemPosition+1);
    notifyItemChanged(dataPosition+1);
  }

  @Override public int getItemCount() {
    // +1 for header row
    return (mData != null ? mData.size() + 1 : 1);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public ViewHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.item_name);
    }
  }

  public static class HeaderViewHolder extends RecyclerView.ViewHolder {
    public HeaderViewHolder(View itemView) {
      super(itemView);
    }
  }
}