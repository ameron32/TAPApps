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

package com.ameron32.apps.tapnotes.util;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

/**
 * Created by klemeilleur on 4/8/2015.
 */
public class CrashlyticsTree extends Timber.HollowTree {
  @Override public void i(String message, Object... args) {
    logMessage(message, args);
  }

  @Override public void i(Throwable t, String message, Object... args) {
    logMessage(message, args);
    // NOTE: We are explicitly not sending the exception to Crashlytics here.
  }

  @Override public void w(String message, Object... args) {
    logMessage("WARN: " + message, args);
  }

  @Override public void w(Throwable t, String message, Object... args) {
    logMessage("WARN: " + message, args);
    // NOTE: We are explicitly not sending the exception to Crashlytics here.
  }

  @Override public void e(String message, Object... args) {
    logMessage("ERROR: " + message, args);
  }

  @Override public void e(Throwable t, String message, Object... args) {
    logMessage("ERROR: " + message, args);
    Crashlytics.logException(t);
  }

  private void logMessage(String message, Object... args) {
    Crashlytics.log(String.format(message, args));
  }
}
