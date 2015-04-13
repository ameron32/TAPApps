package com.ameron32.apps.tapnotes._unused;

/*********************************************************************
 * *******************************************************************
 * *******************************************************************
 *
 * WARNING: OBJECT NOT NECESSARY AT THE MOMENT
 *
 * *******************************************************************
 * *******************************************************************
 * *******************************************************************
 */

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;

import java.util.List;

public class ContentAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  // CONSTANTS
  private static final int NOT_TOUCHING = -1;

  private static final int TYPE_SPACER_HEADER = 0;
  private static final int TYPE_ITEM = 1;


  // LAYOUTS
  @LayoutRes
  private static final int HEADER_LAYOUT = R.layout.view_spacer;
  @LayoutRes
  private static final int ROW_LAYOUT = R.layout.row_nav_text_drawer;


  // FIELDS
  private List<ContentManager.ContentItem> mData;
  private int mSelectedDataPosition;
  private int mTouchedDataPosition = NOT_TOUCHING;

  /**
   * NAVIGATION DRAWER ADAPTER including one (1) header view
   */
  public ContentAdapter(
      List<ContentManager.ContentItem> data) {
    mData = data;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(
      ViewGroup viewGroup, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    if (viewType == TYPE_SPACER_HEADER) {
      final View v = inflater.inflate(HEADER_LAYOUT, viewGroup, false);
      return new HeaderViewHolder(v);
    }

    final View v = inflater.inflate(ROW_LAYOUT, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override public int getItemViewType(
      final int position) {
    if (isPositionHeader(position)) { return TYPE_SPACER_HEADER; }
    return TYPE_ITEM;
  }

  private boolean isPositionHeader(final int position) {
    return position < getHeaderCount();
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
    final int dataPosition = recyclerPosition - getHeaderCount();
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
            touchPosition(NOT_TOUCHING);
            return false;
          case MotionEvent.ACTION_MOVE:
            return false;
          case MotionEvent.ACTION_UP:
            touchPosition(NOT_TOUCHING);
            return false;
        }
        return true;
      }
    });
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(
          View v) {
        ContentManager.get().setSelectedFragment(dataPosition);
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
    // notify old position
    if (mTouchedDataPosition >= 0) { notifyItemChanged(mTouchedDataPosition + getHeaderCount()); }

    // update position
    mTouchedDataPosition = dataPosition;

    // notify new position
    if (dataPosition >= 0) { notifyItemChanged(dataPosition + getHeaderCount()); }
  }

  public void selectPosition(
      int dataPosition) {
    // notify old position
    notifyItemChanged(mSelectedDataPosition + getHeaderCount());

    // update position
    mSelectedDataPosition = dataPosition;

    // notify new position
    notifyItemChanged(dataPosition + getHeaderCount());
  }

  @Override public int getItemCount() {
    return (mData != null ? mData.size() + getHeaderCount() : getHeaderCount());
  }

  public int getHeaderCount() {
    return 1;
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