package com.ameron32.apps.tapnotes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Kris on 4/12/2015.
 * NOTE: see Big Nerd Ranch link regarding proper testing in Android Studio 1.2
 * LINK: https://www.bignerdranch.com/blog/triumph-android-studio-1-2-sneaks-in-full-testing-support/
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class MyActivityTest3 {

  @Before
  public void setUp() throws Exception {
    // setup
  }

  @Test
  public void testSomething() throws Exception {
    // test
  }
}
