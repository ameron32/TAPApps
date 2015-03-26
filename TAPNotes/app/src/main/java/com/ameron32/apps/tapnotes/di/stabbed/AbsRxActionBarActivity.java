package com.ameron32.apps.tapnotes.di.stabbed;


import android.os.Bundle;

import com.ameron32.apps.tapnotes.di.module.ActivityModule;
import com.ameron32.apps.tapnotes.di.module.DefaultAndroidActivityModule;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.lifecycle.LifecycleEvent;
import rx.subjects.BehaviorSubject;


/**
 * Created by klemeilleur on 3/3/2015.
 *
 * Dagger and RxAndroid ActionBarActivity
 */
public abstract class AbsRxActionBarActivity extends StabbedActionBarActivity {

  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new DefaultAndroidActivityModule(this),
        new ActivityModule(this));
  }

  private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();

  public Observable<LifecycleEvent> lifecycle() {
    return lifecycleSubject.asObservable();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    lifecycleSubject.onNext(LifecycleEvent.CREATE);
  }

  @Override
  protected void onStart() {
    super.onStart();
    lifecycleSubject.onNext(LifecycleEvent.START);
  }

  @Override
  protected void onResume() {
    super.onResume();
    lifecycleSubject.onNext(LifecycleEvent.RESUME);
  }

  @Override
  protected void onPause() {
    lifecycleSubject.onNext(LifecycleEvent.PAUSE);
    super.onPause();
  }

  @Override
  protected void onStop() {
    lifecycleSubject.onNext(LifecycleEvent.STOP);
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    lifecycleSubject.onNext(LifecycleEvent.DESTROY);
    super.onDestroy();
  }
}
