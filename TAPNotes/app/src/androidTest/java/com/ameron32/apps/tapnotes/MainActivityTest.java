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

package com.ameron32.apps.tapnotes;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.ameron32.apps.tapnotes.MainActivity;
import com.ameron32.apps.tapnotes.R;

@LargeTest
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  /**
   * Creates an {@link android.test.ActivityInstrumentationTestCase2}.
   *
   * The activity to test. This must be a class in the instrumentation
   * targetPackage specified in the AndroidManifest.xml
   */
  public MainActivityTest() {
    super(MainActivity.class);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getActivity();
  }

  public void testActivityShould() throws InterruptedException {
    onView(withId(R.id.toolbar_actionbar)).check(matches(withId(R.id.toolbar_actionbar)));
  }
}
