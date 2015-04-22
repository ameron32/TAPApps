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

package com.ameron32.apps.tapnotes.frmk;

import android.os.Bundle;
import android.view.View;

import rx.Observable;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.lifecycle.LifecycleEvent;
import rx.android.lifecycle.LifecycleObservable;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klemeilleur on 4/13/2015.
 */
public class AbsRxSupportFragment extends AbsStatedSupportFragment implements IRxFragment {

  private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();

  public Observable<LifecycleEvent> lifecycle() {
    return lifecycleSubject.asObservable();
  }

  @Override
  public void onAttach(android.app.Activity activity) {
    super.onAttach(activity);
    lifecycleSubject.onNext(LifecycleEvent.ATTACH);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    lifecycleSubject.onNext(LifecycleEvent.CREATE);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    lifecycleSubject.onNext(LifecycleEvent.CREATE_VIEW);
  }

  @Override
  public void onStart() {
    super.onStart();
    lifecycleSubject.onNext(LifecycleEvent.START);
  }

  @Override
  public void onResume() {
    super.onResume();
    lifecycleSubject.onNext(LifecycleEvent.RESUME);
  }

  @Override
  public void onPause() {
    lifecycleSubject.onNext(LifecycleEvent.PAUSE);
    super.onPause();
  }

  @Override
  public void onStop() {
    lifecycleSubject.onNext(LifecycleEvent.STOP);
    super.onStop();
  }

  @Override
  public void onDestroyView() {
    lifecycleSubject.onNext(LifecycleEvent.DESTROY_VIEW);
    super.onDestroyView();
  }

  @Override
  public void onDestroy() {
    lifecycleSubject.onNext(LifecycleEvent.DESTROY);
    super.onDestroy();
  }

  @Override
  public void onDetach() {
    lifecycleSubject.onNext(LifecycleEvent.DETACH);
    super.onDetach();
  }



  //TODO: PR this to RxAndroid Framework
  public <T> Observable<T> bindLifecycle(Observable<T> observable, LifecycleEvent lifecycleEvent) {
    Observable<T> boundObservable = AppObservable.bindFragment(this, observable);
    return LifecycleObservable.bindUntilLifecycleEvent(lifecycle(), boundObservable, lifecycleEvent);
  }

  //TODO: PR this to RxAndroid Framework
  public <T> Observable<T> bindLifecycle(Observable<T> observable) {
    Observable<T> boundObservable = AppObservable.bindFragment(this, observable);
    return LifecycleObservable.bindFragmentLifecycle(lifecycle(), boundObservable);
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
