package com.ameron32.trials.tapnotesreboot;

import android.support.v7.widget.Toolbar;

/**
 * Created by klemeilleur on 6/17/2015.
 */
public interface ITalkToolbar {
  void setText1(String text1);
  void setImage(String imageUrl);
  void onToolbarViewCreated(Toolbar toolbar);
}