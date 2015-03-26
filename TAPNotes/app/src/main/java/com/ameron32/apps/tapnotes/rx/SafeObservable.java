package com.ameron32.apps.tapnotes.rx;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Copied by Kris on 3/25/2015.
 *
 * LINK: http://philosophicalhacker.com/2015/03/24/how-to-keep-your-rxjava-subscribers-from-leaking
 * NOTE: Prevents common Activity leaks from subscriber.
 *       Used with @link rx.WeakSubscriberDecorator
 */
public class SafeObservable<T> extends Observable<T> {

  /**
   * Creates an Observable with a Function to execute when it is subscribed to.
   * <p/>
   * <em>Note:</em> Use {@link #create(rx.Observable.OnSubscribe)} to create an Observable, instead of this constructor,
   * unless you specifically have a need for inheritance.
   *
   * @param f {@link rx.Observable.OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
   */
  protected SafeObservable(OnSubscribe<T> f) {
    super(f);
  }

  public Subscription safeSubscribe(Subscriber<T> subscriber) {

    WeakSubscriberDecorator<T> weakSubscriberDecorator = new WeakSubscriberDecorator<>(new WeakReference(subscriber));

    return subscribe(weakSubscriberDecorator);
  }
}
