package com.etob.android.util.rx;

import rx.Subscription;

public class RxUtil {

  public static void unsubscribe(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
