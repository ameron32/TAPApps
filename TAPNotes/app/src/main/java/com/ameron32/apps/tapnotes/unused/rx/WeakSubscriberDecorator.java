package com.ameron32.apps.tapnotes.unused.rx;

import java.lang.ref.WeakReference;

import rx.Subscriber;

/**
 * Copied by Kris on 3/25/2015.
 *
 * LINK: http://philosophicalhacker.com/2015/03/24/how-to-keep-your-rxjava-subscribers-from-leaking
 * NOTE: Prevents common Activity leaks from subscriber.
 *       Used with @link rx.SafeObservable
 *
 * WARNING: May have negative effects.
 */
public class WeakSubscriberDecorator<T> extends Subscriber<T> {

  private final WeakReference<Subscriber<T>> mWeakSubscriber;

  public WeakSubscriberDecorator(Subscriber<T> subscriber) {

    this.mWeakSubscriber = new WeakReference<Subscriber<T>>(subscriber);
  }


  @Override
  public void onCompleted() {

    Subscriber<T> subscriber = mWeakSubscriber.get();

    if (subscriber != null) {

      subscriber.onCompleted();
    }
  }

  @Override
  public void onError(Throwable e) {

    Subscriber<T> subscriber = mWeakSubscriber.get();

    if (subscriber != null) {

      subscriber.onError(e);
    }

  }

  @Override
  public void onNext(T t) {

    Subscriber<T> subscriber = mWeakSubscriber.get();

    if (subscriber != null) {

      subscriber.onNext(t);
    }

  }
}
