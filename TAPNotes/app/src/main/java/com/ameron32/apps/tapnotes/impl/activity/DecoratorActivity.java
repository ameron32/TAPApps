/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015. ameron32
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ameron32.apps.tapnotes.impl.activity;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ameron32.apps.tapnotes.R;
import com.ameron32.apps.tapnotes.frmk.activity.AbsTapActionBarActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by klemeilleur on 4/20/2015.
 */
public class DecoratorActivity extends AbsTapActionBarActivity {

  private static final boolean SET_BACKGROUND_FROM_PALETTE = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    setContentView(provideLayoutResource());
    setTheme(provideTheme());

    ButterKnife.inject(this);
    colorize(providePaletteImage());
  }

//  @InjectView(R.id.title)
//  TextView titleView;
//
//  @InjectView(R.id.description)
//  TextView descriptionView;
//
//  @InjectView(R.id.information_container)
//  View infoView;

  protected @LayoutRes int provideLayoutResource() {
    return R.layout.activity_main;
  }

  protected Theme provideTheme() {
    return Theme.STANDARD;
  }

  protected Bitmap providePaletteImage() {
    final AssetManager assets = getResources().getAssets();
    try {
      return BitmapFactory.decodeStream(assets.open("2015ProgramConventionOptimized_1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void setTheme(Theme theme) {
    switch(theme) {
      case FALLBACK:
        setTheme(R.style.Theme_AppCompat);
        break;
      case STANDARD:
      default:
        setTheme(R.style.CustomTheme);
    }
  }

  protected enum Theme {
    STANDARD, FALLBACK
  }

  private void colorize(Bitmap photo) {
    if (photo == null) {
      return;
    }

    Palette palette = Palette.generate(photo);
    applyPalette(palette);
  }


  private void applyPalette(Palette palette) {
    if (SET_BACKGROUND_FROM_PALETTE) {
      getWindow().setBackgroundDrawable(new ColorDrawable(palette.getLightVibrantSwatch().getRgb()));
    }

//    titleView.setTextColor(palette.getVibrantSwatch().getRgb());
//    descriptionView.setTextColor(palette.getLightVibrantSwatch().getRgb());

//    colorRipple(R.id.info, palette.getDarkMutedSwatch().getRgb(),
//        palette.getDarkVibrantSwatch().getRgb());
//    colorRipple(R.id.star, palette.getMutedSwatch().getRgb(),
//        palette.getVibrantSwatch().getRgb());

//    infoView.setBackgroundColor(palette.getLightMutedSwatch().getRgb());

//    AnimatedPathView star = (AnimatedPathView) findViewById(R.id.star_container);
//    star.setFillColor(palette.getVibrantColor().getRgb());
//    star.setStrokeColor(palette.getLightVibrantColor().getRgb());
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void colorRipple(int id, int bgColor, int tintColor) {
    View buttonView = findViewById(id);

    RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
    GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
    rippleBackground.setColor(bgColor);

    ripple.setColor(ColorStateList.valueOf(tintColor));
  }
}
