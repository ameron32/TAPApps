package com.ameron32.apps.tapnotes.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Kris on 4/25/2015.
 */
public class ViewUtil {
  public static int convertDpToPx(Context context, final int dp) {
    final Resources r = context.getResources();
    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    return Math.round(px);
  }
}
