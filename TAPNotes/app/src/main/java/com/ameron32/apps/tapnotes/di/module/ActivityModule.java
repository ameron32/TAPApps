package com.ameron32.apps.tapnotes.di.module;

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

import com.ameron32.apps.tapnotes.MainActivity;
import com.ameron32.apps.tapnotes.MainToolbarFragment;
import com.ameron32.apps.tapnotes.SettingsActivity;
import com.ameron32.apps.tapnotes._trial._demo.fragment.MaterialImageViewTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.ParseTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.PhotoViewerTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TableTestFragment;
import com.ameron32.apps.tapnotes._trial._demo.fragment.TestFragment;
import com.ameron32.apps.tapnotes.di.controller.ActivityLoggingController;
import com.ameron32.apps.tapnotes.di.controller.ActivitySharedPreferencesController;
import com.ameron32.apps.tapnotes.di.controller.ActivitySnackBarController;
import com.ameron32.apps.tapnotes.di.controller.ActivityTitleController;
import com.ameron32.apps.tapnotes.parse.MyLoginParseActivity;
import com.ameron32.apps.tapnotes.welcome.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module(
    injects = {
        MainActivity.class,
        MyLoginParseActivity.class,
        WelcomeActivity.class,
        SettingsActivity.class,
        MainToolbarFragment.class,
        ParseTestFragment.class,
        TestFragment.class,
        TableTestFragment.class,
        MaterialImageViewTestFragment.class,
        PhotoViewerTestFragment.class
    },
    addsTo = ApplicationModule.class,
    library = true
)
public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

//  /**
//   * Allow the activity context to be injected but require that it be annotated with
//   * {@link com.ameron32.apps.tapnotes.di.me.ForActivity @ForActivity} to explicitly differentiate it from application context.
//   */
//  @Provides
//  @Singleton
//  @ForActivity
//  Context provideActivityContext() {
//    return activity;
//  }

  @Provides
  @Singleton
  ActivityTitleController provideTitleController() {
    return new ActivityTitleController(activity);
  }

  @Provides
  @Singleton
  ActivitySharedPreferencesController provideSharedPreferencesController() {
    return new ActivitySharedPreferencesController(activity);
  }

  @Provides
  @Singleton
  ActivitySnackBarController provideSnackBarController() {
    return new ActivitySnackBarController(activity);
  }

  @Provides
  @Singleton
  ActivityLoggingController provideLoggingController() {
    return new ActivityLoggingController(activity);
  }
}