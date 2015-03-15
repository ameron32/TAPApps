package com.ameron32.apps.tapnotes.parse.adapter;

import com.parse.ParseObject;

/**
 * Created by Kris on 3/14/2015.
 *
 * Element which combined ParseObject & Columnable
 * TODO: needs ParseApplication subclass notification
 */
public class ColumnableParseObject
    extends ParseObject
    implements Columnable<Object>
{


  /**
   *
   */
  public ColumnableParseObject() {
    // required public empty construction
  }

  // required for Columnable

  @Override
  public Object get(int columnPosition) {
    return null;
  }

  @Override
  public int getColumnCount() {
    return this.keySet().size();
  }

  @Override
  public String getColumnHeader(int columnPosition) {
    return null;
  }
}