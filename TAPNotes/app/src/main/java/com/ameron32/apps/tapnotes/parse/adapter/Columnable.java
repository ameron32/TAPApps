package com.ameron32.apps.tapnotes.parse.adapter;

public interface Columnable<T> {
  public T get(int columnPosition);

  public int getColumnCount();

  public String getColumnHeader(int columnPosition);
}
