package com.ameron32.apps.tapnotes.impl.di.module;

/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;

import com.ameron32.apps.tapnotes.SettingsActivity;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityAlertDialogController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityLoggingController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.impl.di.controller.ActivityTitleController;
import com.ameron32.apps.tapnotes.parse.MyLoginParseActivity;
import com.ameron32.apps.tapnotes.welcome.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single mActivity. We can
 * safely create singletons using the mActivity instance because the entire object graph will only
 * ever exist inside of that mActivity.
 */
@Module(
    injects = {
//        ExpandableTestFragment.class,
//        GridTestFragment.class,
//        GridTestFragment.ColorfulAdapter.ViewHolder.class,
//        CollapsingToolbarFragment.class,
//        MainActivity.class,
        MyLoginParseActivity.class,
        WelcomeActivity.class,
//        MainToolbarFragment.class,
//        ParseTestFragment.class,
//        TestFragment.class,
//        TableTestFragment.class,
//        MaterialImageViewTestFragment.class,
//        PhotoViewerTestFragment.class,
        SettingsActivity.class,
    },
    addsTo = ApplicationModule.class,
    library = true
)
public class ActivityModule {
  private final Activity mActivity;

  public ActivityModule(final Activity activity) {
    this.mActivity = activity;
  }

//  /**
//   * Allow the mActivity context to be injected but require that it be annotated with
//   * {@link com.ameron32.apps.tapnotes.impl.di.me.ForActivity @ForActivity} to explicitly differentiate it from application context.
//   */
//  @Provides
//  @Singleton
//  @ForActivity
//  Context provideActivityContext() {
//    return mActivity;
//  }

  @Provides
  @Singleton
  ActivityTitleController provideTitleController() {
    return new ActivityTitleController(mActivity);
  }

  @Provides
  @Singleton
  ActivitySharedPreferencesController provideSharedPreferencesController() {
    return new ActivitySharedPreferencesController(mActivity);
  }

  @Provides
  @Singleton
  ActivitySnackBarController provideSnackBarController() {
    return new ActivitySnackBarController(mActivity);
  }

  @Provides
  @Singleton
  ActivityLoggingController provideLoggingController() {
    return new ActivityLoggingController(mActivity);
  }

  @Provides
  @Singleton
  ActivityAlertDialogController provideAlertDialogController() {
    return new ActivityAlertDialogController(mActivity);
  }
}
