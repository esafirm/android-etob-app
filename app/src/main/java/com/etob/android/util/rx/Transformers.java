package com.etob.android.util.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by esafirm on 8/13/16.
 */
public class Transformers {

  public static <T> Observable.Transformer<T, T> applyApiCall() {
    return observable -> observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
