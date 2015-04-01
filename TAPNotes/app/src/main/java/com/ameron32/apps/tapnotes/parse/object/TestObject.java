package com.ameron32.apps.tapnotes.parse.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.ParseClassName;

/**
 * Created by Kris on 3/17/2015.
 */
@ParseClassName("TestObject")
public class TestObject extends ColumnableParseObject {

  public static TestObject create(String key, String key2, int keyN) {
    final TestObject o = new TestObject();
    o.put("key", key);
    o.put("key2", key2);
    o.put("keyN", keyN);
    return o;
  }

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
