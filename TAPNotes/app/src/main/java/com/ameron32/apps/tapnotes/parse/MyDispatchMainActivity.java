package com.ameron32.apps.tapnotes.parse;

import android.content.Context;
import android.content.Intent;

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

  public static Intent makeIntent(
      Context context) {
    return new Intent(context, MyDispatchMainActivity.class);
  }
}
