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

package com.ameron32.apps.tapnotes._trial._demo.fragment.object;

import com.ameron32.apps.tapnotes.frmk.IRxContext;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.app.RxActivity;
import rx.android.app.support.RxFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static rx.android.lifecycle.LifecycleEvent.DESTROY;

/**
 * Created by klemeilleur on 4/17/2015.
 *
 * POSSIBLY BROKEN
 */
public class RxAsyncTask implements Observer<String> {

  private final Observable<String> mObservable;
  private final IRxContext mRxContext;

  public RxAsyncTask(IRxContext rxContext) {
    mRxContext = rxContext;
    mObservable = Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        onNext("Test String");
        onPostExecute();
      }
    })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io());
  }

  public final void publishProgress(int... values) {
    onProgressUpdate(values);
  }

  public void doInBackground() {}

  public void onProgressUpdate(int... values) {}

  public void onPreExecute() {}

  public void onPostExecute() {}

  public void execute() {
    onPreExecute();

    // TODO: verify--does bindLifecycle unsubscribe?
    Observable<String> cache = mRxContext.bindLifecycle(mObservable, DESTROY).cache();
    cache.subscribe(this);
  }

  /**
   * Notifies the Observer that the {@link rx.Observable} has finished sending push-based notifications.
   * <p>
   * The {@link rx.Observable} will not call this method if it calls {@link #onError}.
   */
  @Override
  public void onCompleted() {
    onPostExecute();
  }

  /**
   * Notifies the Observer that the {@link rx.Observable} has experienced an error condition.
   * <p>
   * If the {@link rx.Observable} calls this method, it will not thereafter call {@link #onNext} or
   * {@link #onCompleted}.
   *
   * @param e the exception encountered by the Observable
   */
  @Override
  public void onError(Throwable e) {
    Timber.tag("RxAsyncTask");
    Timber.e(e, "async failed (onError)", null);
    Timber.e("async failed (onError)");
  }

  /**
   * Provides the Observer with a new item to observe.
   * <p>
   * The {@link rx.Observable} may call this method 0 or more times.
   * <p>
   * The {@code Observable} will not call this method again after it calls either {@link #onCompleted} or
   * {@link #onError}.
   *
   * @param s the item emitted by the Observable
   */
  @Override
  public void onNext(String s) {
    doInBackground();
  }
}
