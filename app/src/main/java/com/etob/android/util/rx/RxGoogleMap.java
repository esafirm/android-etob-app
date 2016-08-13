package com.etob.android.util.rx;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import rx.AsyncEmitter;
import rx.Observable;

/**
 * Created by esafirm on 8/13/16.
 */
public class RxGoogleMap {

  public static Observable<GoogleMap> bind(SupportMapFragment supportMapFragment) {
    return Observable.fromAsync(
        googleMapAsyncEmitter -> supportMapFragment.getMapAsync(googleMap -> {
          googleMapAsyncEmitter.onNext(googleMap);
          googleMapAsyncEmitter.onCompleted();
        }), AsyncEmitter.BackpressureMode.LATEST);
  }
}
