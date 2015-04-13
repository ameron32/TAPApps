package com.ameron32.apps.tapnotes;

import com.ameron32.apps.tapnotes.frmk.di.stabbed.AbsApplication;
import com.ameron32.apps.tapnotes.util.ParseUtil;
import com.ameron32.apps.tapnotes.util.TimberUtil;


public class CoreApplication extends AbsApplication {

  public void onCreate() {
    super.onCreate();

    ParseUtil.initializeParse(this);
    TimberUtil.initializeTimber(this);
  }
}
