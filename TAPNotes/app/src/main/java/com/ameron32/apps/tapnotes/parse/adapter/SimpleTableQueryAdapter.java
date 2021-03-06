package com.ameron32.apps.tapnotes.parse.adapter;

import com.ameron32.apps.tapnotes._trial.ui.TableRowLayout;
import com.ameron32.apps.tapnotes.parse.object.ColumnableParseObject;
import com.parse.ParseQueryAdapter;

/**
 * Created by Kris on 3/14/2015.
 *
 * Demostration of how a SimpleTableQueryAdapter SHOULD WORK.
 * Still untested.
 */
public class
      SimpleTableQueryAdapter<T extends ColumnableParseObject>
    extends
      AbsTableQueryAdapter<T, Object, TableRowLayout>
{

  public SimpleTableQueryAdapter(ParseQueryAdapter.QueryFactory<T> factory, boolean hasStableIds, int rowLayoutResource, int cellLayoutResource, int textViewResourceId) {
    super(factory, hasStableIds, rowLayoutResource, cellLayoutResource, textViewResourceId);
  }

  public SimpleTableQueryAdapter(ParseQueryAdapter.QueryFactory<T> factory, boolean hasStableIds, int rowLayoutResource) {
    super(factory, hasStableIds, rowLayoutResource);
  }

  public SimpleTableQueryAdapter(ParseQueryAdapter.QueryFactory<T> factory, boolean hasStableIds, int cellLayoutResource, int textViewResourceId) {
    super(factory, hasStableIds, cellLayoutResource, textViewResourceId);
  }

  public SimpleTableQueryAdapter(ParseQueryAdapter.QueryFactory<T> factory, boolean hasStableIds) {
    super(factory, hasStableIds);
  }


  @Override
  public void onBindTableRowHolder(TableRowLayout trl, T object) {
    populateRow(object, trl);
  }


  private void populateRow(T object, TableRowLayout layout) {
    // loop through all cells, populating them with the appropriate data
    int objectColumnCount = object.getColumnCount();
    // never attempt more columns than the first row had. less is acceptable, more is impossible.
    int columnCount = (objectColumnCount < getFirstRowColumnCount()) ? objectColumnCount : getFirstRowColumnCount();

    for (int i = 0; i < columnCount; i++) {
      final int columnPosition = i;
      String columnString = null;
//      if (object.isHeaderView()) {
//        columnString = object.getColumnHeader(columnPosition);
//      } else {
        columnString = object.get(columnPosition).toString();
//      }
      layout.populateColumnTextView(columnPosition, columnString, getTextViewResourceId());
    }
  }
}
