package com.ameron32.apps.tapnotes;

import com.ameron32.apps.tapnotes.frmk.di.stabbed.AbsApplication;
import com.ameron32.apps.tapnotes.util.ParseUtil;
import com.ameron32.apps.tapnotes.util.TimberUtil;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class CoreApplication extends AbsApplication {

  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());

    ParseUtil.initializeParse(this);
    TimberUtil.initializeTimber(this);
  }
}
