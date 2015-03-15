package com.ameron32.apps.tapnotes.parse.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ameron32.apps.tapnotes.R;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter.QueryFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  TODO: NEEDS TESTING
 */
public abstract class
      AbsTableQueryAdapter
        <T extends ParseObject & Columnable<V>,
            V extends Object,
            U extends TableRowLayout>
    extends
      AbsRecyclerQueryAdapter<T, AbsTableQueryAdapter.ViewHolder<U>>
{

  private T mHeaderObject;
//  private List<V> mDataset;

  private int mRowLayoutResource;
  protected int getRowLayoutResource() {return mRowLayoutResource;}
  private int mCellLayoutResource;
  protected int getCellLayoutResource() {return mCellLayoutResource;}
  private int mTextViewResourceId;
  protected int getTextViewResourceId() {return mTextViewResourceId;}

  public static class ViewHolder<U extends TableRowLayout>
      extends RecyclerView.ViewHolder
  {
    public U mTableRowLayoutView;

    public ViewHolder(U v) {
      super(v);
      mTableRowLayoutView = v;
    }
  }

//  public TableAdapter(
//      List<? extends Columnable<T>> myDataset,
//      int rowLayoutResource,
//      int cellLayoutResource,
//      int textViewResourceId) {
//    mDataset = myDataset.toArray(new Columnable[myDataset.size()]);
//    mRowLayoutResource = rowLayoutResource;
//    mCellLayoutResource = cellLayoutResource;
//    mTextViewResourceId = textViewResourceId;
//  }
//
//  public TableAdapter(
//      List<? extends Columnable<T>> myDataset,
//      int rowLayoutResource) {
//    this(myDataset, rowLayoutResource, R.layout.simple_table_cell_textview_container, R.id.textview);
//  }
//
//  public TableAdapter(
//      List<? extends Columnable<T>> myDataset,
//      int cellLayoutResource,
//      int textViewResourceId) {
//    this(myDataset, R.layout.simple_table_row_layout, cellLayoutResource, textViewResourceId);
//  }

  public AbsTableQueryAdapter(
//      List<V> myDataset,
      final QueryFactory<T> factory,
      final boolean hasStableIds,
      final int rowLayoutResource,
      final int cellLayoutResource,
      final int textViewResourceId) {
    super(factory, hasStableIds);
//    if (myDataset == null) { mDataset = new ArrayList<V>(); }
//    else { mDataset = myDataset; }
    mRowLayoutResource = rowLayoutResource;
    mCellLayoutResource = cellLayoutResource;
    mTextViewResourceId = textViewResourceId;
  }

  public AbsTableQueryAdapter(
//      List<V> myDataset,
      final QueryFactory<T> factory,
      final boolean hasStableIds,
      int rowLayoutResource) {
    this(factory, hasStableIds,
        rowLayoutResource,
        R.layout.simple_table_cell_textview_container,
        R.id.textview);
  }

  public AbsTableQueryAdapter(
//      List<V> myDataset,
      final QueryFactory<T> factory,
      final boolean hasStableIds,
      int cellLayoutResource,
      int textViewResourceId) {
    this(factory, hasStableIds,
        R.layout.simple_table_row_layout,
        cellLayoutResource,
        textViewResourceId);
  }

  // Create new rows
  @Override public final ViewHolder<U> onCreateViewHolder(
      ViewGroup parent, int viewType) {
    U trl = createRowView(parent, viewType);

    // return furnished viewholder
//    ViewHolder vh = new ViewHolder(trl);
//    return vh;
    return new ViewHolder<U>(trl);
  }

  private U createRowView(
      ViewGroup parent, int viewType) {
    // inflate from template layout XML
    U trl = (U) LayoutInflater.from(parent.getContext()).inflate(mRowLayoutResource, parent, false);

    // set the view's size, margins, paddings and layout parameters
    onSetTableRowAttributes(trl, parent, viewType);

    // inflate cells into row
    int firstRowColumnCount = getRow(0).getColumnCount();
    trl.inflateColumns(firstRowColumnCount, mCellLayoutResource, parent);
    return trl;
  }

  public void setHeaderObject(T headerObject) {
    // sanitize object?
    mHeaderObject = headerObject;
  }

  public TableRowLayout getHeaderRow(ViewGroup parent) {
    // TODO: Header View Type?
    final int HEADER_VIEW_TYPE = 0;
    U trl = createRowView(parent, HEADER_VIEW_TYPE);

//    populateRow(mHeaderObject, trl);
    onBindTableRowHolder(trl, mHeaderObject);
    return trl;
  }

  /**
   * Set the view's size, margins, paddings and layout parameters within this method.
   * @param trl
   * @param parent
   * @param viewType
   */
  protected void onSetTableRowAttributes(
      TableRowLayout trl,
      ViewGroup parent, int viewType) {
    // allow subclass to Override
  }

  // Populate row
  @Override public final void onBindViewHolder(
      ViewHolder<U> holder,
      int position) {
    final T row = getRow(position);

    int columnCount = row.getColumnCount();
    final int maxColumnCount = getRow(0).getColumnCount();
    // use the smaller of the two column counts, firstRow & thisRow
    columnCount = (columnCount < maxColumnCount)
        ? columnCount : maxColumnCount;

    T object = getRow(position);
    onBindTableRowHolder(holder.mTableRowLayoutView, object);
  }

  public abstract void onBindTableRowHolder(U trl, T object);


  /**
   * Get number of rows in table.
   * @return Number of rows in the table.
   */
  @Override public int getItemCount() {
    // Get number of rows
    return super.getItemCount();
  }

  /**
   * Get object @ cell x,y.
   * @param rowPosition
   * @param columnPosition
   * @return Object corresponding to the given cell location.
   */
  public V getItemAt(int rowPosition,
                     int columnPosition) {
    // Get object @ cell x,y
    return getRow(rowPosition).get(columnPosition);
  }

  private T getRow(int rowPosition) {
    return (T) getItem(rowPosition);
  }
}
