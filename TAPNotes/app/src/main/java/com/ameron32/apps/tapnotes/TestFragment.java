package com.ameron32.apps.tapnotes;


import android.app.Activity;

import com.parse.ParseObject;

public class TestFragment extends AbsContentFragment {
  @Override
  protected int getCustomLayoutResource() {
    return R.layout.view_spacer;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    testParse();
  }

  private void testParse() {
    ParseObject testObject = new ParseObject("TestObject");
    testObject.put("key", "value");
    testObject.saveInBackground();
  }
}
