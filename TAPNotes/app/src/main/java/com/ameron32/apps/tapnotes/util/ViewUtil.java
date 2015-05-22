package com.ameron32.apps.tapnotes.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Kris on 4/25/2015.
 */
public class ViewUtil {
  public static int convertDpToPx(Context context, final int dp) {
    final Resources r = context.getResources();
    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    return Math.round(px);
  }

  public static boolean hitTest(View v, int x, int y) {
    final int tx = (int) (ViewCompat.getTranslationX(v) + 0.5f);
    final int ty = (int) (ViewCompat.getTranslationY(v) + 0.5f);
    final int left = v.getLeft() + tx;
    final int right = v.getRight() + tx;
    final int top = v.getTop() + ty;
    final int bottom = v.getBottom() + ty;
    return (x >= left) && (x <= right) && (y >= top) && (y <= bottom);
  }
}
