package com.ameron32.apps.tapnotes.frmk.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ameron32.apps.tapnotes.frmk.RxActivity;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.AbsRxSupportFragment;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.AbsStatedSupportFragment;
import com.ameron32.apps.tapnotes.frmk.di.stabbed.AbsTapActionBarActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by klemeilleur on 3/3/2015.
 *
 * Dagger and RxAndroid SupportFragment
 */
public abstract class AbsTapSupportFragment extends AbsRxSupportFragment {
//
//  RxActivity mRxActivity;
//
//  @Override
//  public void onAttach(Activity activity) {
//    super.onAttach(activity);
//    if (activity instanceof RxActivity) {
//      mRxActivity = (RxActivity) activity;
//    } else {
//      throw new IllegalStateException("AbsTapSupportFragment must attach to activity that implements frmk.RxActivity.");
//    }
//  }
//
//  public RxActivity getRxActivity() {
//    return mRxActivity;
//  }
}
