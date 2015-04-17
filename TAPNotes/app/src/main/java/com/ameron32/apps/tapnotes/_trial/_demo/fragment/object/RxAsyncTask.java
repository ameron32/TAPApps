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
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by klemeilleur on 4/17/2015.
 */
public class RxAsyncTask {

  private final Observable<String> mObservable;
  private final IRxContext mRxContext;

  public RxAsyncTask(IRxContext rxContext) {
    mRxContext = rxContext;
    mObservable = Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {

      }
    }).subscribeOn(Schedulers.io());
  }

  public void doInBackground() {
    // TODO: publishProgress
  }

  public void onProgressUpdate() {
    // TODO: onNext
  }

  public void onPreExecute() {
    // TODO: before
  }

  public void onPostExecute() {
    // TODO: after
  }

  public void execute() {
    // TODO: store subscription
    cache = mRxContext.bindLifecycle(mObservable, DESTROY).cache();
    cache.subscribe(RxAndroidFragment.this);
    // TODO: unsubscribe
  }
}
