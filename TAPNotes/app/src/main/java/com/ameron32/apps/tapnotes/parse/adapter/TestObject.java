package com.ameron32.apps.tapnotes.parse.adapter;

import com.parse.ParseClassName;

import java.util.TreeSet;

/**
 * Created by Kris on 3/17/2015.
 */
@ParseClassName("TestObject")
public class TestObject extends ColumnableParseObject {

  @Override
  public Object get(int columnPosition) {
    return this.get(getColumnHeader(columnPosition));
  }

  @Override
  public int getColumnCount() {
    return this.keySet().size();
  }

  @Override
  public String getColumnHeader(int columnPosition) {
    int count = 0;
    for (String key : this.keySet()) {
      if (count == columnPosition) {
        return key;
      }
      count++;
    }
    return null;
  }
}
