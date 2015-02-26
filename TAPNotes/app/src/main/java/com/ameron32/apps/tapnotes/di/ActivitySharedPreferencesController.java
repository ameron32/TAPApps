package com.ameron32.apps.tapnotes.di;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ActivitySharedPreferencesController extends AbsController {

  private static final String SHARED_PREFERENCES_KEY = "SharedPreferencesKey";

  public ActivitySharedPreferencesController(Activity activity) {
    super(activity);
  }

  public void savePreference(String key, String value) {
    final SharedPreferences prefs = getActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.commit();
  }

  public String restorePreference(String key, String defaultValue) {
    final SharedPreferences prefs = getActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    return prefs.getString(key, defaultValue);
  }
}
