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

package com.ameron32.apps.tapnotes.impl.di.module;

import android.support.v7.app.ActionBarActivity;

import com.ameron32.apps.tapnotes.MainActivity;
import com.ameron32.apps.tapnotes._trial._demo.fragment.AnimatedPanesTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.GridTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialViewPagerTestFragment;
import com.ameron32.apps.tapnotes.impl.di.controller.ActionBarActivityFullScreenController;
import com.ameron32.apps.tapnotes.impl.fragment.MainToolbarFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.CollapsingToolbarFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ExpandableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ParseTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.PhotoViewerTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TestFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by klemeilleur on 4/20/2015.
 */
@Module(
    injects = {
        AnimatedPanesTestFragment.class,
        ExpandableTestFragment.class,
        GridTestFragment.class,
        CollapsingToolbarFragment.class,
        MainActivity.class,
//        MyLoginParseActivity.class,
//        WelcomeActivity.class,
//        SettingsActivity.class,
        MainToolbarFragment.class,
        ParseTestFragment.class,
        TestFragment.class,
        TableTestFragment.class,
        MaterialImageViewTestFragment.class,
        MaterialViewPagerTestFragment.class,
        PhotoViewerTestFragment.class
    },
    includes = ActivityModule.class,
    addsTo = ApplicationModule.class,
    library = true
)
public class ActionBarActivityModule {

  private final ActionBarActivity mActionBarActivity;

  public ActionBarActivityModule(final ActionBarActivity actionBarActivity) {
    this.mActionBarActivity = actionBarActivity;
  }

  @Provides
  @Singleton
  ActionBarActivity provideActionBarActivity() {
    return mActionBarActivity;
  }

  @Provides
  @Singleton
  ActionBarActivityFullScreenController provideFullScreenController() {
    return new ActionBarActivityFullScreenController(mActionBarActivity);
  }
}
