package com.ameron32.apps.tapnotes.frmk.di.stabbed;


import android.os.Bundle;

import com.ameron32.apps.tapnotes.impl.di.module.ActionBarActivityModule;
import com.ameron32.apps.tapnotes.impl.di.module.ActivityModule;
import com.ameron32.apps.tapnotes.impl.di.module.DefaultAndroidActivityModule;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.lifecycle.LifecycleEvent;
import rx.android.lifecycle.LifecycleObservable;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by klemeilleur on 3/3/2015.
 *
 * Dagger and RxAndroid ActionBarActivity
 */
public abstract class AbsRxActionBarActivity extends AbsStabbedActionBarActivity {

  @Override
  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new DefaultAndroidActivityModule(this),
        new ActivityModule(this),
        new ActionBarActivityModule(this));
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



  //TODO: PR this to RxAndroid Framework
  public <T> Observable<T> bindLifecycle(Observable<T> observable, LifecycleEvent lifecycleEvent) {
    Observable<T> boundObservable = AppObservable.bindActivity(this, observable);
    return LifecycleObservable.bindUntilLifecycleEvent(lifecycle(), boundObservable, lifecycleEvent);
  }

  //TODO: PR this to RxAndroid Framework
  public <T> Observable<T> bindLifecycle(Observable<T> observable) {
    Observable<T> boundObservable = AppObservable.bindActivity(this, observable);
    return LifecycleObservable.bindActivityLifecycle(lifecycle(), boundObservable);
  }



  private CompositeSubscription mCompositeSubscription;

  public void addToCompositeSubscription(Subscription s) {
    if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
      mCompositeSubscription = new CompositeSubscription();
    }
    mCompositeSubscription.add(s);
  }

  public void unsubscribeAll() {
    mCompositeSubscription.unsubscribe();
  }
}
