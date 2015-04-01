package com.ameron32.apps.tapnotes.di.stabbed;

import android.os.Bundle;
import android.view.View;

import de.psdev.stabbedandroid.StabbedSupportFragment;
import rx.Observable;
import rx.android.app.AppObservable;
import rx.android.lifecycle.LifecycleEvent;
import rx.android.lifecycle.LifecycleObservable;
import rx.subjects.BehaviorSubject;

/**
 * Created by klemeilleur on 3/3/2015.
 *
 * Dagger and RxAndroid SupportFragment
 */
public abstract class AbsRxSupportFragment extends StabbedSupportFragment {

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
  protected <T> Observable<T> bindLifecycle(Observable<T> observable, LifecycleEvent lifecycleEvent) {
    Observable<T> boundObservable = AppObservable.bindFragment(this, observable);
    return LifecycleObservable.bindUntilLifecycleEvent(lifecycle(), boundObservable, lifecycleEvent);
  }

  //TODO: PR this to RxAndroid Framework
  protected <T> Observable<T> bindLifecycle(Observable<T> observable) {
    Observable<T> boundObservable = AppObservable.bindFragment(this, observable);
    return LifecycleObservable.bindFragmentLifecycle(lifecycle(), boundObservable);
  }
}