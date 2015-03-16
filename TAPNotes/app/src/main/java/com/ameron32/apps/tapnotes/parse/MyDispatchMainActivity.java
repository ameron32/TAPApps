package com.ameron32.apps.tapnotes.parse;

import com.ameron32.apps.tapnotes.MainActivity;

/**
 * Created by klemeilleur on 3/16/2015.
 */
public class MyDispatchMainActivity extends MyDispatchActivity {
  @Override
  protected Class<?> getTargetClass() {
    return MainActivity.class;
  }

  @Override
  protected Class<?> getLoginActivityClass() {
    return MyLoginParseActivity.class;
  }
}
